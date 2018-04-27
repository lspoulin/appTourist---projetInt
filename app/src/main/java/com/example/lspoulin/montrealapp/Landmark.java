package com.example.lspoulin.montrealapp;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Random;

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
    private String tags;

    public double calculateDistanceKM(int latitude, int longitude){
        return 111.111 *
                Math.toDegrees((Math.acos(Math.cos(Math.toRadians(this.latitude))
                        * Math.cos(Math.toRadians(latitude))
                        * Math.cos(Math.toRadians(this.longitude - longitude))
                        + Math.sin(Math.toRadians(this.latitude))
                        * Math.sin(Math.toRadians(latitude)))));
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    private boolean liked;

    public Drawable getImage() {
        return DrawableManager.getInstance().getDrawable(image);

    }

    public void setImage(Drawable drawable) {
        this.image = id + "";
        DrawableManager.getInstance().addDrawable(this.image, drawable);

    }

    public void setImage(String image) {
        this.image = image;

    }

    //TODO : add tumbnail and normal images
    private float price;

    private String image;

    public float getDistanceKM() {
        return distanceKM;
    }

    public void setDistanceKM(float distanceKM) {
        this.distanceKM = distanceKM;
    }

    private float distanceKM;

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
                    String url, float price, float distanceKM, String image, String tags, boolean liked){
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
        this.price = price;
        this.distanceKM = distanceKM;
        this.image = image;
        this.tags = tags;
        this.liked = liked;
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
        parcel.writeFloat(distanceKM);
        parcel.writeString(image);
        parcel.writeByte((byte)(liked?1:0));
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
            landmark.setDistanceKM(parcel.readFloat());
            landmark.setImage(parcel.readString());
            landmark.setLiked((parcel.readByte()==0)?false:true);

            return landmark;
        }

        @Override
        public Landmark[] newArray(int i) {
            return new Landmark[i];
        }
    };
}
