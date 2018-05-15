package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity  extends AppCompatActivity {
    private ImageButton btnBack;
    private List<Landmark> landmarkList;
    private List<Landmark> landmarkListReceive;
    private FavoriteActivity.CustomAdapter customAdapter;
    ListView mainListView;
    private ProgressBar progress;
    private ApiHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        apiHelper = new ApiHelper();
        landmarkList = new ArrayList<Landmark>();

        Bundle bundle= getIntent().getExtras();
        ArrayList<Landmark> landmarkListReceive = bundle
                .getParcelableArrayList("Liste");

        landmarkList = landmarkListReceive;

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
        mainListView = (ListView) findViewById(R.id.listAffResult);
        customAdapter = new FavoriteActivity.CustomAdapter();
        //DrawableManager.getInstance().setListener(customAdapter);
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
        apiHelper.loadLandmarkById(id, this, new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                if (object == null) return;
                Landmark landmark = (Landmark) object;
                if(landmark == null) return;
                //DrawableManager.getInstance().loadImage(landmark.getImage(), FavoriteActivity.this);
                showLandmark(landmark);
            }
        });
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

            Picasso.get().load(ApiManager.getPhotoURL(l.getImage())).into(image);
            return view;
        }
    }

}