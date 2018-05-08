package com.example.lspoulin.montrealapp;

/**
 * Created by lspoulin on 2018-05-07.
 */

import org.json.JSONObject;

interface Mappable {

    void mapJSON(JSONObject object);
    String toJSON();
}
