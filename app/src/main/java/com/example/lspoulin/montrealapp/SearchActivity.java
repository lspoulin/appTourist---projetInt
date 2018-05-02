package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnRecherche ;
    EditText motsClef ;
    Spinner spinSortBy;
    String strKeyWord , strSort;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnRecherche = (Button)findViewById(R.id.btnSearch);
        motsClef = (EditText)findViewById(R.id.keyWord);
        spinSortBy = (Spinner) findViewById(R.id.spinSort);
        btnBack = (ImageButton)findViewById(R.id.btnSearchBack);

        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.sort_search, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSortBy.setAdapter(spinAdapter);

        btnRecherche.setOnClickListener(this);
        btnBack.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnSearch){
            strSort = spinSortBy.getSelectedItem().toString();

            strKeyWord = motsClef.getText().toString();


            if(strKeyWord == null){
                strKeyWord = "";
            }

            Intent i ;
            i  = new Intent(SearchActivity.this, ResultActivity.class);


            i.putExtra("KeyWord", strKeyWord);
            i.putExtra("Preference", strSort);


            startActivity(i);


        }else if((view.getId() == R.id.btnSearchBack)){



            finish();
        }

    }
}