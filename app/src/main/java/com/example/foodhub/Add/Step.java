package com.example.foodhub.Add;

import java.io.Serializable;

public class Step implements Serializable {
    private String desc;
    private Integer sec;
    private Integer min;
    private Integer hour;

    public Step(String desc, Integer sec, Integer min, Integer hour) {
        this.desc = desc;
        this.sec = sec;
        this.min = min;
        this.hour = hour;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getSec() {
        return sec;
    }

    public void setSec(Integer sec) {
        this.sec = sec;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }
}
