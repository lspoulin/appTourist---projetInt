package com.example.lspoulin.montrealapp;

/**
 * Created by lspoulin on 2018-04-27.
 */

public class ServerManager {
    public static final boolean LOCAL_SERVER = false;
    public static final String CONTROLLEUR_ENTRY_POINT = "https://apptouristprojetint.000webhostapp.com/PHP/";
    public static final String CONTROLLEUR_LANDMARK_ENDPOINT = "activityControleurJSON.php";
    public static final String CONTROLLEUR_USER_ENDPOINT = "userControleurJSON.php";
    public static final String CONTROLLEUR_LOCAL_ENTRY_POINT = "http://10.0.2.2:8888/ProjetFinal/PHP/";

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







}
