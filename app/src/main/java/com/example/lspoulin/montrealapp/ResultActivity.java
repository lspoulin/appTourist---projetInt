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
        final String tags = key.toLowerCase();
        Map<String, String> params = new HashMap<>();
        // Les parametres pour POST
        params.put("action", "listerParDistance");
        params.put("latitude", latitude+"");
        params.put("longitude", longitude+"");
        if(UserManager.getInstance().isLoggin() && UserManager.getInstance().getUser() != null) {
            params.put("userid", UserManager.getInstance().getUser().getId() + "");
        }

        ApiManager<Landmark> apiLandmark;
        try {
            apiLandmark = new ApiManager<Landmark>(Landmark.class);
            Map<String, String> parameters = new HashMap<String,String>();
            parameters.put("action", "lister");
            if(UserManager.getInstance().isLoggin())
                parameters.put("userid", UserManager.getInstance().getUser().getId()+"");
            apiLandmark.postReturnMappableArray(ApiManager.getControllerLandmark(), this, parameters, new Callback() {
                @Override
                public void methodToCallBack(Object object) {
                    ArrayList<Landmark> temps = (ArrayList<Landmark>) object;
                    ArrayList<Landmark> filtered = new ArrayList<Landmark>();
                    for(Landmark landmark: temps) {
                        if((landmark.getTags().toLowerCase().contains(tags)) || (landmark.getTitle().toLowerCase().contains(tags)) || (landmark.getDescription().toLowerCase().contains(tags))) {

                            filtered.add(landmark);
                            DrawableManager.getInstance().loadImage(landmark.getImage(), ResultActivity.this);
                        }
                    }
                    landmarkList = filtered;
                    customAdapter.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);
                    Intent result = new Intent();
                    result.putParcelableArrayListExtra(ServerActivity.LANDMARK_LIST, filtered);
                    resultOk(result);
                }
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void resultOk(Intent result){
        setResult(RESULT_OK, result);
        result.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

    }


    private void loadLandmarks(final String tag) {
        progress.setVisibility(View.VISIBLE);

        ApiManager<Landmark> apiLandmark;
        try {
            apiLandmark = new ApiManager<Landmark>(Landmark.class);
            Map<String, String> parameters = new HashMap<String,String>();
            parameters.put("action", "lister");
            if(UserManager.getInstance().isLoggin())
                parameters.put("userid", UserManager.getInstance().getUser().getId()+"");
            apiLandmark.postReturnMappableArray(ApiManager.getControllerLandmark(), this, parameters, new Callback() {
                @Override
                public void methodToCallBack(Object object) {
                    ArrayList<Landmark> temps = (ArrayList<Landmark>) object;
                    ArrayList<Landmark> filtered = new ArrayList<Landmark>();
                    for(Landmark landmark: temps) {
                        if(landmark.getTags().contains(tag)) {
                            filtered.add(landmark);
                            DrawableManager.getInstance().loadImage(landmark.getImage(), ResultActivity.this);
                        }
                    }
                    landmarkList = filtered;
                    customAdapter.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);
                }
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
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


    private void loadLandmarkById(int id){
        ApiManager<Landmark> apiLandmark;
        try {
            apiLandmark = new ApiManager<Landmark>(Landmark.class);
            Map<String, String> parameters = new HashMap<String,String>();
            parameters.put("action", "listerParId");
            parameters.put("id", id+"");
            if(UserManager.getInstance().isLoggin())
                parameters.put("userid", UserManager.getInstance().getUser().getId()+"");
            apiLandmark.postReturnMappable(ApiManager.getControllerLandmark(), this, parameters, new Callback() {
                @Override
                public void methodToCallBack(Object object) {
                    Landmark landmark = (Landmark) object;
                    DrawableManager.getInstance().loadImage(landmark.getImage(), ResultActivity.this);
                    showLandmark(landmark);
                }
            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

}
