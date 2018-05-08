package com.example.lspoulin.montrealapp;

/**
 * Created by lspoulin on 2018-05-07.
 */

import org.json.JSONObject;

/**
 * Created by lspoulin on 2018-05-07.
 */

interface Mappable {

    void mapJSON(JSONObject object);
    String toJSON();
}
