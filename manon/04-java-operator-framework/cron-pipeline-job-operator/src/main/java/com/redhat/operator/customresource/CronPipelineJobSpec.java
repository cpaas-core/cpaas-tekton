package com.redhat.operator.customresource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.fabric8.generator.annotation.Pattern;
import io.fabric8.kubernetes.model.annotation.PrinterColumn;


public class CronPipelineJobSpec {

    @PrinterColumn(name="Cron")
    @Pattern("^(\\d+|\\*)(/\\d+)?(\\s+(\\d+|\\*)(/\\d+)?){4}$")
    @JsonPropertyDescription("The cron spec defining the interval a PipelineRun is run")
    private String cronSpec;

    @PrinterColumn(name="PipelineRun")
    @JsonPropertyDescription("The name of the PipelineRun that is going to be priodically executed")
    private String pipelineRunRef;

    public String getCronSpec(){
        return this.cronSpec;
    }

    public void setCronSpec(String cronSpec){
        this.cronSpec = cronSpec;
    }

    public String getPipelineRun(){
        return this.pipelineRunRef;
    }

    public void setPipelineRun(String pipelineRun){
        this.pipelineRunRef = pipelineRun;
    }
}
