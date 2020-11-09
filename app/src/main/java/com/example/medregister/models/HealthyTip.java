package com.example.medregister.models;

public class HealthyTip {

    private String tipTitle;

    private String tipText;

    private int tipImage;

    public HealthyTip(int tipImage, String tipTitle, String tipText) {
        this.tipTitle = tipTitle;
        this.tipText = tipText;
        this.tipImage = tipImage;
    }

    public String getTipTitle() {
        return tipTitle;
    }

    public void setTipTitle(String tipTitle) {
        this.tipTitle = tipTitle;
    }

    public String getTipText() {
        return tipText;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
    }

    public int getTipImage() {
        return tipImage;
    }

    public void setTipImage(int tipImage) {
        this.tipImage = tipImage;
    }
}
