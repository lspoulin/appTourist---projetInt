package com.example.lspoulin.montrealapp;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int CODE_LIST_LANDMARK = 10001;
    public static final int CODE_LOGIN = 10002;
    public static final int CODE_GET_LANDMARK = 10003;
    public static final int CODE_LIST_LANDMARK_WITH_TAGS = 10004;

    private List<Landmark> landmarkList;
    private ListView mainListView;
    private CustomAdapter customAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        landmarkList = new ArrayList<Landmark>();

        mainListView = (ListView) findViewById(R.id.listView);
        customAdapter = new CustomAdapter();
        mainListView.setAdapter(customAdapter);


        Intent i  = new Intent(MainActivity.this, ServerActivity.class);
        i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_LIST_LANDMARK);
        //startActivityForResult(i, CODE_LIST_LANDMARK);

        i  = new Intent(MainActivity.this, ServerActivity.class);
        i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_LOGIN_DUMMY_DATA);
        i.putExtra(ServerActivity.PARAM_LOGIN_USER,"root" );
        i.putExtra(ServerActivity.PARAM_LOGIN_PASSWORD, "pass");
        startActivityForResult(i, CODE_LOGIN);

        i  = new Intent(MainActivity.this, ServerActivity.class);
        i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_LIST_LANDMARK_WITH_TAGS);
        i.putExtra(ServerActivity.PARAM_LANDMARK_TAGS,"sport,family" );
        startActivityForResult(i, CODE_LIST_LANDMARK_WITH_TAGS);

        mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i  = new Intent(MainActivity.this, ServerActivity.class);
                i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_GET_LANDMARK);
                i.putExtra(ServerActivity.PARAM_LANDMARK_ID,landmarkList.get(position).getId() );
                startActivityForResult(i, CODE_GET_LANDMARK);

            }
        });
    }

    private void showLandmark(final Landmark landmark) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.showlandmark);
        dialog.setTitle(landmark.getTitle());

        // set the custom dialog components - text, image and button
        final TextView title = (TextView) dialog.findViewById(R.id.title);
        final TextView description = (TextView) dialog.findViewById(R.id.description);
        final TextView address = (TextView) dialog.findViewById(R.id.address);
        final ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
        final Button webbutton = (Button) dialog.findViewById(R.id.buttonWeb);

        imageView.setImageDrawable(landmark.getImage());

        title.setText(landmark.getTitle());
        description.setText(landmark.getDescription());
        address.setText(landmark.getAddress());

        webbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = landmark.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == CODE_LIST_LANDMARK && resultCode == ServerActivity.RESULT_OK){
            ArrayList<Landmark> listerLandmark = intent.getParcelableArrayListExtra(ServerActivity.LANDMARK_LIST);

            landmarkList = listerLandmark;
            customAdapter.notifyDataSetChanged();
        }

        if(requestCode == CODE_LOGIN && resultCode == ServerActivity.RESULT_OK){
            Toast.makeText(this, "Login successful", Toast.LENGTH_LONG).show();
        }
        if(requestCode == CODE_GET_LANDMARK && resultCode == ServerActivity.RESULT_OK){
            ArrayList<Landmark> listerLandmark = intent.getParcelableArrayListExtra(ServerActivity.LANDMARK_LIST);
            showLandmark(listerLandmark.get(0));
        }
        if(requestCode == CODE_LIST_LANDMARK_WITH_TAGS && resultCode == ServerActivity.RESULT_OK){
            ArrayList<Landmark> listerLandmark = intent.getParcelableArrayListExtra(ServerActivity.LANDMARK_LIST);

            landmarkList = listerLandmark;
            customAdapter.notifyDataSetChanged();
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
            TextView title = (TextView) view.findViewById(R.id.title);
            ImageView image = (ImageView) view.findViewById(R.id.image);

            Landmark l = landmarkList.get(i);
            title.setText(l.getTitle());
            image.setImageDrawable(l.getImage());
            return view;
        }
    }
}
