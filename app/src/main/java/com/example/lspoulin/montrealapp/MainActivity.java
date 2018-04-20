package com.example.lspoulin.montrealapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int CODE_LIST_LANDMARK = 10001;
    public static final int CODE_LOGIN = 10002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i  = new Intent(MainActivity.this, ServerActivity.class);
        i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_LIST_LANDMARK);
        startActivityForResult(i, CODE_LIST_LANDMARK);

        i  = new Intent(MainActivity.this, ServerActivity.class);
        i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_LOGIN_DUMMY_DATA);
        i.putExtra(ServerActivity.PARAM_LOGIN_USER,"root" );
        i.putExtra(ServerActivity.PARAM_LOGIN_PASSWORD, "pass");
        startActivityForResult(i, CODE_LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == CODE_LIST_LANDMARK && resultCode == ServerActivity.RESULT_OK){
            ArrayList<Landmark> listerLandmark = intent.getParcelableArrayListExtra(ServerActivity.LANDMARK_LIST);
            String output = "";
            for (Landmark l : listerLandmark){
                output += l.getTitle() + " " + l.getDistanceKM()+ "km\n";
            }
            Toast.makeText(this, output.substring(0, output.length()-1), Toast.LENGTH_LONG).show();
        }

        if(requestCode == CODE_LOGIN && resultCode == ServerActivity.RESULT_OK){
            Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
