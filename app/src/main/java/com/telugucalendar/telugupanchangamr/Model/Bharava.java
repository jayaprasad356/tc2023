package com.telugucalendar.telugupanchangamr.Model;

public class Bharava {
    String id,time,description;

    public Bharava(String id, String time, String description) {
        this.id = id;
        this.time = time;
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

