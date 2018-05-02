package com.example.lspoulin.montrealapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final int CODE_LIST_LANDMARK = 10001;
    public static final int CODE_LOGIN = 10002;
    public static final int CODE_GET_LANDMARK = 10003;
    public static final int CODE_LIST_LANDMARK_WITH_TAGS = 10004;
    public static final int CODE_CREATE_NEW_USER = 10005;
    public static final int CODE_LANDMARK_LIKED = 10006;

    Spinner spinSortBy;
    private List<Landmark> landmarkList;
    private ListView mainListView;
    private CustomAdapter customAdapter;
    private ImageButton btnUtilisateur, btnPreference, btnFavoris, btnRecherche;
    private ProgressBar progress;
    private Switch swtPref;
    private Map<String, String> tagsMap = new HashMap<String, String>();



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

        tagsMap.put("Populaire", "plus_populaire");
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

        landmarkList = new ArrayList<Landmark>();

        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.sort_activity, android.R.layout.simple_spinner_item);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSortBy.setAdapter(spinAdapter);
        spinSortBy.setOnItemSelectedListener(this);
        swtPref.setOnCheckedChangeListener(this);
    }

    private void showLandmark(final Landmark landmark) {

        Intent i;
        i  = new Intent(MainActivity.this, LandmarkActivity.class);
        i.putExtra("Landmark", (Parcelable) landmark);
        startActivity(i);
    }

    private void loginAttempt(final String username, final String password) {
        StringRequest requete = new StringRequest(Request.Method.POST, ServerManager.getControllerUser(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject unUser;
                                User user = new User();
                                for(i=1;i<jsonResponse.length();i++){
                                    unUser=jsonResponse.getJSONObject(i);
                                    user = new User(unUser.getInt("id"),
                                            unUser.getString("name"),
                                            unUser.getString("email"),
                                            unUser.getString("preferences"));

                                }
                                UserManager.getInstance().setUser(user);
                                Toast.makeText(MainActivity.this, "Login successful for user : " + UserManager.getInstance().getUser().getName(), Toast.LENGTH_LONG).show();
                                String tag = tagsMap.get(spinSortBy.getSelectedItem().toString());
                                if(tag==null)tag="";
                                loadLandmarks(tag);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Login Unsucessful" , Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Login Unsucessful" , Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Login Unsucessful" , Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les   parametres pour POST
                params.put("action", "login");
                params.put("user", username);
                params.put("password", ServerManager.md5(password));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }

    private void createNewUserAttempt(final String name, final String email, final String password, final String preferences) {
        StringRequest requete = new StringRequest(Request.Method.POST, ServerManager.getControllerUser(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                User user = new User();
                                user.setPreferences(preferences);
                                user.setEmail(email);
                                user.setName(name);
                                user.setId(jsonResponse.getInt(1));
                                UserManager.getInstance().setUser(user);
                                Toast.makeText(MainActivity.this, "User created : "+ UserManager.getInstance().getUser().getName() , Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "User not created" , Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "User not created" , Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "User not created" , Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les   parametres pour POST
                params.put("action", "enregistrer");
                params.put("name", name);
                params.put("password", ServerManager.md5(password));
                params.put("email", email);
                params.put("preferences", preferences);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
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

    private void loadLandmarks(String tag) {
        progress.setVisibility(View.VISIBLE);

        final String tags = tag;
        StringRequest requete = new StringRequest(Request.Method.POST, ServerManager.getControllerLandmark(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject unLandmark;
                                ArrayList<Landmark> landmarks = new ArrayList<Landmark>();
                                for(i=1;i<jsonResponse.length();i++){
                                    unLandmark=jsonResponse.getJSONObject(i);

                                    if(unLandmark.getString("tags").contains(tags)) {

                                        Landmark l = new Landmark(unLandmark.getInt("id"),
                                                unLandmark.getString("title"),
                                                unLandmark.getString("description"),
                                                unLandmark.getString("address"),
                                                (float) unLandmark.getDouble("latitude"),
                                                (float) unLandmark.getDouble("longitude"),
                                                unLandmark.getString("url"),
                                                (float) unLandmark.getDouble("price"),
                                                (float) unLandmark.getDouble("distanceKM"),
                                                "",
                                                unLandmark.getString("tags"),
                                                unLandmark.getInt("liked") != 0);
                                        l.setImage(getDrawableBitmapFromJSON(unLandmark.getString("image")));
                                        landmarks.add(l);

                                    }else{}
                                }

                                landmarkList = landmarks;
                                customAdapter.notifyDataSetChanged();
                            }
                            else{
                                //resultNotOk();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //resultNotOk();
                        }
                        finally {
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //resultNotOk();
                        progress.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "lister");
                if(UserManager.getInstance().isLoggin())
                    params.put("userid", UserManager.getInstance().getUser().getId()+"");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }

    private void loadLandmarkById(final int id){
        StringRequest requete = new StringRequest(Request.Method.POST, ServerManager.getControllerLandmark(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject unLandmark;
                                ArrayList<Landmark> landmarks = new ArrayList<Landmark>();
                                for(i=1;i<jsonResponse.length();i++){
                                    unLandmark=jsonResponse.getJSONObject(i);

                                    Landmark l = new Landmark(unLandmark.getInt("id"),
                                            unLandmark.getString("title"),
                                            unLandmark.getString("description"),
                                            unLandmark.getString("address"),
                                            (float)unLandmark.getDouble("latitude"),
                                            (float) unLandmark.getDouble("longitude"),
                                            unLandmark.getString("url"),
                                            (float)unLandmark.getDouble("price"),
                                            (float)unLandmark.getDouble("distanceKM"),
                                            "",
                                            unLandmark.getString("tags"),
                                            unLandmark.getInt("liked")!=0);
                                    l.setImage(getDrawableBitmapFromJSON(unLandmark.getString("image")));
                                    landmarks.add(l);
                                }
                                showLandmark(landmarks.get(0));
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
                // Les parametres pour POST
                params.put("action", "listerParId");
                params.put("id", id+"");
                if(UserManager.getInstance().isLoggin())
                    params.put("userid", UserManager.getInstance().getUser().getId()+"");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);

    }

    private Drawable getDrawableBitmapFromJSON(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        Drawable d = new BitmapDrawable(getResources(),decodedByte);
        return d;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        /*if(requestCode == CODE_LIST_LANDMARK && resultCode == ServerActivity.RESULT_OK){
            ArrayList<Landmark> listerLandmark = intent.getParcelableArrayListExtra(ServerActivity.LANDMARK_LIST);

            landmarkList = listerLandmark;
            customAdapter.notifyDataSetChanged();
        }

        if(requestCode == CODE_LOGIN && resultCode == ServerActivity.RESULT_OK){
            UserManager.getInstance().setUser((User)intent.getParcelableExtra(ServerActivity.USER));
            Toast.makeText(this, "Login successful for user : " + UserManager.getInstance().getUser().getName(), Toast.LENGTH_LONG).show();
            String tag = tagsMap.get(spinSortBy.getSelectedItem().toString());
            if(tag==null)tag="";
            loadLandmarks(tag);
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
        }*/

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
        if(swtPref.isChecked()){
            if(UserManager.getInstance().isLoggin()){
                affPreference(tag);
            }
        }else{
            loadLandmarks(tag);
        }

        loadLandmarks(tag);

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
            try {
                ArrayList<Landmark> landmarkLiked = new ArrayList<Landmark>();
                for (Landmark l : landmarkList) {
                    if(l.isLiked())
                    landmarkLiked.add(l);
                }
                landmarkList = landmarkLiked;
                customAdapter.notifyDataSetChanged();
            }
            catch (Exception e){

            }

        }else if(view.getId() == R.id.btnUser){
            if (UserManager.getInstance().isLoggin()){
                Intent i;
                i  = new Intent(MainActivity.this, UserActivity.class);
            }
            else{
                showUserLogin();
            }

        }else if(view.getId() == R.id.btnPref){
            if (UserManager.getInstance().isLoggin()){
                Intent i;
                i  = new Intent(MainActivity.this, PreferenceActivity.class);

/*\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ SI ISLOGGIN JE VEUT ENVOYER A L'ACTIVITY PREFERENCE UN STRING DES PREFERENCE DE L'UTILISATEUR \\\\\\\\\\\\\\*/
/*\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ SI ISLOGGIN JE VEUT ENVOYER A L'ACTIVITY PREFERENCE UN STRING DES PREFERENCE DE L'UTILISATEUR \\\\\\\\\\\\\\*/

                i.putExtra("Preference", UserManager.getInstance().getUser().getPreferences());


/*\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ SI ISLOGGIN JE VEUT ENVOYER A L'ACTIVITY PREFERENCE UN STRING DES PREFERENCE DE L'UTILISATEUR \\\\\\\\\\\\\\*/
/*\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ SI ISLOGGIN JE VEUT ENVOYER A L'ACTIVITY PREFERENCE UN STRING DES PREFERENCE DE L'UTILISATEUR \\\\\\\\\\\\\\*/


                startActivity(i);
            }
            else{
                showUserLogin();
            }


        /*

            Intent i;
            i  = new Intent(MainActivity.this, PreferenceActivity.class);
            //i.putExtra("Preference", User.getPreferences().toString());

            startActivity(i);

        */

        }else if(view.getId() == R.id.btnSrch){
            Toast.makeText(this, "Button Srch CLicked", Toast.LENGTH_LONG). show();
            Intent i;
            i  = new Intent(MainActivity.this, SearchActivity.class);

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
        if(swtPref.isChecked()){
            if(UserManager.getInstance().isLoggin()){
                affPreference(tag);
            }
        }else{

            loadLandmarks(tag);
        }

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

    public void affPreference(String tag){
        progress.setVisibility(View.VISIBLE);

        final String tags = tag;
        StringRequest requete = new StringRequest(Request.Method.POST, ServerManager.getControllerLandmark(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            UserManager user = UserManager.getInstance();
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject unLandmark;
                                ArrayList<Landmark> landmarks = new ArrayList<Landmark>();
                                for(i=1;i<jsonResponse.length();i++){
                                    unLandmark=jsonResponse.getJSONObject(i);
                                    Landmark l = new Landmark(unLandmark.getInt("id"),
                                    unLandmark.getString("title"),
                                    unLandmark.getString("description"),
                                    unLandmark.getString("address"),
                                    (float) unLandmark.getDouble("latitude"),
                                    (float) unLandmark.getDouble("longitude"),
                                    unLandmark.getString("url"),
                                    (float) unLandmark.getDouble("price"),
                                    (float) unLandmark.getDouble("distanceKM"),
                                    "",
                                    unLandmark.getString("tags"),
                                    unLandmark.getInt("liked") != 0);
                                    l.setImage(getDrawableBitmapFromJSON(unLandmark.getString("image")));
                                    for (String preference :user.getUser().getPreferences().split(",")){
                                        if(l.getTags().contains(preference)){
                                            landmarks.add(l);
                                            break;
                                        }
                                    }
                                }

                                landmarkList = landmarks;
                                customAdapter.notifyDataSetChanged();
                            }
                            else{
                                //resultNotOk();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //resultNotOk();
                        }
                        finally {
                            progress.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //resultNotOk();
                        progress.setVisibility(View.GONE);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "lister");
                if(UserManager.getInstance().isLoggin())
                    params.put("userid", UserManager.getInstance().getUser().getId()+"");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);
    }




}
