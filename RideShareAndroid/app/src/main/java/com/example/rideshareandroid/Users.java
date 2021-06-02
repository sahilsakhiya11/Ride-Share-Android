package com.example.rideshareandroid;

public class Users {

    public String name;
    public String image;
    public String status;
    public String thumbnail;



    public Users(){

    }

    public Users(String name, String image, String status, String thumbnail) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumbnail;
    }

    public void setThumb_image(String thumb_image) {
        this.thumbnail = thumbnail;
    }

}