package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import java.util.List;
import java.util.Map;

import static com.example.lspoulin.montrealapp.ServerManager.getControllerLandmark;

public class ResultActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private List<Landmark> landmarkList;
    private ResultActivity.CustomAdapter customAdapter;
    ListView mainListView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        Intent i = getIntent();
        String key = i.getStringExtra("KeyWord");
        String sort = i.getStringExtra("Preference");

        landmarkList = new ArrayList<Landmark>();



        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
        mainListView = (ListView) findViewById(R.id.listAffResult);
        customAdapter = new ResultActivity.CustomAdapter();
        DrawableManager.getInstance().setListener(customAdapter);
        mainListView.setAdapter(customAdapter);

        btnBack = (ImageButton)findViewById(R.id.btnResultBack) ;

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if(sort.equals("Distance")){

            listerLandmarkByDistance(45.5519, -73.6431, key);

        }else{


            loadLandmarks(key);
        }


        mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadLandmarkById(landmarkList.get(position).getId());
            }
        });



    }



    private void listerLandmarkByDistance(final double latitude, final double longitude, String key) {

        progress.setVisibility(View.VISIBLE);

        final String tags = key;
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
                                    //l.setImage(getDrawableBitmapFromJSON(unLandmark.getString("image")));

                                    if((unLandmark.getString("tags").contains(tags)) || (unLandmark.getString("title").contains(tags)) || (unLandmark.getString("description").contains(tags))) {

                                        landmarks.add(l);

                                    }else{

                                    }
                                }


                                landmarkList = landmarks;
                                customAdapter.notifyDataSetChanged();
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
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resultNotOk();
                        progress.setVisibility(View.GONE);
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


    private void resultNotOk(){
        Intent result = new Intent();
        setResult(RESULT_CANCELED, result);
        result.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //ServerActivity.this.finish();
        overridePendingTransition(0, 0);
    }

    private void resultOk(Intent result){
        setResult(RESULT_OK, result);
        result.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        //ServerActivity.this.finish();
        overridePendingTransition(0, 0);
    }


    private void loadLandmarks(String tag) {
       progress.setVisibility(View.VISIBLE);

        final String tags = tag;
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

                                    if((unLandmark.getString("tags").contains(tags)) || (unLandmark.getString("title").contains(tags)) || (unLandmark.getString("description").contains(tags))) {
                                        Landmark l = getLandmarkFromResponse(jsonResponse.getJSONObject(i));
                                        landmarks.add(l);

                                    }else{

                                    }
                                }

                                landmarkList = landmarks;
                                customAdapter.notifyDataSetChanged();
                            }
                            else{
                                //resultNotOk();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //resultNotOk();
                        }
                        finally {
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //resultNotOk();
                        progress.setVisibility(View.GONE);
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
        Volley.newRequestQueue(this).add(requete);
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


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return landmarkList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.sample_row, null);
            TextView title = (TextView) view.findViewById(R.id.txtTitre);
            TextView adresse = (TextView) view.findViewById(R.id.txtAdresse);
            ImageView image = (ImageView) view.findViewById(R.id.imgSmall);

            Landmark l = landmarkList.get(i);
            title.setText(l.getTitle());
            adresse.setText(l.getAddress());
            Bitmap bitmap = DrawableManager.getInstance().getDrawable(l.getImage());
            Drawable imageDrawable = new BitmapDrawable(getResources(), bitmap);
            image.setImageDrawable(imageDrawable);
            return view;
        }
    }

    private void showLandmark(final Landmark landmark) {

        Intent i;
        i  = new Intent(ResultActivity.this, LandmarkActivity.class);
        i.putExtra(LandmarkActivity.LANDMARK, (Parcelable) landmark);
        startActivity(i);
    }


    private void loadLandmarkById(final int id){
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
                                    Landmark l = getLandmarkFromResponse(jsonResponse.getJSONObject(i));
                                    landmarks.add(l);
                                }
                                showLandmark(landmarks.get(0));
                            }
                            else{
                                //resultNotOk();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //resultNotOk();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //resultNotOk();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "listerParId");
                params.put("id", id+"");
                if(UserManager.getInstance().isLoggin())
                    params.put("userid", UserManager.getInstance().getUser().getId()+"");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);

    }

}
