import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.redhat.operator.customresource.CronPipelineJob;
import com.redhat.operator.customresource.CronPipelineJobStatus;
import io.fabric8.tekton.client.TektonClient;
import io.fabric8.tekton.pipeline.v1beta1.PipelineRun;
import io.javaoperatorsdk.operator.api.reconciler.*;
import io.javaoperatorsdk.operator.processing.event.rate.RateLimited;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.cronutils.model.CronType.UNIX;

@RateLimited(maxReconciliations = 2, within = 3)
@ControllerConfiguration
public class CronPipelineJobReconciler
        implements Reconciler<CronPipelineJob> {

    private static final Logger log = LoggerFactory.getLogger(CronPipelineJobReconciler.class);
    private final TektonClient client;
    public CronPipelineJobReconciler(TektonClient client){
        this.client = client;
    }
    @Override
    public UpdateControl<CronPipelineJob> reconcile(
            CronPipelineJob cronPipelineJob, Context<CronPipelineJob> context) throws Exception {

        // Check if is suspended
        CronPipelineJobStatus cronPipelineJobStatus = cronPipelineJob.getStatus();
        if (cronPipelineJobStatus == null){
            cronPipelineJobStatus = new CronPipelineJobStatus();
        }
        if (cronPipelineJobStatus.isSuspend() || cronPipelineJobStatus.isActive()){
            return UpdateControl.noUpdate();
        }

        String pipelineRunName = cronPipelineJob.getSpec().getPipelineRunRef();

        // Retrieve pipelineRun
        PipelineRun pipelineRun = this.client.v1beta1()
                .pipelineRuns()
                .inNamespace(cronPipelineJob.getMetadata().getNamespace())
                .withName(pipelineRunName)
                .get();

        if (pipelineRun == null){
            log.warn("No pipelineRun with name '{}' found", pipelineRunName);
            // TODO - Add template to create it if not exist (like Deployment create pods)
            return UpdateControl.noUpdate();
        }

        // Check last execution and crontab
        ZonedDateTime lastScheduled = null;
        if (cronPipelineJobStatus.getLastScheduled() != null){
            lastScheduled = ZonedDateTime.parse(cronPipelineJobStatus.getLastScheduled());
        }
        String cronSpec = cronPipelineJob.getSpec().getCronSpec();

        // TODO - Make compatible with other cron formats (QUARTZ, CRON4J, SPRING and SPRING53)
        CronParser quartzCronParser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(UNIX));
        Cron parsedUnixCronExpression = quartzCronParser.parse(cronSpec);

        ZonedDateTime now = ZonedDateTime.now();
        ExecutionTime executionTime = ExecutionTime.forCron(parsedUnixCronExpression);
        ZonedDateTime expectedPreviousExecutionTime = executionTime.lastExecution(now).get();

        if (lastScheduled != null && expectedPreviousExecutionTime.isBefore(lastScheduled)) {
            log.debug("Expected previous execution {} already executed at {}",
                    expectedPreviousExecutionTime, lastScheduled);
            return UpdateControl.noUpdate();
        }

        // Create a new pipelineRun
        cronPipelineJobStatus.setActive(true);
        cronPipelineJob.setStatus(cronPipelineJobStatus);
        UpdateControl uc = UpdateControl.patchStatus(cronPipelineJob);

        String generateName = pipelineRun.getMetadata().getGenerateName();
        if (generateName == null){
            generateName = pipelineRun.getMetadata().getName();
        }
        UUID uuid = UUID.randomUUID();
        pipelineRun.getMetadata().setName(generateName + uuid.toString());
        pipelineRun.getMetadata().setUid(uuid.toString());
        this.client.v1beta1().pipelineRuns().inNamespace(cronPipelineJob.getMetadata().getNamespace())
                .createOrReplace(pipelineRun);

        cronPipelineJobStatus.setActive(false);
        cronPipelineJobStatus.setLastScheduled(ZonedDateTime.now().toString());
        cronPipelineJob.setStatus(cronPipelineJobStatus);

        // TODO - Add reschedule
        return UpdateControl.patchStatus(cronPipelineJob).rescheduleAfter(
                executionTime.timeToNextExecution(now).get().getSeconds(), TimeUnit.SECONDS);
    }

}
