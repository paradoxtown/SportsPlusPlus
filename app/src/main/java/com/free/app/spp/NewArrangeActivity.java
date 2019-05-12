package com.free.app.spp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class NewArrangeActivity extends AppCompatActivity {

    String teamA, teamB, pos, time;
    EditText e1, e2, e3, e4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_arrange);
        e1 = findViewById(R.id.edit1);
        e2 = findViewById(R.id.edit2);
        e3 = findViewById(R.id.edit3);
        e4 = findViewById(R.id.edit4);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamA = e1.getText().toString().trim();
                teamB = e2.getText().toString().trim();
                pos = e3.getText().toString().trim();
                time = e4.getText().toString().trim();
                if (teamA.contentEquals("") || teamB.contentEquals("") || pos.contentEquals("") || time.contentEquals("")) {
                    Toast.makeText(NewArrangeActivity.this, "请输入完整的赛程信息！", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = getIntent();
                    intent.putExtra("123", new ArrangeListItem(teamA, teamB, pos, time));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}

class ArrangeListItem implements Serializable {
    private String teamA,teamB,pos,time;
    ArrangeListItem(String a,String b,String p,String t){
        teamA = a;
        teamB = b;
        pos = p;
        time = t;
    }

    String getPos() {
        return pos;
    }

    String getTeamA() {
        return teamA;
    }

    String getTeamB() {
        return teamB;
    }

    String getTime() {
        return time;
    }
}
