package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;


public class PreferenceActivity extends AppCompatActivity {

    public static final String PREFERENCE = "com.exemple.lspoulin.montrealapp.PreferenceActivity.Preference";
    private Switch stRestaurant, stCulturel, stSport , stFamille,stPleinAir , stRecre, stPopulaire;
    private Button btnSave;
    private String listPref, newPref ;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        Intent intent = getIntent();

        listPref = intent.getExtras().getString(PREFERENCE);

        stRestaurant = (Switch)findViewById(R.id.swtRestaurant);
        stCulturel = (Switch)findViewById(R.id.swtCult);
        stSport = (Switch)findViewById(R.id.swtSport);
        stFamille = (Switch)findViewById(R.id.swtFam);
        stPleinAir = (Switch)findViewById(R.id.swtPleinAir);
        stRecre = (Switch)findViewById(R.id.swtRecre);
        stPopulaire = (Switch)findViewById(R.id.swtPopu);
        btnSave = (Button)findViewById(R.id.btnSauv);
        btnBack = (ImageButton)findViewById(R.id.btnPrefBack) ;

        loadPref(listPref);

        newPref = "";

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(stSport.isChecked()){
                    newPref += "sport, ";
                }
                if(stRestaurant.isChecked()){
                    newPref += "gastronomique, ";
                }
                if(stCulturel.isChecked()){
                    newPref += "culturelle, ";
                }
                if(stPleinAir.isChecked()){
                    newPref += "plein_air, ";
                }
                if(stPopulaire.isChecked()){
                    newPref += "plus_populaire, ";
                }
                if(stRecre.isChecked()){
                    newPref += "recreative, ";
                }
                if(stFamille.isChecked()){
                    newPref += "familier, ";
                }
                saveUserPreferences(newPref);
            }
        });
    }

    private void saveUserPreferences(final String preferences) {
        UserManager.getInstance().getUser().setPreferences(newPref);
        ApiHelper apiHelper = new ApiHelper(getApplicationContext());
        apiHelper.saveUser( new Callback() {
            @Override
            public void methodToCallBack(Object object) {

            }
        });
    }

    public void loadPref(String listPref) {
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

    private void resultNotOk(){
        Intent result = new Intent();
        setResult(RESULT_CANCELED, result);
        result.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        PreferenceActivity.this.finish();
        overridePendingTransition(0, 0);
    }

    private void resultOk(Intent result){
        setResult(RESULT_OK, result);
        result.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        PreferenceActivity.this.finish();
        overridePendingTransition(0, 0);
    }
}

