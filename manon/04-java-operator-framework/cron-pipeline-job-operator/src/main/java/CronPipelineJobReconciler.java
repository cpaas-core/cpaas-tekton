import com.cronutils.model.Cron;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.model.time.ExecutionTime;
import com.cronutils.parser.CronParser;
import com.redhat.operator.customresource.CronPipelineJob;
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
        if (cronPipelineJob.getStatus().isSuspend() || cronPipelineJob.getStatus().isActive()){
            return UpdateControl.noUpdate();
        }

        String pipelineRunName = cronPipelineJob.getSpec().getPipelineRun();

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
        ZonedDateTime lastScheduled = ZonedDateTime.ofInstant(
                cronPipelineJob.getStatus().getLastScheduled().toInstant(),
                ZoneId.systemDefault());
        String cronSpec = cronPipelineJob.getSpec().getCronSpec();

        // TODO - Make compatible with other cron formats (QUARTZ, CRON4J, SPRING and SPRING53)
        CronParser quartzCronParser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(UNIX));
        Cron parsedUnixCronExpression = quartzCronParser.parse(cronSpec);

        ZonedDateTime now = ZonedDateTime.now();
        ExecutionTime executionTime = ExecutionTime.forCron(parsedUnixCronExpression);
        ZonedDateTime expectedPreviousExecutionTime = executionTime.lastExecution(now).get();

        if (expectedPreviousExecutionTime.isBefore(lastScheduled)) {
            log.debug("Expected previous execution {} already executed at {}",
                    expectedPreviousExecutionTime, lastScheduled);
            return UpdateControl.noUpdate();
        }

        // Create a new pipelineRun
        cronPipelineJob.getStatus().setActive(true);
        UpdateControl uc = UpdateControl.patchStatus(cronPipelineJob);

        String generateName = cronPipelineJob.getMetadata().getGenerateName();
        if (generateName == null){
            generateName = cronPipelineJob.getMetadata().getName();
        }
        UUID uuid = UUID.randomUUID();
        pipelineRun.getMetadata().setName(generateName + uuid.toString());
        this.client.v1beta1().pipelineRuns().inNamespace(cronPipelineJob.getMetadata().getNamespace())
                .createOrReplace(pipelineRun);

        cronPipelineJob.getStatus().setActive(false);
        cronPipelineJob.getStatus().setLastScheduled(new Date());

        // TODO - Add reschedule
        return UpdateControl.patchStatus(cronPipelineJob);
    }

}
