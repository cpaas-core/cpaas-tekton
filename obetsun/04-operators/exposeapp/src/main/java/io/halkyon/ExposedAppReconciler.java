package io.halkyon;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.networking.v1.IngressBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.reconciler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@ControllerConfiguration(namespaces = Constants.WATCH_CURRENT_NAMESPACE, name = "exposedapp")
public class ExposedAppReconciler implements Reconciler<ExposedApp> {
  static final Logger log = LoggerFactory.getLogger(ExposedAppReconciler.class);
  static final String APP_LABEL = "app.kubernetes.io/name";
  private final KubernetesClient client;

  public ExposedAppReconciler(KubernetesClient client) {
    this.client = client;
  }

  // TODO Fill in the rest of the reconciler

  @Override
  public UpdateControl<ExposedApp> reconcile(ExposedApp exposedApp, Context context) {
    final var labels = Map.of(APP_LABEL, exposedApp.getMetadata().getName());
    final var name=exposedApp.getMetadata().getName();
    final var spec=exposedApp.getSpec();
    final var imageRef=spec.getImageRef();

    var deployment =new DeploymentBuilder()
            .withMetadata(createMetadata(exposedApp, labels))
            .withNewSpec()
            .withNewSelector().withMatchLabels(labels).endSelector()
            .withNewTemplate()
            .withNewMetadata().withLabels(labels).endMetadata()
            .withNewSpec()
            .addNewContainer()
            .withName(name).withImage(imageRef)
            .addNewPort()
            .withName("http").withProtocol("TCP").withContainerPort(8080)
            .endPort()
            .endContainer()
            .endSpec()
            .endTemplate()
            .endSpec()
            .build();

    client.apps().deployments().createOrReplace(deployment);

    client.services().createOrReplace(new ServiceBuilder()
            .withMetadata(createMetadata(exposedApp, labels))
            .withNewSpec()
            .addNewPort()
            .withName("http")
            .withPort(8080)
            .withNewTargetPort().withValue(8080).endTargetPort()
            .endPort()
            .withSelector(labels)
            .withType("ClusterIP")
            .endSpec()
            .build());

    final var metadata = createMetadata(exposedApp, labels);
    metadata.setAnnotations(Map.of(
            "nginx.ingress.kubernetes.io/rewrite-target", "/",
            "kubernetes.io/ingress.class", "nginx"
    ));

    client.network().v1().ingresses().createOrReplace(new IngressBuilder()
            .withMetadata(metadata)
            .withNewSpec()
            .addNewRule()
            .withNewHttp()
            .addNewPath()
            .withPath("/")
            .withPathType("Prefix")
            .withNewBackend()
            .withNewService()
            .withName(metadata.getName())
            .withNewPort().withNumber(8080).endPort()
            .endService()
            .endBackend()
            .endPath()
            .endHttp()
            .endRule()
            .endSpec()
            .build());

    return UpdateControl.noUpdate();

  }

  private ObjectMeta createMetadata(ExposedApp resource, Map<String, String> labels){
    final var metadata=resource.getMetadata();
    return new ObjectMetaBuilder()
            .withName(metadata.getName())
            .addNewOwnerReference()
            .withUid(metadata.getUid())
            .withApiVersion(resource.getApiVersion())
            .withName(metadata.getName())
            .withKind(resource.getKind())
            .endOwnerReference()
            .withLabels(labels)
            .build();
  }

}

