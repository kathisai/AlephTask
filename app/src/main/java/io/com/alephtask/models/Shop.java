package io.com.alephtask.models;

import java.util.ArrayList;

import io.com.alephtask.R;

/**
 * Created by skathi on 3/8/2018.
 */

public class Shop {
    private String name;
    private String distance;
    private String visitedCount;
    private String totalCount;
    private int imageResorce;

    public Shop(String name, String distance, String visitedCount, String totalCount, int imageResorce) {
        this.name = name;
        this.distance = distance;
        this.visitedCount = visitedCount;
        this.totalCount = totalCount;
        this.imageResorce = imageResorce;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(String visitedCount) {
        this.visitedCount = visitedCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public int getImageResorce() {
        return imageResorce;
    }

    public void setImageResorce(int imageResorce) {
        this.imageResorce = imageResorce;
    }


    public static ArrayList<Shop> createContactsList(int numContacts) {
        ArrayList<Shop> contacts = new ArrayList<Shop>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Shop("Name","30","40","100", R.drawable.ic_send_black_24dp));
        }

        return contacts;
    }
}
