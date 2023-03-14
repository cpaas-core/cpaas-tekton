package org.acme;

import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.internal.KubernetesDeserializer;
import org.acme.cr.DemoResource;
import org.acme.cr.DemoResourceDoneable;
import org.acme.cr.DemoResourceList;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClientProducer {

  @Produces
  @Singleton
  MixedOperation<DemoResource, DemoResourceList, DemoResourceDoneable, Resource<DemoResource, DemoResourceDoneable>>
  makeCustomResourceClient(KubernetesClient defaultClient) {
    KubernetesDeserializer.registerCustomKind("instana.com/v1alpha1", "Demo", DemoResource.class);
    CustomResourceDefinition crd = defaultClient.customResourceDefinitions().list().getItems().stream()
        .findFirst().orElseThrow(RuntimeException::new);
    return defaultClient.customResources(crd, DemoResource.class, DemoResourceList.class, DemoResourceDoneable.class);
  }

  @Produces
  @Singleton
  private KubernetesClient makeDefaultClient(@Named("namespace") String namespace) {
    return new DefaultKubernetesClient().inNamespace(namespace);
  }

  @Produces
  @Singleton
  @Named("namespace")
  String findMyCurrentNamespace() throws IOException {
    return new String(Files.readAllBytes(Paths.get("/var/run/secrets/kubernetes.io/serviceaccount/namespace")));
  }
}
