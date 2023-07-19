package com.task.demo.models;

public class Exrate {
    private String curr_from;
    private String curr_to;
    private double rate;

    public Exrate() {
    }

    public Exrate(String curr_from, String curr_to, double rate) {
        this.curr_from = curr_from;
        this.curr_to = curr_to;
        this.rate = rate;
    }

    public String getCurr_from() {
        return curr_from;
    }

    public void setCurr_from(String curr_from) {
        this.curr_from = curr_from;
    }

    public String getCurr_to() {
        return curr_to;
    }

    public void setCurr_to(String curr_to) {
        this.curr_to = curr_to;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
