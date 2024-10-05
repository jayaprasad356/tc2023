package com.telugucalendar.telugupanchangamr.Model;

public class SliderData {

    // image url is used to
    // store the url of image
    private String imgUrl,link;

    // Constructor method.
    public SliderData(String imgUrl , String link) {
        this.imgUrl = imgUrl;
        this.link = link;
    }



    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }
    public String getLink() {
        return link;
    }

    // Setter method
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public void setLink(String link) {
        this.link = link;
    }
}
