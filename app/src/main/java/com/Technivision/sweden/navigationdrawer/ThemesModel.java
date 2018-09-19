package com.Technivision.sweden.navigationdrawer;

/**
 * Created by Ma7MouD on 2/5/2018.
 */

public class ThemesModel {

    private String title ;
    private String image ;

    public ThemesModel(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
