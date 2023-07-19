package com.task.demo.settings;

import org.springframework.stereotype.Component;

@Component
public class ConverterSettings {
    private volatile double margin = 0.05;
    private volatile long timeToUpdateInSeconds = 5;

    private volatile Boolean isUpdateActive = Boolean.TRUE;

    public void setMargin(double margin){
        this.margin=margin;
    }

    public double getMargin() {
        return margin;
    }

    public long getTimeToUpdateInSeconds() {
        return timeToUpdateInSeconds;
    }

    public void setTimeToUpdateInSeconds(long timeToUpdateInSeconds) {
        this.timeToUpdateInSeconds = timeToUpdateInSeconds;
    }

    public Boolean isUpdateActive() {
        return isUpdateActive;
    }

    public void setUpdateActive(Boolean updateActive) {
        isUpdateActive = updateActive;
    }
}
