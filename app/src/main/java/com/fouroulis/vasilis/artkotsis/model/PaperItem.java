package com.fouroulis.vasilis.artkotsis.model;

import java.io.Serializable;

/**
 * Created by Vasilis Fouroulis on 19/5/2018.
 */
public class PaperItem implements Serializable {

    private int id;
    private String title;
    private String bytes;
    private String date;
    private String time;
    private String image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBytes() {
        return bytes;
    }

    public String getTime() {
        return time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public PaperItem(String title, int id, String bytes, String date, String time, String image ) {
        this.id = id;
        this.bytes = bytes;
        this.date = date;
        this.title = title;
        this.time = time;
        this.image = image;
    }
}
