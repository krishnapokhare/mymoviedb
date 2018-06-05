package com.pokhare.mymoviedb.helpers;

import org.json.JSONObject;

public interface ApiCallbackListener {
    void onTaskCompleted(JSONObject result);
}
