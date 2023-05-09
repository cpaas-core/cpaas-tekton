import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.tekton.client.DefaultTektonClient;
import io.fabric8.tekton.client.TektonClient;
import io.javaoperatorsdk.operator.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CronPipelineJobOperator {

    private static final Logger log = LoggerFactory.getLogger(CronPipelineJobOperator.class);
    public static void main(String[] args){
        log.info("CronPipelineOperator starting!");

        TektonClient tknClient = new DefaultTektonClient();
        KubernetesClient k8sClient = new KubernetesClientBuilder().build();
        Operator operator = new Operator(k8sClient);
        operator.register(new CronPipelineJobReconciler(tknClient));
        operator.start();
    }
}
