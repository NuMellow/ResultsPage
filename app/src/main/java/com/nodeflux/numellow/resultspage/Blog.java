package com.nodeflux.numellow.resultspage;

import java.util.ArrayList;

/**
 * Created by cmmuk_000 on 11/25/2016.
 */

//Class that represents a blog. in the case of the Career app it would represent a table such as course or University
public class Blog {

    //represents the attributes of database object e.g Name of course in course table
    private String title;
    private String description;
    private String image;
    private Child Child;

    public Blog()
    {

    }


    //CONSTRUCTOR
    public Blog(String description, String image, String title, Child child) {
        this.description = description;
        this.image = image;
        this.title = title;
        this.Child = child;
    }

    //GETTERS AND SETTER FOR THE PRIVATE VARIABLES
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChild(Child child)
    {
        this.Child = child;
    }

    public Child getChild()
    {
        return Child;
    }
}
