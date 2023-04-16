import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

public class PodWatcher {
    @Inject
    KubernetesClient defaultClient;

    void onStartup(@Observes StartupEvent ev){
        List<Pod> list = defaultClient.pods().list().getItems();
        for (Pod resource : list){
            System.out.println("Found resource: " +
                    " name=" + resource.getMetadata().getName() +
                    " version=" +resource.getMetadata().getResourceVersion());
        }

        defaultClient.pods().watch(new Watcher<Pod>() {
            @Override
            public void eventReceived(Action action, Pod resource) {
                System.out.println("Received: " + action + "event for " +
                        " name=" + resource.getMetadata().getName() +
                        " version=" + resource.getMetadata().getResourceVersion());
            }

            @Override
            public void onClose(WatcherException e) {
                if (e != null){
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        });
    }

}
