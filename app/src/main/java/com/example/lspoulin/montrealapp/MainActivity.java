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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final int CODE_LIST_LANDMARK = 10001;
    public static final int CODE_LOGIN = 10002;
    public static final int CODE_GET_LANDMARK = 10003;
    public static final int CODE_LIST_LANDMARK_WITH_TAGS = 10004;
    public static final int CODE_CREATE_NEW_USER = 10005;
    Spinner spinSortBy;
    private List<Landmark> landmarkList;
    private ListView mainListView;
    private CustomAdapter customAdapter;
    ImageButton btnUtilisateur, btnPreference, btnFavoris, btnRecherche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinSortBy = (Spinner) findViewById(R.id.spnSort);
        btnFavoris = (ImageButton)findViewById(R.id.btnFav);
        btnUtilisateur = (ImageButton)findViewById(R.id.btnUser);
        btnPreference = (ImageButton)findViewById(R.id.btnPref);
        btnRecherche = (ImageButton)findViewById(R.id.btnSrch);
        spinSortBy = (Spinner) findViewById(R.id.spnSort);

        btnFavoris.setOnClickListener(this);
        btnUtilisateur.setOnClickListener(this);
        btnRecherche.setOnClickListener(this);
        btnPreference.setOnClickListener(this);



        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.sort_activity, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSortBy.setAdapter(spinAdapter);
        spinSortBy.setOnItemSelectedListener(this);


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
            UserManager.getInstance().setUser((User)intent.getParcelableExtra(ServerActivity.USER));
            Toast.makeText(this, "Login successful for user : " + UserManager.getInstance().getUser().getName(), Toast.LENGTH_LONG).show();
        }
        if(requestCode == CODE_CREATE_NEW_USER && resultCode == ServerActivity.RESULT_OK){
            UserManager.getInstance().setUser((User)intent.getParcelableExtra(ServerActivity.USER));
            Toast.makeText(this, "User created : "+ UserManager.getInstance().getUser().getName() , Toast.LENGTH_LONG).show();
        }

        if(requestCode == CODE_CREATE_NEW_USER && resultCode == ServerActivity.RESULT_CANCELED){
            Toast.makeText(this, "User not created" , Toast.LENGTH_LONG).show();
        }

        if(requestCode == CODE_LOGIN && resultCode == ServerActivity.RESULT_CANCELED){
            Toast.makeText(this, "Login unsuccessfull", Toast.LENGTH_LONG).show();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        landmarkList = new ArrayList<Landmark>();

        mainListView = (ListView) findViewById(R.id.listAct);
        customAdapter = new CustomAdapter();
        mainListView.setAdapter(customAdapter);


        Intent i  = new Intent(MainActivity.this, ServerActivity.class);
        i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_LIST_LANDMARK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(i, CODE_LIST_LANDMARK);
        overridePendingTransition(0,0);

        /*i  = new Intent(MainActivity.this, ServerActivity.class);
        i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_LOGIN);
        i.putExtra(ServerActivity.PARAM_LOGIN_USER,"lspoulin" );
        i.putExtra(ServerActivity.PARAM_LOGIN_PASSWORD, "allo");
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(i, CODE_LOGIN);
        overridePendingTransition(0,0);*/


        /*i  = new Intent(MainActivity.this, ServerActivity.class);
        i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_NEW_USER);
        i.putExtra(ServerActivity.PARAM_NEW_USER_USER,"lspoulin2" );
        i.putExtra(ServerActivity.PARAM_NEW_USER_PASSWORD, "allotoi");
        i.putExtra(ServerActivity.PARAM_NEW_USER_EMAIL, "lspoulin2@gmail.com");
        i.putExtra(ServerActivity.PARAM_NEW_USER_PREFERENCES, "sport");
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(i, CODE_CREATE_NEW_USER);
        overridePendingTransition(0,0);*/

        /*i  = new Intent(MainActivity.this, ServerActivity.class);
        i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_LIST_LANDMARK_WITH_TAGS);
        i.putExtra(ServerActivity.PARAM_LANDMARK_TAGS,"sport" );
        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(i, CODE_LIST_LANDMARK_WITH_TAGS);*/

        mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i  = new Intent(MainActivity.this, ServerActivity.class);
                i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_GET_LANDMARK);
                i.putExtra(ServerActivity.PARAM_LANDMARK_ID,landmarkList.get(position).getId() );
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(i, CODE_GET_LANDMARK);
                overridePendingTransition(0,0);

            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnFav){
            try {
                for (Landmark l : landmarkList) {
                    if (!l.isLiked()) {
                        landmarkList.remove(l);
                    }

                }
                customAdapter.notifyDataSetChanged();
            }
            catch (Exception e){

            }

        }else if(view.getId() == R.id.btnUser){
            if (UserManager.getInstance().isLoggin()){
                showUserSetting();
            }
            else{
                showUserLogin();
            }

        }else if(view.getId() == R.id.btnPref){
            Toast.makeText(this, "Button Pref CLicked", Toast.LENGTH_LONG). show();

        }else if(view.getId() == R.id.btnSrch){
            Toast.makeText(this, "Button Srch CLicked", Toast.LENGTH_LONG). show();


        }
    }

    private void showUserSetting() {
        Toast.makeText(this, "Show user settings", Toast.LENGTH_LONG). show();
    }

    private void showUserLogin() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.showuserlogin);
        dialog.setTitle("Login");

        // set the custom dialog components - text, image and button
        final View layoutLogin = (View) dialog.findViewById(R.id.login);
        final TextView usernameLogin = (TextView) dialog.findViewById(R.id.editUserNameLogin);
        final TextView passwordLogin = (TextView) dialog.findViewById(R.id.editPAsswordLogin);
        final Button login = (Button) dialog.findViewById(R.id.buttonLogin);
        final Button showSignup = (Button) dialog.findViewById(R.id.buttonShowSignup);

        final View layoutCreate = (View) dialog.findViewById(R.id.createuser);
        final TextView email = (TextView) dialog.findViewById(R.id.editEmail);
        final TextView username = (TextView) dialog.findViewById(R.id.editUserName);
        final TextView password = (TextView) dialog.findViewById(R.id.editPassword);
        final TextView confirm = (TextView) dialog.findViewById(R.id.editConfirm);
        final Button signup = (Button) dialog.findViewById(R.id.buttonCreate);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i  = new Intent(MainActivity.this, ServerActivity.class);
                i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_LOGIN);
                i.putExtra(ServerActivity.PARAM_LOGIN_USER,usernameLogin.getText().toString());
                i.putExtra(ServerActivity.PARAM_LOGIN_PASSWORD, passwordLogin.getText().toString());
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(i, CODE_LOGIN);
                overridePendingTransition(0,0);
                dialog.dismiss();
            }
        });

        showSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutLogin.setVisibility(View.GONE);
                layoutCreate.setVisibility(View.VISIBLE);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                i  = new Intent(MainActivity.this, ServerActivity.class);
                i.putExtra(ServerActivity.SERVICE, ServerActivity.SERVICE_NEW_USER);
                i.putExtra(ServerActivity.PARAM_NEW_USER_USER, username.getText().toString() );
                i.putExtra(ServerActivity.PARAM_NEW_USER_PASSWORD, password.getText().toString());
                i.putExtra(ServerActivity.PARAM_NEW_USER_EMAIL, email.getText().toString());
                i.putExtra(ServerActivity.PARAM_NEW_USER_PREFERENCES, "");
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(i, CODE_CREATE_NEW_USER);
                overridePendingTransition(0,0);
                dialog.dismiss();
            }
        });

        dialog.show();

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
            image.setImageDrawable(l.getImage());
            return view;
        }
    }
}
