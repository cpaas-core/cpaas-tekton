package org.acme.cr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.Set;

@JsonDeserialize
@RegisterForReflection
public class DemoResourceSpec {

  @JsonProperty("demo.properties")
  private Set<String> demoProperties;

  public Set<String> getDemoProperties() {
    return demoProperties;
  }

  public void setDemoProperties(Set<String> demoProperties) {
    this.demoProperties = demoProperties;
  }

  @Override
  public String toString() {
    return "[" + String.join(", ", demoProperties.toArray(new String[]{})) + "]";
  }
}
