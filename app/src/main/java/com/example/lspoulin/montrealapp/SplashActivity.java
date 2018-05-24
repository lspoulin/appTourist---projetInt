package com.example.lspoulin.montrealapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    public final static String LANDMARK_LIST = "com.example.lspoulin.montrealapp.SplashActivity.landmarklist";
    public final static String PREFERENCES = "com.example.lspoulin.montrealapp.SplashActivity.preferences";
    public final static String PREFERENCES_USERID = "com.example.lspoulin.montrealapp.SplashActivity.userid";
    public final static String PREFERENCES_USERNAME = "com.example.lspoulin.montrealapp.SplashActivity.username";
    public final static String PREFERENCES_USEREMAIL = "com.example.lspoulin.montrealapp.SplashActivity.useremail";
    public final static String PREFERENCES_USERPREFERENCES = "com.example.lspoulin.montrealapp.SplashActivity.userpreferences";

    private ArrayList<Landmark> landmarkArrayList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = this.getSharedPreferences(SplashActivity.PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String username = preferences.getString(SplashActivity.PREFERENCES_USERNAME, "");

        if(!username.equals("")){
            User user = new User();
            user.setId(preferences.getInt(SplashActivity.PREFERENCES_USERID, -1));
            user.setName(username);
            user.setEmail(preferences.getString(SplashActivity.PREFERENCES_USEREMAIL, ""));
            user.setPreferences(preferences.getString(SplashActivity.PREFERENCES_USERPREFERENCES, ""));
            UserManager.getInstance().setUser(user, this);
        }
        startHeavyProcessing();

    }

    private void startHeavyProcessing(){
        new LongOperation().execute("");
    }

    private class LongOperation extends AsyncTask<String, Void, String> {
        @Override

        protected String doInBackground(String... params) {
            ApiHelper apiHelper = new ApiHelper(getApplicationContext());

            apiHelper.loadLandmarks("", SplashActivity.this, new Callback() {
                @Override
                public void methodToCallBack(Object object) {
                    landmarkArrayList = (ArrayList<Landmark>) object;
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
