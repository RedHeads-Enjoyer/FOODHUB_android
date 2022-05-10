package com.example.foodhub;

import com.example.foodhub.Add.Step;

import java.util.ArrayList;
import java.util.Date;

public class Recipe {
    private String name, description;
    private ArrayList<Integer> rating;
    private Date date;
//    private ArrayList<Step> = new ArrayList;

    public Recipe() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getRating() {
        return rating;
    }

    public void setRating(ArrayList<Integer> rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Recipe(String name, ArrayList<Integer> rating, Date date) {
        this.name = name;
        this.rating = rating;
        this.date = date;
    }
}
