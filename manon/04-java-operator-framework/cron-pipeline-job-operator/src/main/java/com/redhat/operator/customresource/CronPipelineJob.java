package com.redhat.operator.customresource;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;

@ShortNames("cpj")
@Group("triggers.tekton.manon")
@Version("v1")
public class CronPipelineJob extends CustomResource<CronPipelineJobSpec, CronPipelineJobStatus> implements Namespaced {

}
