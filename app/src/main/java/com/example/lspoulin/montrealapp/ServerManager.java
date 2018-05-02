package com.example.lspoulin.montrealapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by lspoulin on 2018-04-27.
 */

public class ServerManager {
    public static final boolean LOCAL_SERVER = false;
    public static final String CONTROLLEUR_ENTRY_POINT = "https://apptouristprojetint.000webhostapp.com/PHP/";
    public static final String PHOTO_ENTRY_POINT = "https://apptouristprojetint.000webhostapp.com/photos/";
    public static final String CONTROLLEUR_LANDMARK_ENDPOINT = "activityControleurJSON.php";
    public static final String CONTROLLEUR_USER_ENDPOINT = "userControleurJSON.php";
    public static final String CONTROLLEUR_LOCAL_ENTRY_POINT = "http://10.0.2.2:8888/ProjetFinal/PHP/";
    public static final String PHOTO_LOCAL_ENTRY_POINT = "http://10.0.2.2:8888/ProjetFinal/photos/";


    private static ServerManager instance;
    private ServerManager(){

    }

    public static ServerManager getInstance(){
        if(instance == null)
            instance = new ServerManager();
        return instance;
    }

    public static String getControllerLandmark() {
        if (LOCAL_SERVER)
            return CONTROLLEUR_LOCAL_ENTRY_POINT + CONTROLLEUR_LANDMARK_ENDPOINT;
        else
            return CONTROLLEUR_ENTRY_POINT + CONTROLLEUR_LANDMARK_ENDPOINT;
    }

    public static String getControllerUser() {
        if (LOCAL_SERVER)
            return CONTROLLEUR_LOCAL_ENTRY_POINT + CONTROLLEUR_USER_ENDPOINT;
        else
            return CONTROLLEUR_ENTRY_POINT + CONTROLLEUR_USER_ENDPOINT;
    }

    public static String getPhotoURL(String image) {
        if (LOCAL_SERVER)
            return PHOTO_LOCAL_ENTRY_POINT + image;
        else
            return PHOTO_ENTRY_POINT + image;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }







}
