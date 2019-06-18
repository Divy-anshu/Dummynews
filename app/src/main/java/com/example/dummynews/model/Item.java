package com.example.dummynews.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Item {
    @SerializedName("articles")
    ArrayList<Articles> arrayList;

    public ArrayList<Articles> getArrayList() {
        return arrayList;
    }


}
