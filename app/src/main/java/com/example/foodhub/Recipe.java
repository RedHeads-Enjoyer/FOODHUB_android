package com.example.foodhub;

import android.net.Uri;

import java.util.ArrayList;

public class Recipe {
    private String name, description, userID;
    private ArrayList<Step> steps;
    private Integer like, dislike, views;
    private String image, recipeID;
    private ArrayList<String> whoLiked;
    private ArrayList<String> whoWatched;
    private ArrayList<String> whoDisliked;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Recipe(String name, String description, String userID, ArrayList<Step> steps, Integer like, Integer dislike, Integer views, String image, String recipeID, ArrayList<String> whoLiked, ArrayList<String> whoWatched, ArrayList<String> whoDisiked) {
        this.name = name;
        this.description = description;
        this.userID = userID;
        this.steps = steps;
        this.like = like;
        this.dislike = dislike;
        this.views = views;
        this.image = image;
        this.recipeID = recipeID;
        this.whoLiked = whoLiked;
        this.whoWatched = whoWatched;
        this.whoDisliked = whoDisiked;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public ArrayList<String> getWhoLiked() {
        return whoLiked;
    }

    public void setWhoLiked(ArrayList<String> whoLiked) {
        this.whoLiked = whoLiked;
    }

    public ArrayList<String> getWhoWatched() {
        return whoWatched;
    }

    public void setWhoWatched(ArrayList<String> whoWatched) {
        this.whoWatched = whoWatched;
    }

    public ArrayList<String> getWhoDisiked() {
        return whoDisliked;
    }

    public void setWhoDisiked(ArrayList<String> whoDisiked) {
        this.whoDisliked = whoDisiked;
    }
}
