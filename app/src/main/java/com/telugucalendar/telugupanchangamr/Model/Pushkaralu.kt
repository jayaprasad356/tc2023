package com.telugucalendar.telugupanchangamr.Model;

public class Pushkaralu {
    String id, pushkaralu_id, sub_title, sub_description;

    // Default constructor
    public Pushkaralu() {}

    // Parameterized constructor
    public Pushkaralu(String id, String pushkaralu_id, String sub_title, String sub_description) {
        this.id = id;
        this.pushkaralu_id = pushkaralu_id;
        this.sub_title = sub_title;
        this.sub_description = sub_description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPushkaralu_id() {
        return pushkaralu_id;
    }

    public void setPushkaralu_id(String pushkaralu_id) {
        this.pushkaralu_id = pushkaralu_id;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getSub_description() {
        return sub_description;
    }

    public void setSub_description(String sub_description) {
        this.sub_description = sub_description;
    }
}
