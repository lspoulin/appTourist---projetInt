package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
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
            ApiHelper apiHelper = new ApiHelper();

            apiHelper.loadLandmarks("", SplashActivity.this, new Callback() {
                @Override
                public void methodToCallBack(Object object) {
                    landmarkArrayList = (ArrayList<Landmark>) object;
                    for(Landmark landmark:landmarkArrayList){
                        DrawableManager.getInstance().loadImage(landmark.getImage(), SplashActivity.this);
                    }
                    finish();
                }
            });

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
}
