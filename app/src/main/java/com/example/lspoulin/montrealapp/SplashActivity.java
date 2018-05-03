package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    public final static String LANDMARK_LIST = "com.example.lspoulin.montrealapp.SplashActivity.landmarklist";
    private ArrayList<Landmark> landmarkArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startHeavyProcessing();

    }

    private void startHeavyProcessing(){
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            //some heavy processing resulting in a Data String
                final String tags = "";
                StringRequest requete = new StringRequest(Request.Method.POST, ServerManager.getControllerLandmark(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    Log.d("RESULTAT", response);
                                    int i;
                                    JSONArray jsonResponse = new JSONArray(response);
                                    String msg = jsonResponse.getString(0);
                                    if(msg.equals("OK")){
                                        JSONObject unLandmark;
                                        ArrayList<Landmark> landmarks = new ArrayList<Landmark>();
                                        for(i=1;i<jsonResponse.length();i++){
                                            unLandmark=jsonResponse.getJSONObject(i);
                                            Landmark l = getLandmarkFromResponse(jsonResponse.getJSONObject(i));
                                            landmarks.add(l);
                                        }
                                        landmarkArrayList = landmarks;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                finally {
                                    finish();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                finish();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        // Les parametres pour POST
                        params.put("action", "lister");
                        if(UserManager.getInstance().isLoggin())
                            params.put("userid", UserManager.getInstance().getUser().getId()+"");
                        return params;
                    }
                };
                Log.d("requete url" ,requete.getUrl());
                Volley.newRequestQueue(SplashActivity.this).add(requete);
                return null;
        }
    }

    @Override
    public void finish(){
        Intent result = new Intent(SplashActivity.this, MainActivity.class);
        result.putParcelableArrayListExtra(LANDMARK_LIST, landmarkArrayList);
        startActivity(result);

        super.finish();
    }

    private Landmark getLandmarkFromResponse(JSONObject unLandmark) throws JSONException{
        Landmark l = new Landmark(unLandmark.getInt("id"),
                unLandmark.getString("title"),
                unLandmark.getString("description"),
                unLandmark.getString("address"),
                (float) unLandmark.getDouble("latitude"),
                (float) unLandmark.getDouble("longitude"),
                unLandmark.getString("url"),
                (float) unLandmark.getDouble("price"),
                (float) unLandmark.getDouble("distanceKM"),
                unLandmark.getString("image"),
                unLandmark.getString("tags"),
                unLandmark.getInt("liked") != 0);
        DrawableManager.getInstance().loadImage(l.getImage(), getApplicationContext());

        return l;
    }


}
