package com.app.partyplanet.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewDataModel {

    @SerializedName("data")
    @Expose
    private ReviewData data;

    public ReviewData getData()
    {
        return data;
    }

    public void setData(ReviewData data)
    {
        this.data = data;
    }

    @SerializedName("status")
    @Expose
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("message")
    @Expose
    private String message;
}
