package com.example.lspoulin.montrealapp;

/**
 * Created by lspoulin on 2018-05-07.
 */

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;


public class ApiManager<T extends Mappable> {

    private final Constructor<? extends T> ctor;

    private T field;

    ApiManager(Class<? extends T> impl) throws NoSuchMethodException {
        this.ctor = impl.getConstructor();
    }

    /*public void myMethod() throws Exception {
        field = ctor.newInstance();
    }*/



    public static final boolean LOCAL_SERVER = true;
    public static final String BASE_URL = "https://apptouristprojetint.000webhostapp.com/PHP/";
    public static final String BASE_URL_PHOTO = "https://apptouristprojetint.000webhostapp.com/photos/";
    public static final String CONTROLLEUR_LANDMARK_ENDPOINT = "activityControleurJSON.php";
    public static final String CONTROLLEUR_USER_ENDPOINT = "userControleurJSON.php";
    public static final String LOCAL_BASE_URL = "http://10.0.2.2:8888/ProjetFinal/PHP/";
    public static final String LOCAL_BASE_URL_PHOTO = "http://10.0.2.2:8888/ProjetFinal/photos/";


    public static String getControllerLandmark() {
        if (LOCAL_SERVER)
            return LOCAL_BASE_URL + CONTROLLEUR_LANDMARK_ENDPOINT;
        else
            return BASE_URL + CONTROLLEUR_LANDMARK_ENDPOINT;
    }

    public static String getControllerUser() {
        if (LOCAL_SERVER)
            return LOCAL_BASE_URL + CONTROLLEUR_USER_ENDPOINT;
        else
            return BASE_URL + CONTROLLEUR_USER_ENDPOINT;
    }

    public static String getPhotoURL(String image) {
        if (LOCAL_SERVER)
            return LOCAL_BASE_URL_PHOTO + image;
        else
            return BASE_URL_PHOTO + image;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void postReturnMappableArray(String url, Context context, final Map<String, String> params, final Callback callback){
        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject jsonObject;
                                ArrayList<Mappable> objects = new ArrayList<Mappable>();
                                for(i=1;i<jsonResponse.length();i++){
                                    field = ctor.newInstance();
                                    field.mapJSON(jsonResponse.getJSONObject(i));
                                    objects.add(field);
                                }
                                callback.methodToCallBack(objects);
                            }
                            else{
                                callback.methodToCallBack(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.methodToCallBack(null);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.methodToCallBack(null);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        Volley.newRequestQueue(context).add(requete);

    }


    public void postReturnMappable(String url, Context context, final Map<String, String> params, final Callback callback){
        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject jsonObject;
                                ArrayList<Mappable> objects = new ArrayList<Mappable>();
                                for(i=1;i<jsonResponse.length();i++){
                                    field = ctor.newInstance();
                                    field.mapJSON(jsonResponse.getJSONObject(i));
                                    objects.add(field);
                                }
                                callback.methodToCallBack(objects.get(0));
                            }
                            else{
                                callback.methodToCallBack(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.methodToCallBack(null);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.methodToCallBack(null);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        Volley.newRequestQueue(context).add(requete);

    }

    public void postReturnIdCreated(String url, Context context, final Map<String, String> params, final Callback callback){
        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                callback.methodToCallBack((Integer)jsonResponse.getInt(1));
                            }
                            else{
                                callback.methodToCallBack(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.methodToCallBack(null);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.methodToCallBack(null);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };
        Volley.newRequestQueue(context).add(requete);

    }




}
