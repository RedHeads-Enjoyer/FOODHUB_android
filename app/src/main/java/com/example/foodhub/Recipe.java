package com.example.foodhub;

import android.net.Uri;

import com.example.foodhub.Add.Step;

import java.util.ArrayList;
import java.util.Date;

public class Recipe {
    private String name, description, userID;
    private ArrayList<Step> steps;
    private Integer like, dislike, views;
    private Uri image;

    public Recipe(String name, String description, String userID, ArrayList<Step> steps, Integer like, Integer dislike, Integer views, Uri image) {
        this.name = name;
        this.description = description;
        this.userID = userID;
        this.steps = steps;
        this.like = like;
        this.dislike = dislike;
        this.views = views;
        this.image = image;
    }

    public Recipe() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getDislike() {
        return dislike;
    }

    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }
}
