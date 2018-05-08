package com.example.lspoulin.montrealapp;

import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lspoulin on 2018-05-08.
 */

public class ApiHelper {
    private ApiManager<Landmark> apiManagerLandmark;
    private ApiManager<User> apiManagerUser;

    public ApiHelper(){
        try{
            apiManagerLandmark = new ApiManager<Landmark>(Landmark.class);
            apiManagerUser = new ApiManager<User>(User.class);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void loadLandmarkById(int id, Context context, Callback callback){
        Map<String, String> parameters = new HashMap<String,String>();
        parameters.put("action", "listerParId");
        parameters.put("id", id+"");
        if(UserManager.getInstance().isLoggin())
            parameters.put("userid", UserManager.getInstance().getUser().getId()+"");
        apiManagerLandmark.postReturnMappable(ApiManager.getControllerLandmark(), context, parameters, callback);
    }

    public void loadLandmarks(final String tag, Context context, Callback callback) {
        Map<String, String> parameters = new HashMap<String,String>();
        parameters.put("action", "lister");
        if(UserManager.getInstance().isLoggin())
            parameters.put("userid", UserManager.getInstance().getUser().getId()+"");
        apiManagerLandmark.postReturnMappableArray(ApiManager.getControllerLandmark(), context, parameters, callback);
    }


    public void createNewUserAttempt(final String name, final String email, final String password, final String preferences, Context context, Callback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "enregistrer");
        params.put("name", name);
        params.put("password", ApiManager.md5(password));
        params.put("email", email);
        params.put("preferences", preferences);
        apiManagerUser.postReturnIdCreated(ApiManager.getControllerUser(), context, params, callback);
    }

    public void loginAttempt(final String username, final String password, Context context, Callback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "login");
        params.put("user", username);
        params.put("password", ApiManager.md5(password));
        apiManagerUser.postReturnMappable(ApiManager.getControllerUser(), context, params, callback);
    }

    public void landmarkUnliked(final int userid, final int landmarkid, Context context, Callback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "activityUnLiked");
        params.put("userid", String.valueOf(userid));
        params.put("activityid", String.valueOf(landmarkid));
        apiManagerLandmark.post(ApiManager.getControllerLandmark(), context, params, callback);
    }

    public void landmarkLiked(final int userid, final int landmarkid, Context context, Callback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "activityLiked");
        params.put("userid", String.valueOf(userid));
        params.put("activityid", String.valueOf(landmarkid));
        apiManagerLandmark.post(ApiManager.getControllerLandmark(), context, params, callback);
    }

    public void saveUser(Context context, Callback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("action", "maj");
        params.put("id", UserManager.getInstance().getUser().getId()+"");
        params.put("preferences", UserManager.getInstance().getUser().getPreferences());
        params.put("email", UserManager.getInstance().getUser().getEmail());
        params.put("name", UserManager.getInstance().getUser().getName());
        apiManagerUser.post(ApiManager.getControllerUser(), context, params, callback);
    }

    public void listerLandmarkByDistance(final double latitude, final double longitude, String tag, Context context, Callback callback) {
        final String tags = tag.toLowerCase();
        Map<String, String> params = new HashMap<>();
        params.put("action", "listerParDistance");
        params.put("latitude", latitude+"");
        params.put("longitude", longitude+"");
        if(UserManager.getInstance().isLoggin() && UserManager.getInstance().getUser() != null) {
            params.put("userid", UserManager.getInstance().getUser().getId() + "");
        }

        apiManagerLandmark.postReturnMappableArray(ApiManager.getControllerLandmark(), context, params, callback);

    }

}
