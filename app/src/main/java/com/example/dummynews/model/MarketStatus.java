package com.example.dummynews.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarketStatus {

    @SerializedName("indices")
    @Expose
    private Indices indices;

    public Indices getIndices() {
        return indices;
    }

    public void setIndices(Indices indices) {
        this.indices = indices;
    }

}