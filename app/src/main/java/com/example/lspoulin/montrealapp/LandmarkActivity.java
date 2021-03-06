package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class LandmarkActivity extends AppCompatActivity {
    public static final String LANDMARK = "com.example.lspoulimn.montrealapp.landmarkActivity.landmark";
    public static final String LANDMARK_ID = "com.example.lspoulimn.montrealapp.landmarkActivity.landmarkid";
    public static final String LIKED = "com.example.lspoulimn.montrealapp.landmarkActivity.liked";


    private ImageButton btnBack;
    private  Landmark landmark;
    private ApiHelper apiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);

        apiHelper = new ApiHelper(getApplicationContext());
        Intent intent = getIntent();
        landmark = intent.getExtras().getParcelable(LANDMARK);

        TextView title = (TextView)findViewById(R.id.title);
        TextView description = (TextView)findViewById(R.id.description);
        TextView address = (TextView)findViewById(R.id.address);
        ImageView imageView = (ImageView)findViewById(R.id.imgLandmark);
        Button webbutton = (Button)findViewById(R.id.btnWeb);
        final ImageButton liked = (ImageButton)findViewById(R.id.imgLiked);
        btnBack = (ImageButton)findViewById(R.id.btnLandMarkBack) ;

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        title.setText(landmark.getTitle());
        address.setText(landmark.getAddress());
        description.setText(landmark.getDescription());

        Picasso.get().load(ApiManager.getPhotoURL(landmark.getImage())).into(imageView);

        if(!UserManager.getInstance().isLoggin()){
            liked.setVisibility(View.GONE);
        }
        else{
            if(landmark.isLiked())
                liked.setImageResource(R.drawable.heartfilled);
            else
                liked.setImageResource(R.drawable.heartoutline);
        }



        webbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = landmark.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                landmark.setLiked(!landmark.isLiked());
                if(landmark.isLiked()) {
                    liked.setImageResource(R.drawable.heartfilled);
                    landmarkLiked(UserManager.getInstance().getUser().getId(), landmark.getId());
                }
                else {
                    liked.setImageResource(R.drawable.heartoutline);
                    landmarkUnliked(UserManager.getInstance().getUser().getId(), landmark.getId());
                }
            }
        });
    }

    @Override
    public void finish(){

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        intent.putExtra(LandmarkActivity.LANDMARK_ID, landmark.getId());
        intent.putExtra(LandmarkActivity.LIKED,landmark.isLiked());

        super.finish();
    }

    private void landmarkUnliked(final int userid, final int landmarkid) {
        apiHelper.landmarkUnliked(userid, landmarkid, new Callback() {
            @Override
            public void methodToCallBack(Object object) {

            }
        });
    }

    private void landmarkLiked(final int userid, final int landmarkid) {
        apiHelper.landmarkLiked(userid, landmarkid, new Callback() {
            @Override
            public void methodToCallBack(Object object) {

            }
        });
    }

}