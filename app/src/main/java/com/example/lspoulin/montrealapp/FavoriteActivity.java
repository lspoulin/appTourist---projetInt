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

public class FavoriteActivity  extends AppCompatActivity {
    private ImageButton btnBack;
    private List<Landmark> landmarkList;
    private List<Landmark> landmarkListReceive;
    private FavoriteActivity.CustomAdapter customAdapter;
    ListView mainListView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        landmarkList = new ArrayList<Landmark>();

        Bundle bundle= getIntent().getExtras();
        ArrayList<Landmark> landmarkListReceive = bundle
                .getParcelableArrayList("Liste");

        landmarkList = landmarkListReceive;


        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
        mainListView = (ListView) findViewById(R.id.listAffResult);
        customAdapter = new FavoriteActivity.CustomAdapter();
        DrawableManager.getInstance().setListener(customAdapter);
        mainListView.setAdapter(customAdapter);



        btnBack = (ImageButton) findViewById(R.id.btnResultBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        customAdapter.notifyDataSetChanged();

        mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadLandmarkById(landmarkList.get(position).getId());
            }
        });

    }

    private void showLandmark(final Landmark landmark) {

        Intent i;
        i  = new Intent(FavoriteActivity.this, LandmarkActivity.class);
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
                    DrawableManager.getInstance().loadImage(landmark.getImage(), FavoriteActivity.this);
                    showLandmark(landmark);
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

}