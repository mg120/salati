package com.Technivision.sweden.navigationdrawer;

/**
 * Created by Ma7MouD on 2/9/2018.
 */

public class AzanModel {

    private int id ;
    private String name ;
    private String desc ;
    private String sound_url ;

    public AzanModel(int id, String name, String desc, String sound_url) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.sound_url = sound_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSound_url() {
        return sound_url;
    }

    public void setSound_url(String sound_url) {
        this.sound_url = sound_url;
    }
}
