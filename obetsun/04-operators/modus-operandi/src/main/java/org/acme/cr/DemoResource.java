package org.acme.cr;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.client.CustomResource;
import io.quarkus.runtime.annotations.RegisterForReflection;

@JsonDeserialize
@RegisterForReflection
public class DemoResource extends CustomResource {

  private DemoResourceSpec spec;
  private DemoResourceStatus status;

  public DemoResourceSpec getSpec() {
    return spec;
  }

  public void setSpec(DemoResourceSpec spec) {
    this.spec = spec;
  }

  @Override
  public String toString() {
    return "name=" + getMetadata().getName() + ", version=" + getMetadata().getResourceVersion() + ", spec=" + spec;
  }
}
