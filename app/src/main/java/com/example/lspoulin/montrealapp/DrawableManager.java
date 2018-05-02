package com.example.lspoulin.montrealapp;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lspoulin on 2018-04-24.
 */

public class DrawableManager {
    private static final String ROOTDIR = ".montrealapp";
    private static DrawableManager instance;
    private HashMap<String, Bitmap> drawablelist;
    private DrawableManager(){
        drawablelist = new HashMap<String, Bitmap>();
    }
    private ArrayList<Object> listerners = new ArrayList<Object>();

    public void setListener(Object o){
        listerners.add(o);
    }

    private String getRootFolder(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static DrawableManager getInstance(){
        if(instance == null)
            instance = new DrawableManager();
        return instance;
    }

    public Bitmap getDrawable(String key){
        return drawablelist.get(key);
    }

    public void addDrawable(String key, Bitmap drawable){
        if (!drawablelist.containsKey(key))
            drawablelist.put(key, drawable);
        for(Object o: listerners){
            if (o instanceof BaseAdapter)
                ((BaseAdapter) o).notifyDataSetChanged();
            if(o instanceof ImageView){
                ((ImageView)o).setImageBitmap(drawable);
            }
        }
    }


    public String saveToDisk(Bitmap bitmap, String filename) {

        String stored = null;

        File sdcard = Environment.getExternalStorageDirectory() ;

        //File folder = new File(sdcard.getAbsoluteFile(), ROOTDIR);
        //folder.mkdir();

        File file = new File(getRootFolder(), filename ) ;
        if (file.exists())
            return stored ;

        try {
            Log.d("Saving image :", filename);
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            stored = "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Image is stored :", filename);
        return stored;
    }

    public File getImage(String imagename) {
        File mediaImage = null;
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root);
            if (!myDir.exists())
                return null;

            mediaImage = new File(getRootFolder() +"/"+imagename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Image loaded :", imagename);
        return mediaImage;
    }
    public boolean checkifImageExists(String imagename)
    {
        Bitmap b = null ;
        File file = getImage(getRootFolder()+"/"+imagename);
        String path = file.getAbsolutePath();

        if (file.exists() && path != null)
            b = BitmapFactory.decodeFile(path);

        if(b == null ||  b.equals(""))
        {
            return false ;
        }
        return true ;
    }


    public static boolean canWriteOnExternalStorage() {
        // get the state of your external storage
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) && Environment.isExternalStorageEmulated()) {
            // if storage is mounted return true
            return true;
        }
        return false;
    }

    public void loadImage(final String image, Context context) {

        if(!drawablelist.containsKey(image)) {
            if (checkifImageExists(image)) {
                Log.d("Image exist on disk :", image);
                File f = getImage(image);
                Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                addDrawable(image, bitmap);
                Log.d("Image added :", image);
            } else {
                ImageRequest request = new ImageRequest(ServerManager.getPhotoURL(image),
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                Log.d("Image downloaded :", image);
                                addDrawable(image, bitmap);
                                if(canWriteOnExternalStorage()) {
                                    saveToDisk(bitmap, image);

                                }

                            }
                        }, 0, 0, null,
                        new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                Volley.newRequestQueue(context).add(request);
            }
        }
    }
}
