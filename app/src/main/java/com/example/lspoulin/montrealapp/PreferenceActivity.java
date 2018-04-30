package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class PreferenceActivity extends AppCompatActivity {

    private Switch stRestaurant, stCulturel, stSport , stFamille,stPleinAir , stRecre, stPopulaire;
    private Button btnSave;
    private String listPref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        Intent intent = getIntent();

        listPref = intent.getExtras().getString("Preference");

        stRestaurant = (Switch)findViewById(R.id.swtRestaurant);
        stCulturel = (Switch)findViewById(R.id.swtCult);
        stSport = (Switch)findViewById(R.id.swtSport);
        stFamille = (Switch)findViewById(R.id.swtFam);
        stPleinAir = (Switch)findViewById(R.id.swtPleinAir);
        stRecre = (Switch)findViewById(R.id.swtRecre);
        stPopulaire = (Switch)findViewById(R.id.swtPopu);
        btnSave = (Button)findViewById(R.id.btnSauv);


        loadPref(listPref);



    }

    public void loadPref(String listPref) {
        


        listPref = "sport, recreatif, plus_populaire";


            if(listPref.contains("sport")){
                stSport.setChecked(true);

            }

            if (listPref.contains("gastronomique")){
                stRestaurant.setChecked(true);

            }
            if (listPref.contains("plus_populaire")){
                stPopulaire.setChecked(true);

            }
            if (listPref.contains("plein_air")){
                stPleinAir.setChecked(true);

            }
            if (listPref.contains("familier")){
                  stFamille.setChecked(true);

            }
            if (listPref.contains("culturelle")){
                stCulturel.setChecked(true);

            }
            if (listPref.contains("recreative")){
                stRecre.setChecked(true);

            }


        



    }

    

}

