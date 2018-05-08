package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;

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
