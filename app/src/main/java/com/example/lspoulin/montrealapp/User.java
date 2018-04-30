package com.example.lspoulin.montrealapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lspoulin on 2018-04-24.
 */

public class User  implements Serializable, Parcelable {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    private String email;
    private static String preferences;

    public User(){}

    public User(int id, String name, String email, String preferences){
        this.id = id;
        this.name = name;
        this.email = email;
        this.preferences = preferences;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(preferences);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel parcel) {
            User user = new User();
            user.setId(parcel.readInt());
            user.setName(parcel.readString());
            user.setEmail(parcel.readString());
            user.setPreferences(parcel.readString());

            return user;
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };
}
