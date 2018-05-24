package com.example.lspoulin.montrealapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by lspoulin on 2018-04-26.
 */

public class UserManager {
    private User user;
    private static UserManager instance;
    private UserManager(){

    }
    public static UserManager getInstance(){
        if(instance == null)
            instance = new UserManager();
        return instance;
    }

    public boolean isLoggin(){
        return user != null;
    }

    public void setUser(User user, Context context){

        SharedPreferences preferences = context.getSharedPreferences(SplashActivity.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(SplashActivity.PREFERENCES_USERID, user.getId());
        editor.putString(SplashActivity.PREFERENCES_USERNAME, user.getName());
        editor.putString(SplashActivity.PREFERENCES_USEREMAIL, user.getEmail());
        editor.putString(SplashActivity.PREFERENCES_USERPREFERENCES, user.getPreferences());
        editor.commit();

        this.user = user;
    }

    public User getUser(){
        return user;
    }

}
