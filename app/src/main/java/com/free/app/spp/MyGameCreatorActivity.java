package com.free.app.spp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.spark.submitbutton.SubmitButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


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

        Button newAdButton = findViewById(R.id.add_admin);
//        newAdButton.setShadowEnabled(true);
//        newAdButton.setShadowHeight(5);
//        newAdButton.setCornerRadius(5);
        newAdButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog();
            }
        });
        SubmitButton saveButton = findViewById(R.id.submit_button);
        final EditText nameEditText = (EditText)findViewById(R.id.match_name_edit);
        final EditText introEditText  =(EditText)findViewById(R.id.match_intro_edit);
        final Spinner s = (Spinner)findViewById(R.id.date_spinner);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (introEditText.getText().toString().trim().length() > 100) {
                    Toast.makeText(MyGameCreatorActivity.this, "介绍应在100个字符以内", Toast.LENGTH_LONG).show();
                }
                else if (nameEditText.getText().toString().trim().length() > 10) {
                    Toast.makeText(MyGameCreatorActivity.this, "比赛名应在10个字符以内", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = getIntent();
                    intent.putExtra("name", nameEditText.getText().toString())
                            .putExtra("intro", introEditText.getText().toString())
                            .putExtra("date", s.getSelectedItem().toString())
                            .putStringArrayListExtra("admin", A);

                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, A);
        l.setAdapter(adp);
    }
    protected void validateUser(final String username) throws JSONException {
        Http <JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException, IOException {
                JSONObject j = jsonArray.getJSONObject(0);
                int flag = Integer.parseInt(j.getString("result"));
                for(String name:A){
                    if(name.contentEquals(username)){
                        Toast.makeText(MyGameCreatorActivity.this, "该用户已经被添加为管理员了", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (username.contentEquals("")) {
                    Toast.makeText(MyGameCreatorActivity.this, "请输入管理员用户名", Toast.LENGTH_LONG).show();
                    return;
                }
                if(flag != 1){
                    Toast.makeText(MyGameCreatorActivity.this, "该用户不存在", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(MyGameCreatorActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                    A.add(username);
                    adp = new ArrayAdapter<String>(MyGameCreatorActivity.this, android.R.layout.simple_list_item_1, A);
                    l.setAdapter(adp);
                }
            }
        });
        JSONObject p = new JSONObject();
        p.put("method","user");
        p.put("username",username);
        h.execute("Validate",p.toString());
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
                try {
                    validateUser(text);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });
        ad.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
        AlertDialog dialog = ad.create();  //创建对话框
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
