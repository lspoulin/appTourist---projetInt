package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class LandmarkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark);

        Intent intent = getIntent();

        final Landmark landmark = intent.getExtras().getParcelable("Landmark");


        TextView title = (TextView)findViewById(R.id.title);
        TextView description = (TextView)findViewById(R.id.description);
        TextView address = (TextView)findViewById(R.id.address);
        ImageView imageView = (ImageView)findViewById(R.id.imgLandmark);
        Button webbutton = (Button)findViewById(R.id.btnWeb);
        ImageButton liked = (ImageButton)findViewById(R.id.imgLiked);

        title.setText(landmark.getTitle());
        address.setText(landmark.getAddress());
        description.setText(landmark.getDescription());
        imageView.setImageDrawable(landmark.getImage());

        webbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = landmark.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        //test



    }
}