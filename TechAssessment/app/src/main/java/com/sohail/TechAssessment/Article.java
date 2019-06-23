package com.sohail.TechAssessment;

import java.io.Serializable;

/**
 * Created by Sohail Yasin on 6/21/2018.
 */

public class Article implements Serializable {
    private String name;
    private String DetailUrl;
    private String description;
    private String ImageURL;
    private String Writer;
    private String Date;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getImageURL() {
        return ImageURL;
    }
    public void setImageURL(String ImageURL) {
        this.ImageURL = ImageURL;
    }


    public String getWriter() {
        return Writer;
    }
    public void setWriter(String Writer) {
        this.Writer = Writer;
    }

    public String getDate() {
        return Date;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }



    public String getDetailUrl() {
        return DetailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.DetailUrl = detailUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
