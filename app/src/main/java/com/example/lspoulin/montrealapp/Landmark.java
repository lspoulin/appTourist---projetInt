package com.example.lspoulin.montrealapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lspoulin on 2018-04-20.
 */
//Java Bean Landmark

public class Landmark implements Serializable, Parcelable {
    private int id;
    private String title;
    private String description;
    private String address;
    private float longitude;
    private float latitude;
    private String url;
    //TODO : add tumbnail and normal images
    private float price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Landmark(){

    }

    public Landmark(int id, String title, String description, String address, float latitude, float longitude,
                    String url, float price){
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(address);
        parcel.writeFloat(latitude);
        parcel.writeFloat(longitude);
        parcel.writeString(url);
        parcel.writeFloat(price);

    }

    public static final Parcelable.Creator<Landmark> CREATOR = new Creator<Landmark>() {

        @Override
        public Landmark createFromParcel(Parcel parcel) {
            Landmark landmark = new Landmark();
            landmark.setId(parcel.readInt());
            landmark.setTitle(parcel.readString());
            landmark.setDescription(parcel.readString());
            landmark.setAddress(parcel.readString());
            landmark.setLatitude(parcel.readFloat());
            landmark.setLongitude(parcel.readFloat());
            landmark.setUrl(parcel.readString());
            landmark.setPrice(parcel.readFloat());

            return landmark;
        }

        @Override
        public Landmark[] newArray(int i) {
            return new Landmark[i];
        }
    };
}
