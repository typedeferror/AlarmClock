package com.example.user.alarmclock;


public class Alarm {
    private int hour;
    private int min;
    private String label;

    public Alarm(int hour, int min, String label) {
        this.hour = hour;
        this.min = min;
        this.label = label;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public String time() {
        return String.format("%s:%s", hour, min);
    }

    public String getLabel() {
        return label;
    }
}
