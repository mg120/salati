package com.Technivision.sweden.navigationdrawer.CalendarFragments;

/**
 * Created by Ma7MouD on 2/6/2018.
 */

public class CalenderModel {

    private String id;
    private String month_num;
    private String day_name;

    private String meladay_day;
    private String fajr_cal;
    private String zuhr_cal;
    private String aser_cal;
    private String maghrib_cal;
    private String isha_cal;

    public CalenderModel(String id, String month_num, String day_name, String meladay_day, String fajr_cal, String zuhr_cal, String aser_cal, String maghrib_cal, String isha_cal) {
        this.id = id;
        this.month_num = month_num;
        this.day_name = day_name;
        this.meladay_day = meladay_day;
        this.fajr_cal = fajr_cal;
        this.zuhr_cal = zuhr_cal;
        this.aser_cal = aser_cal;
        this.maghrib_cal = maghrib_cal;
        this.isha_cal = isha_cal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth_num() {
        return month_num;
    }

    public void setMonth_num(String month_num) {
        this.month_num = month_num;
    }

    public String getDay_name() {
        return day_name;
    }

    public void setDay_name(String day_name) {
        this.day_name = day_name;
    }

    public String getMeladay_day() {
        return meladay_day;
    }

    public void setMeladay_day(String meladay_day) {
        this.meladay_day = meladay_day;
    }

    public String getFajr_cal() {
        return fajr_cal;
    }

    public void setFajr_cal(String fajr_cal) {
        this.fajr_cal = fajr_cal;
    }

    public String getZuhr_cal() {
        return zuhr_cal;
    }

    public void setZuhr_cal(String zuhr_cal) {
        this.zuhr_cal = zuhr_cal;
    }

    public String getAser_cal() {
        return aser_cal;
    }

    public void setAser_cal(String aser_cal) {
        this.aser_cal = aser_cal;
    }

    public String getMaghrib_cal() {
        return maghrib_cal;
    }

    public void setMaghrib_cal(String maghrib_cal) {
        this.maghrib_cal = maghrib_cal;
    }

    public String getIsha_cal() {
        return isha_cal;
    }

    public void setIsha_cal(String isha_cal) {
        this.isha_cal = isha_cal;
    }
}