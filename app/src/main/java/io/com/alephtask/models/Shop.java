package io.com.alephtask.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import io.com.alephtask.R;

/**
 * Created by skathi on 3/8/2018.
 */

public class Shop implements Parcelable {
    public static final Parcelable.Creator<Shop> CREATOR = new Parcelable.Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel source) {
            return new Shop(source);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
    private String name;
    private String distance;
    private int visitedCount;
    private int totalCount;
    private int imageResorce;
    private int type;

    public Shop(String name, String distance, int visitedCount, int totalCount, int imageResorce, int type) {
        this.name = name;
        this.distance = distance;
        this.visitedCount = visitedCount;
        this.totalCount = totalCount;
        this.imageResorce = imageResorce;
        this.type = type;
    }

    protected Shop(Parcel in) {
        this.name = in.readString();
        this.distance = in.readString();
        this.visitedCount = in.readInt();
        this.totalCount = in.readInt();
        this.imageResorce = in.readInt();
        this.type = in.readInt();
    }

    public static ArrayList<Shop> createContactsList(int numContacts, int type) {
        ArrayList<Shop> contacts = new ArrayList<Shop>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Shop("Name One", "30", 40, 100, R.drawable.ic_color_paint_black_24dp, type));
        }

        return contacts;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getVisitedCount() {
        return visitedCount;
    }

    public void setVisitedCount(int visitedCount) {
        this.visitedCount = visitedCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getImageResorce() {
        return imageResorce;
    }

    public void setImageResorce(int imageResorce) {
        this.imageResorce = imageResorce;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.distance);
        dest.writeInt(this.visitedCount);
        dest.writeInt(this.totalCount);
        dest.writeInt(this.imageResorce);
        dest.writeInt(this.type);
    }
}
