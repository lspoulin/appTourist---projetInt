package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ServerActivity extends AppCompatActivity {
    public static final String RESPONSETO = "The Activity that called me!";
    public static final String SERVICE = "com.example.lspoulin.montrealapp.ServerActivity.service";
    public static final String SERVICE_LIST_LANDMARK = "com.example.lspoulin.montrealapp.ServerActivity.service.listlandmark";
    public static final String LANDMARK_LIST = "com.example.lspoulin.montrealapp.ServerActivity.service.listactivity";

    private String respondTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String dataTransmited=intent.getStringExtra(SERVICE);
        switch (dataTransmited){
            case SERVICE_LIST_LANDMARK:
                listActivity();
            default:
        }


    }

    private void listActivity() {
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
                0.0f
        ));

        result.putParcelableArrayListExtra(this.LANDMARK_LIST, landmarkList);
        setResult(RESULT_OK, result);
        ServerActivity.this.finish();
    }
}
