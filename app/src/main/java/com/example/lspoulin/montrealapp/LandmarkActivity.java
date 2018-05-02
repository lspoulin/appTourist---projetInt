package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class LandmarkActivity extends AppCompatActivity {

    private ImageButton btnBack;

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
        DrawableManager.getInstance().setListener(imageView);
        Bitmap bitmap = DrawableManager.getInstance().getDrawable(landmark.getImage());
        Drawable imageDrawable = new BitmapDrawable(getResources(), bitmap);
        imageView.setImageDrawable(imageDrawable);



        //imageView.setImageURI(Uri.parse(ServerManager.getPhotoURL(landmark.getImage())));
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

        //test



    }

    private void landmarkUnliked(final int userid, final int landmarkid) {
        StringRequest requete = new StringRequest(Request.Method.POST, ServerManager.getControllerUser(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                //Intent result = new Intent();
                                //resultOk(result);
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
                // Les   parametres pour POST
                params.put("action", "activityUnLiked");
                params.put("userid", String.valueOf(userid));
                params.put("activityid", String.valueOf(landmarkid));

                Log.d("Unliked param", String.valueOf(userid) + " " + String.valueOf(landmarkid));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }

    private void landmarkLiked(final int userid, final int landmarkid) {
        StringRequest requete = new StringRequest(Request.Method.POST, ServerManager.getControllerUser(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                Intent result = new Intent();
                                //resultOk(result);
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
                // Les   parametres pour POST
                params.put("action", "activityLiked");
                params.put("userid", String.valueOf(userid));
                params.put("activityid", String.valueOf(landmarkid));

                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }
}