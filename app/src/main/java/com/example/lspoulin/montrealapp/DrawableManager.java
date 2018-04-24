package com.example.lspoulin.montrealapp;

import android.graphics.drawable.Drawable;

import java.util.HashMap;

/**
 * Created by lspoulin on 2018-04-24.
 */

public class DrawableManager {
    private static DrawableManager instance;
    private HashMap<String, Drawable> drawablelist;
    private DrawableManager(){
        drawablelist = new HashMap<String, Drawable>();
    }

    public static DrawableManager getInstance(){
        if(instance == null)
            instance = new DrawableManager();
        return instance;
    }

    public Drawable getDrawable(String key){
       return drawablelist.get(key);
    }

    public void addDrawable(String key, Drawable drawable){
        if (drawablelist.containsKey(key)){
            drawablelist.remove(key);
            drawablelist.put(key, drawable);
        }
        else
            drawablelist.put(key, drawable);
    }
}
