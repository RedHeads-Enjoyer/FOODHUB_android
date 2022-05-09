package com.example.foodhub.steps;

public class Step {
    private String desc;
    private String duration;

    public Step(String desc, String duration) {
        this.desc = desc;
        this.duration = duration;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
