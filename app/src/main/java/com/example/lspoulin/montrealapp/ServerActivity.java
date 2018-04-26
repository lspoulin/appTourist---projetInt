package com.example.lspoulin.montrealapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class ServerActivity extends AppCompatActivity {
    public static final boolean LOCAL_SERVER = false;
    public static final String CONTROLLEUR_ENTRY_POINT = "https://apptouristprojetint.000webhostapp.com/PHP/";
    public static final String CONTROLLEUR_LANDMARK_ENDPOINT = "activityControleurJSON.php";
    public static final String CONTROLLEUR_LOCAL_ENTRY_POINT = "http://10.0.2.2:8888/ProjetFinal/PHP/";

    public static final String SERVICE = "com.example.lspoulin.montrealapp.ServerActivity.service";

    public static final String SERVICE_LIST_LANDMARK = "com.example.lspoulin.montrealapp.ServerActivity.service.listlandmark";
    public static final String SERVICE_LIST_LANDMARK_DUMMY_DATA = "com.example.lspoulin.montrealapp.ServerActivity.service.listlandmarkdummydata";
    public static final String SERVICE_LIST_LANDMARK_ORDER_BY_DISTANCE = "com.example.lspoulin.montrealapp.ServerActivity.service.listlandmarkorderbydistance";
    public static final String SERVICE_LIST_LANDMARK_WITH_TAGS = "com.example.lspoulin.montrealapp.ServerActivity.service.listlandmarkwithtags";
    public static final String LANDMARK_LIST = "com.example.lspoulin.montrealapp.ServerActivity.service.listactivity";
    public static final String PARAM_LANDMARK_TAGS = "com.example.lspoulin.montrealapp.ServerActivity.service.paramlandmartags";


    public static final String SERVICE_GET_LANDMARK = "com.example.lspoulin.montrealapp.ServerActivity.service.getlandmark";
    public static final String SERVICE_GET_LANDMARK_DUMMY_DATA = "com.example.lspoulin.montrealapp.ServerActivity.service.getlandmarkdummydata";
    public static final String PARAM_LANDMARK_ID = "com.example.lspoulin.montrealapp.ServerActivity.service.paramlandmarkid";

    public static final String SERVICE_LOGIN = "com.example.lspoulin.montrealapp.ServerActivity.service.login";
    public static final String SERVICE_LOGIN_DUMMY_DATA = "com.example.lspoulin.montrealapp.ServerActivity.service.logindummydata";
    public static final String PARAM_LOGIN_USER = "com.example.lspoulin.montrealapp.ServerActivity.service.loginuser";
    public static final String PARAM_LOGIN_PASSWORD = "com.example.lspoulin.montrealapp.ServerActivity.service.loginpassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String dataTransmited = intent.getStringExtra(SERVICE);
        if (dataTransmited != null)
            switch (dataTransmited) {
                case SERVICE_GET_LANDMARK:
                    int id = intent.getIntExtra(PARAM_LANDMARK_ID, 0);
                    getLandmark(id);
                    break;
                case SERVICE_GET_LANDMARK_DUMMY_DATA:
                    getLandmark();
                    break;
                case SERVICE_LIST_LANDMARK_DUMMY_DATA:
                    listLandmark();
                    break;
                case SERVICE_LIST_LANDMARK:
                    listerLandmark();
                    break;
                case SERVICE_LOGIN_DUMMY_DATA:
                    String user = intent.getStringExtra(PARAM_LOGIN_USER);
                    String password = intent.getStringExtra(PARAM_LOGIN_PASSWORD);
                    loginDummy(user, password);
                    break;
                case SERVICE_LIST_LANDMARK_ORDER_BY_DISTANCE:
                    attemptToGPS();
                    break;
                case SERVICE_LIST_LANDMARK_WITH_TAGS:
                    String tags = intent.getStringExtra(PARAM_LANDMARK_TAGS);
                    listerLandmarkWithTags(tags);
                    break;
                default:
                    resultNotOk();
                    break;
            }
        else
            resultNotOk();
    }

    private void listerLandmarkWithTags(final String tags) {
            StringRequest requete = new StringRequest(Request.Method.POST, getControllerLandmark(),
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

                                        Landmark l = new Landmark(unLandmark.getInt("id"),
                                                unLandmark.getString("title"),
                                                unLandmark.getString("description"),
                                                unLandmark.getString("address"),
                                                (float)unLandmark.getDouble("latitude"),
                                                (float) unLandmark.getDouble("longitude"),
                                                unLandmark.getString("url"),
                                                (float)unLandmark.getDouble("price"),
                                                (float)unLandmark.getDouble("distanceKM"),
                                                "",
                                                unLandmark.getString("tags"),
                                                unLandmark.getBoolean("liked"));
                                        l.setImage(getDrawableBitmapFromJSON(unLandmark.getString("image")));
                                        landmarks.add(l);
                                    }
                                    Intent result = new Intent();
                                    result.putParcelableArrayListExtra(ServerActivity.LANDMARK_LIST, landmarks);
                                    resultOk(result);
                                }
                                else{
                                    resultNotOk();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                resultNotOk();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            resultNotOk();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    // Les parametres pour POST
                    params.put("action", "listerAvecTags");
                    params.put("tags", tags);
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(requete);
        }


    private void getLandmark() {
        listLandmark();
    }

    private void getLandmark(final int id){
        StringRequest requete = new StringRequest(Request.Method.POST, getControllerLandmark(),
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

                                    Landmark l = new Landmark(unLandmark.getInt("id"),
                                            unLandmark.getString("title"),
                                            unLandmark.getString("description"),
                                            unLandmark.getString("address"),
                                            (float)unLandmark.getDouble("latitude"),
                                            (float) unLandmark.getDouble("longitude"),
                                            unLandmark.getString("url"),
                                            (float)unLandmark.getDouble("price"),
                                            (float)unLandmark.getDouble("distanceKM"),
                                            "",
                                            unLandmark.getString("tags"),
                                            unLandmark.getBoolean("liked"));
                                    l.setImage(getDrawableBitmapFromJSON(unLandmark.getString("image")));
                                    landmarks.add(l);
                                }
                                Intent result = new Intent();
                                result.putParcelableArrayListExtra(ServerActivity.LANDMARK_LIST, landmarks);
                                resultOk(result);
                            }
                            else{
                                resultNotOk();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            resultNotOk();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resultNotOk();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "listerParId");
                params.put("id", id+"");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);

    }

    private void attemptToGPS() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listerLandmarkByDistance(45.5519, -73.6431);//coordonnee du college ahuntsic

        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                resultNotOk();
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        listerLandmarkByDistance(location.getLatitude(), location.getLongitude());

                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });*/
    }

    private void listerLandmarkByDistance(final double latitude, final double longitude) {
        StringRequest requete = new StringRequest(Request.Method.POST, getControllerLandmark(),
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
                                    Landmark l = new Landmark(unLandmark.getInt("id"),
                                            unLandmark.getString("title"),
                                            unLandmark.getString("description"),
                                            unLandmark.getString("address"),
                                            (float)unLandmark.getDouble("latitude"),
                                            (float) unLandmark.getDouble("longitude"),
                                            unLandmark.getString("url"),
                                            (float)unLandmark.getDouble("price"),
                                            (float)unLandmark.getDouble("distanceKM"),
                                            "",
                                            unLandmark.getString("tags"),
                                            unLandmark.getBoolean("liked")
                                    );
                                    l.setImage(getDrawableBitmapFromJSON(unLandmark.getString("image")));
                                    landmarks.add(l);
                                }
                                Intent result = new Intent();
                                result.putParcelableArrayListExtra(ServerActivity.LANDMARK_LIST, landmarks);
                                resultOk(result);
                            }
                            else{
                                resultNotOk();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            resultNotOk();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resultNotOk();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "listerParDistance");
                params.put("latitude", latitude+"");
                params.put("longitude", longitude+"");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }

    public Drawable getDrawableBitmapFromJSON(String encodedImage){
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Drawable d = new BitmapDrawable(getResources(),decodedByte);
        return d;
    }


    public void listerLandmark() {
        StringRequest requete = new StringRequest(Request.Method.POST, getControllerLandmark(),
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

                                    Landmark l = new Landmark(unLandmark.getInt("id"),
                                            unLandmark.getString("title"),
                                            unLandmark.getString("description"),
                                            unLandmark.getString("address"),
                                            (float)unLandmark.getDouble("latitude"),
                                            (float) unLandmark.getDouble("longitude"),
                                            unLandmark.getString("url"),
                                            (float)unLandmark.getDouble("price"),
                                            (float)unLandmark.getDouble("distanceKM"),
                                            "",
                                            unLandmark.getString("tags"),
                                            unLandmark.getBoolean("liked"));
                                    l.setImage(getDrawableBitmapFromJSON(unLandmark.getString("image")));
                                    landmarks.add(l);
                                }
                                Intent result = new Intent();
                                result.putParcelableArrayListExtra(ServerActivity.LANDMARK_LIST, landmarks);
                                resultOk(result);
                            }
                            else{
                                resultNotOk();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            resultNotOk();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resultNotOk();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "lister");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }

    private String getControllerLandmark() {
        if (LOCAL_SERVER)
            return CONTROLLEUR_LOCAL_ENTRY_POINT + CONTROLLEUR_LANDMARK_ENDPOINT;
        else
            return CONTROLLEUR_ENTRY_POINT + CONTROLLEUR_LANDMARK_ENDPOINT;
    }


    private void listLandmark() {
        Intent result = new Intent();

        ArrayList<Landmark> landmarkList = new ArrayList<Landmark>();
        Landmark l = new Landmark();

        landmarkList.add(new Landmark(
                1,
                "Stade Olympiqe",
                "Le stade olympique de Montréal est un stade omnisports couvert d'une capacité maximale de 65 000 places, conçu par l'architecte français Roger Taillibert et construit pour les Jeux olympiques d'été de 1976.",
                "4545, avenue Pierre-de-Coubertin",
                45.5546f,
                -73.5243f,
                "http://parcolympique.qc.ca",
                0.0f,
                1.0f,
                null,
                "sport",
                false
        ));

        result.putParcelableArrayListExtra(this.LANDMARK_LIST, landmarkList);
        resultOk(result);
    }

    private void loginDummy(String user, String password){
        if (user.equals("root") && password.equals("pass")){
            resultOk(new Intent());
        }
        else
            resultNotOk();
    }

    private void resultNotOk(){
        Intent result = new Intent();
        setResult(RESULT_CANCELED, result);
        result.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ServerActivity.this.finish();
        overridePendingTransition(0, 0);
    }

    private void resultOk(Intent result){
        setResult(RESULT_OK, result);
        result.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        ServerActivity.this.finish();
        overridePendingTransition(0, 0);
    }

}
