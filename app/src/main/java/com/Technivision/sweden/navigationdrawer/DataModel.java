package com.Technivision.sweden.navigationdrawer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ma7MouD on 1/31/2018.
 */

public class DataModel implements Parcelable{

    private String day_num ;
    private String fajr_time ;
    private String zuhr_time ;
    private String aser_time ;
    private String maghrib_time ;
    private String isha_time ;

    public DataModel(String day_num, String fajr_time, String zuhr_time, String aser_time, String maghrib_time, String isha_time) {
        this.day_num = day_num;
        this.fajr_time = fajr_time;
        this.zuhr_time = zuhr_time;
        this.aser_time = aser_time;
        this.maghrib_time = maghrib_time;
        this.isha_time = isha_time;
    }

    protected DataModel(Parcel in) {
        day_num = in.readString();
        fajr_time = in.readString();
        zuhr_time = in.readString();
        aser_time = in.readString();
        maghrib_time = in.readString();
        isha_time = in.readString();
    }

    public static final Creator<DataModel> CREATOR = new Creator<DataModel>() {
        @Override
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        @Override
        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };

    public String getDay_num() {
        return day_num;
    }

    public void setDay_num(String day_num) {
        this.day_num = day_num;
    }

    public String getFajr_time() {
        return fajr_time;
    }

    public void setFajr_time(String fajr_time) {
        this.fajr_time = fajr_time;
    }

    public String getZuhr_time() {
        return zuhr_time;
    }

    public void setZuhr_time(String zuhr_time) {
        this.zuhr_time = zuhr_time;
    }

    public String getAser_time() {
        return aser_time;
    }

    public void setAser_time(String aser_time) {
        this.aser_time = aser_time;
    }

    public String getMaghrib_time() {
        return maghrib_time;
    }

    public void setMaghrib_time(String maghrib_time) {
        this.maghrib_time = maghrib_time;
    }

    public String getIsha_time() {
        return isha_time;
    }

    public void setIsha_time(String isha_time) {
        this.isha_time = isha_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(day_num);
        parcel.writeString(fajr_time);
        parcel.writeString(zuhr_time);
        parcel.writeString(aser_time);
        parcel.writeString(maghrib_time);
        parcel.writeString(isha_time);
    }
}
