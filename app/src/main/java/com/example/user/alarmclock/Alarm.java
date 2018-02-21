package com.example.user.alarmclock;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Alarm implements Parcelable {

    private int id;
    private String label;
    private Date time;
    private boolean active;
    private int[] repeat;

    public Alarm(String label, Date time, boolean active, int[] repeat, int id) {
        this.id = id;
        this.label = label;
        this.time = time;
        this.active = active;
        this.repeat = repeat;
    }

    private Alarm(Parcel in) {
        id = in.readInt();
        label = in.readString();
        active = in.readByte() != 0;
        repeat = in.createIntArray();
    }

    public int getId() {
        return id;
    }

    public int[] getRepeat() {
        return repeat;
    }

    public String getLabel() {
        return label;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(label);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeArray(new int[][]{repeat});
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
}
