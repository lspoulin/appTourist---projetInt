package com.example.lspoulin.montrealapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final int CODE_LANDMARK = 10001;


    private Spinner spinSortBy;
    private List<Landmark> landmarkList;
    private List<Landmark> landmarkListToSend;
    private String[] tabPref ;
    private ListView mainListView;
    private CustomAdapter customAdapter;
    private ImageButton btnUtilisateur, btnPreference, btnFavoris, btnRecherche;
    private ProgressBar progress;
    private Switch swtPref;
    private Map<String, String> tagsMap = new HashMap<String, String>();
    private boolean comingFromSplash;
    private final ApiHelper apiHelper = new ApiHelper();

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
        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);
        swtPref= (Switch) findViewById(R.id.switchPref);

        tabPref = new String[7];

        tabPref[0] = "gastronomique";
        tabPref[1] = "plus_populaire";
        tabPref[2] = "plein_air";
        tabPref[3] = "sport";
        tabPref[4] = "familier";
        tabPref[5] = "culturelle";
        tabPref[6] = "recreative";


        tagsMap.put("Général", "");
        tagsMap.put("Restaurant", "gastronomique");
        tagsMap.put("Populaire", "plus_populaire");
        tagsMap.put("Plein Air", "plein_air");
        tagsMap.put("Sportive", "sport");
        tagsMap.put("Familiale", "familier");
        tagsMap.put("Culturelle", "culturelle");
        tagsMap.put("Récréative", "recreative");


        btnFavoris.setOnClickListener(this);
        btnUtilisateur.setOnClickListener(this);
        btnRecherche.setOnClickListener(this);
        btnPreference.setOnClickListener(this);

        landmarkList = getIntent().getParcelableArrayListExtra(SplashActivity.LANDMARK_LIST);
        if(landmarkList == null) landmarkList = new ArrayList<Landmark>();
        comingFromSplash = true;
        landmarkListToSend = new ArrayList<Landmark>();
        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.sort_activity, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSortBy.setAdapter(spinAdapter);
        spinSortBy.setOnItemSelectedListener(this);
        swtPref.setOnCheckedChangeListener(this);
    }

    private void showLandmark(final Landmark landmark) {
        Intent i;
        i  = new Intent(MainActivity.this, LandmarkActivity.class);
        i.putExtra(LandmarkActivity.LANDMARK, (Parcelable) landmark);
        startActivityForResult(i, CODE_LANDMARK);
    }

    private void loginAttempt(final String username, final String password) {
        apiHelper.loginAttempt(username, password, this, new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                UserManager.getInstance().setUser((User)object);
            }
        });
    }

    private void createNewUserAttempt(final String name, final String email, final String password, final String preferences) {
        apiHelper.createNewUserAttempt(name, email, password, preferences, this, new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                User user = new User((Integer) object, name, email, preferences);
                UserManager.getInstance().setUser(user);
            }
        });
    }

    private void loadLandmarks(final String tag) {
        progress.setVisibility(View.VISIBLE);
        apiHelper.loadLandmarks(tag, this, new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                ArrayList<Landmark> temps = (ArrayList<Landmark>) object;
                ArrayList<Landmark> filtered = new ArrayList<Landmark>();
                for(Landmark landmark: temps) {
                    if(landmark.getTags().contains(tag)) {
                        filtered.add(landmark);
                    }
                }
                landmarkList = filtered;
                customAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }
        });
    }


    private void loadLandmarkById(int id){
        apiHelper.loadLandmarkById(id, this, new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                Landmark landmark = (Landmark) object;
                showLandmark(landmark);
            }
        });
    }

    private void affPreference(final String tags){
        progress.setVisibility(View.VISIBLE);
        apiHelper.loadLandmarks(tags, this, new Callback() {
            @Override
            public void methodToCallBack(Object object) {
                if(object == null) return;
                ArrayList<Landmark> temps = (ArrayList<Landmark>) object;
                if (temps == null) return;
                ArrayList<Landmark> filtered = new ArrayList<Landmark>();
                for(Landmark landmark: temps) {
                    for(String pref : tabPref) {
                        if(landmark.getTags().contains(tags) || (UserManager.getInstance().getUser().getPreferences().contains(pref) && landmark.getTags().contains(pref))){
                            filtered.add(landmark);
                            break;
                        }
                    }
                }
                landmarkList = filtered;
                customAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

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
        mainListView = (ListView) findViewById(R.id.listAct);
        customAdapter = new CustomAdapter();
        mainListView.setAdapter(customAdapter);

        String tag = tagsMap.get(spinSortBy.getSelectedItem());
        if(tag == null) tag ="";
        if(!comingFromSplash)
        if(swtPref.isChecked()){
            if(UserManager.getInstance().isLoggin()){
                affPreference(tag);
            }
        }else{
            loadLandmarks(tag);
        }
        else
            comingFromSplash = false;

        mainListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadLandmarkById(landmarkList.get(position).getId());
            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnFav){
            if (UserManager.getInstance().isLoggin()){

                    ArrayList<Landmark> landmarkLiked = new ArrayList<Landmark>();
                    for (Landmark l : landmarkList) {
                        if(l.isLiked())
                            landmarkLiked.add(l);
                    }
                    landmarkListToSend = landmarkLiked;

                    Intent intent;
                    intent  = new Intent(MainActivity.this, FavoriteActivity.class);
                    intent.putParcelableArrayListExtra("Liste", (ArrayList<? extends Parcelable>) landmarkListToSend);
                    startActivity(intent);
                }
            else{
                showUserLogin();
            }

        }else if(view.getId() == R.id.btnUser){
            if (UserManager.getInstance().isLoggin()){
            }
            else{
                showUserLogin();
            }

        }else if(view.getId() == R.id.btnPref){
            if (UserManager.getInstance().isLoggin()){
                Intent i;
                i  = new Intent(MainActivity.this, PreferenceActivity.class);
                i.putExtra(PreferenceActivity.PREFERENCE, UserManager.getInstance().getUser().getPreferences());
                startActivity(i);
            }
            else{
                showUserLogin();
            }
        }else if(view.getId() == R.id.btnSrch){
            Intent i;
            i  = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(i);

        }
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
                String username = usernameLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                loginAttempt(username, password);

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
                String txtusername = username.getText().toString();
                String txtpassword = password.getText().toString();
                String txtemail = email.getText().toString();
                String txtconfirm = confirm.getText().toString();
                String txtpreferences = "";
                if (txtpassword.equals(txtconfirm)) {
                    createNewUserAttempt(txtusername, txtemail, txtpassword, txtpreferences);
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(getParent(), "Password invalid", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        String tag = tagsMap.get(spinSortBy.getSelectedItem());
        if(tag == null) tag = "";
        if(!comingFromSplash)
            if(swtPref.isChecked()){
                if(UserManager.getInstance().isLoggin()){
                    affPreference(tag);
                }
            }else{

                loadLandmarks(tag);
            }
        else
            comingFromSplash = false;

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

            Picasso.get().load(ApiManager.getPhotoURL(l.getImage())).into(image);

            return view;
        }
    }
}
