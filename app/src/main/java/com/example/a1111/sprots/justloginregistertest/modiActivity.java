package com.example.a1111.sprots.justloginregistertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a1111.sprots.R;

import butterknife.ButterKnife;

public class modiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi);

        ButterKnife.bind(this);

    }

}
