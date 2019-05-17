package com.free.app.spp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MyGameCreatorActivity extends AppCompatActivity {
    ArrayList<String> A = new ArrayList<>();
    ArrayAdapter<String> adp;
    ListView l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_game_creator);

        l = (ListView)findViewById(R.id.AdministerListView);
        //输入账号名，将之添加管理员

        Button newAdButton = findViewById(R.id.NewAdButton);
        newAdButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog();
            }
        });
        Button saveButton = (Button)findViewById(R.id.SaveButton);
        final EditText e1 = (EditText)findViewById(R.id.match_name_edit);
        final EditText e2  =(EditText)findViewById(R.id.match_intro_edit);
        final Spinner s = (Spinner)findViewById(R.id.date_spinner);
//        ArrayList<String>dates = new ArrayList<>();
//        Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        ArrayAdapter<String>adpForS = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,dates);
//        s.setAdapter(adpForS);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("name",e1.getText().toString())
                        .putExtra("intro",e2.getText().toString())
                        .putExtra("date",s.getSelectedItem().toString())
                        .putStringArrayListExtra("admin",A);

                setResult(RESULT_OK,intent);
                finish();
            }
        });
        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, A);
        l.setAdapter(adp);
    }

    protected void CreateDialog() {
        LayoutInflater li = LayoutInflater.from(MyGameCreatorActivity.this);
        final View v = li.inflate(R.layout.new_administrator_dialog, null);
        AlertDialog.Builder ad = new AlertDialog.Builder(MyGameCreatorActivity.this);
        ad.setIcon(R.drawable.basketball);
        ad.setTitle("新的管理员");
        ad.setMessage("请输入新管理员的用户名");
        ad.setView(v);
        ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText e = (EditText) v.findViewById(R.id.new_administrator_edit);
                String text = e.getText().toString();
                if (text.contentEquals("")) {
                    Toast.makeText(MyGameCreatorActivity.this, "请输入管理员用户名！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MyGameCreatorActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                    A.add(text);
                    adp = new ArrayAdapter<String>(MyGameCreatorActivity.this, android.R.layout.simple_list_item_1, A);
                    l.setAdapter(adp);
                }
            }
        });
        ad.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = ad.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
