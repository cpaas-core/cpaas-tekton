import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.NamespacedKubernetesClientAdapter;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientProducer {
    @Produces
    @Singleton
    KubernetesClient makeDefaultKubernetesClient(@Named("namespace") String namespace){
        return new DefaultKubernetesClient().inNamespace(namespace);
    }

    @Produces
    @Singleton
    @Named("namespace")
    String getNamespace() throws IOException {
        return new String(Files.readAllBytes(Paths.get("/var/run/secrets/kubernetes.io/serviceaccount/namespace")));
    }
}
