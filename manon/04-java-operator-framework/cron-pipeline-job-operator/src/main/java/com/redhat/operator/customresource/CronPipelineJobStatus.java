package com.redhat.operator.customresource;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.fabric8.kubernetes.model.annotation.PrinterColumn;

import java.util.Date;

public class CronPipelineJobStatus {

    @JsonProperty("active")
    private boolean isActive = false;

    @PrinterColumn(name="Suspend")
    private boolean suspend = false;

    @PrinterColumn(name="Last Scheduled")
    private Date lastScheduled;

    public boolean isActive(){
        return this.isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isSuspend() {
        return this.suspend;
    }

    public void setSuspend(boolean suspend) {
        this.suspend = suspend;
    }

    public Date getLastScheduled() {
        return this.lastScheduled;
    }

    public void setLastScheduled(Date lastScheduled) {
        this.lastScheduled = lastScheduled;
    }
}
