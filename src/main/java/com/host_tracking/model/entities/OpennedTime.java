package com.host_tracking.model.entities;

public class OpennedTime {
    
    private Long hours;
    private Long minutes;

    public OpennedTime() {
    }

    public OpennedTime(Long hours, Long minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public Long getHours() {
        return hours;
    }

    public void setHours(Long hours) {
        this.hours = hours;
    }

    public Long getMinutes() {
        return minutes;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public void setCurrentSession(OpennedTime opennedTime){
        if (getHours() == null || getMinutes() == null){
            throw new IllegalStateException("You have not started your session");
        }
        this.hours = opennedTime.getHours();
        this.minutes = opennedTime.getMinutes();

    }

    public OpennedTime getCurrentSessionTime(){
        return this;
    }

    public void resetOpennedTime(){
        this.hours = 0L;
        this.minutes = 0L;
    }

    @Override
    public String toString() {
        return getHours() +" Hours : " + getMinutes() + " minutes";

    }
}
