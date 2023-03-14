package org.acme;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.quarkus.runtime.StartupEvent;
import org.acme.cr.DemoResource;
import org.acme.cr.DemoResourceDoneable;
import org.acme.cr.DemoResourceList;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

public class PodListerWatcher {

  @Inject
  private KubernetesClient defaultClient;

  @Inject
  private MixedOperation<DemoResource, DemoResourceList, DemoResourceDoneable, Resource<DemoResource, DemoResourceDoneable>> crClient;

  void onStartup(@Observes StartupEvent ev) {

    List<DemoResource> list = crClient.list().getItems();
    for (DemoResource resource : list) {
      System.out.println("Found resource " + resource);
    }

    crClient.watch(new Watcher<DemoResource>() {
      @Override
      public void eventReceived(Action action, DemoResource resource) {
        System.out.println("Received " + action + " event for resource " + resource);
      }

      @Override
      public void onClose(KubernetesClientException e) {
        if (e != null) {
          e.printStackTrace();
          System.exit(-1);
        }
      }
    });
  }
}
