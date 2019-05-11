package com.free.app.spp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View.OnClickListener;

import java.util.ArrayList;

public class MyGameCreatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_game_creator);

        ArrayList<String>A = new ArrayList<>();
        A.add("金泽");
        A.add("金泽");
        A.add("金泽");
        A.add("刘启航");
        ListView l = findViewById(R.id.AdministerListView);
        ArrayAdapter<String>adp;
        Button newAdButton = findViewById(R.id.NewAdButton);
        newAdButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adp = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,A);
        l.setAdapter(adp);
    }
}
