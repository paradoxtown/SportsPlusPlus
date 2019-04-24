package com.free.app.spp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;


public class ArrangeActivity extends AppCompatActivity {
    private JSONArray ret = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrange);
        this.setTitle("赛程安排");
        Intent i = getIntent();
        String team_name = i.getStringExtra("123");

    }


    void GetSchedule(String teamName){
        Http<JSONArray> http = new Http<>();
        http.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray Schedule) throws JSONException, IOException {
                ret = Schedule;
                Handle(ret);
            }
        });
        http.execute("GetSchedule", teamName);
    }

    void Handle(JSONArray e) throws JSONException {
        for(int i = 0;i < e.length();i++){
            JSONObject j = e.getJSONObject(i);
            Iterator<String> it = j.keys();
            while(it.hasNext()){
                String key = it.next();
                String value = j.getString(key);
            }

        }
    }


}
