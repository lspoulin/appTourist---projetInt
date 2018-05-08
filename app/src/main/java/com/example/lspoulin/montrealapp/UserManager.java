package com.example.lspoulin.montrealapp;

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

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

}
