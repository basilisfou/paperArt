package com.fouroulis.vasilis.artkotsis.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Vasilis Fouroulis on 19/5/2018.
 */
public class PaperItem implements Serializable {

    private int id;
    private String gramsPerSquareMetre;
    private String bit;
    private String dpi;
    private String color;
    private String resolution;
    private String bytes;
    private String paperType;
    private String scanPrinter;
    private String dateTime;
    private String serialNumber;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getGramsPerSquareMetre() {
        return gramsPerSquareMetre;
    }

    public void setGramsPerSquareMetre(String gramsPerSquareMetre) {
        this.gramsPerSquareMetre = gramsPerSquareMetre;
    }

    public String getBit() {
        return bit;
    }

    public void setBit(String bit) {
        this.bit = bit;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public String getScanPrinter() {
        return scanPrinter;
    }

    public void setScanPrinter(String scanPrinter) {
        this.scanPrinter = scanPrinter;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public PaperItem(int id,  String gramsPerSquareMetre, String bit, String dpi, String color, String resolution, String bytes, String paperType, String scanPrinter, String dateTime, String serialNumber, String image) {
        this.id = id;
        this.gramsPerSquareMetre = gramsPerSquareMetre;
        this.bit = bit;
        this.dpi = dpi;
        this.color = color;
        this.resolution = resolution;
        this.bytes = bytes;
        this.paperType = paperType;
        this.scanPrinter = scanPrinter;
        this.dateTime = dateTime;
        this.serialNumber = serialNumber;
        this.image = image;
    }
}
