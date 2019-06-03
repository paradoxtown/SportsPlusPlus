package com.free.app.spp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.unstoppable.submitbuttonview.SubmitButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import info.hoang8f.widget.FButton;


public class MyGameCreatorActivity extends AppCompatActivity {
    ArrayList<String> A = new ArrayList<>();
    ArrayAdapter<String> adp;
    ListView l;
    ArrayList<String> dateToStart = new ArrayList<>();

    protected void dateCalculation(){
        Calendar cal = Calendar.getInstance();
        int nowyear = cal.get(Calendar.YEAR);
        int nowmonth = cal.get(Calendar.MONTH) + 1;
        int nowday = cal.get(Calendar.DAY_OF_MONTH);
        for(int i = 1;i <= 30;i++){
            dateToStart.add(String.valueOf(nowyear) + "年" + String.valueOf(nowmonth) + "月" + String.valueOf(nowday) + "日");
            nowday++;
            if(nowmonth == 4 || nowmonth == 6 || nowmonth == 9 || nowmonth == 11)
                if(nowday == 31){nowmonth++;nowday = 1;}
                else if(nowmonth == 1 || nowmonth == 3 || nowmonth == 5 || nowmonth == 7 ||
                        nowmonth == 8 || nowmonth == 10 || nowmonth == 12)
                    if(nowday == 32){nowmonth++;nowday = 1;}
                    else{
                        if ((nowyear % 4 == 0 && nowyear % 100 != 0) || (nowyear % 100 == 0 && nowyear % 400 == 0)) {
                            if(nowday == 30){nowmonth++;nowday = 1;}
                        } else {
                            if (nowday == 29){nowmonth++;nowday = 1;}
                        }
                    }
            if(nowmonth == 13){
                nowyear++;nowmonth = 1;
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_game_creator);

        l = findViewById(R.id.AdministerListView);
        dateCalculation();
        //输入账号名，将之添加管理员
        Intent i = this.getIntent();
        String creatorName = i.getStringExtra("UserName");
        A.add(creatorName);

        SubmitButton cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FButton newAdButton = findViewById(R.id.add_admin);
        newAdButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog();
            }
        });

        final EditText nameEditText = findViewById(R.id.match_name_edit);
        final EditText introEditText = findViewById(R.id.match_intro_edit);
        final Spinner s = findViewById(R.id.date_spinner);
        ArrayAdapter<String>adpForSpinner = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dateToStart);
        adpForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adpForSpinner);

        final SubmitButton saveButton = findViewById(R.id.submit_button);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (introEditText.getText().toString().trim().length() > 100) {
                    Toast.makeText(MyGameCreatorActivity.this, "介绍应在100个字符以内", Toast.LENGTH_LONG).show();
                    saveButton.reset();
                } else if (nameEditText.getText().toString().trim().length() > 10) {
                    Toast.makeText(MyGameCreatorActivity.this, "比赛名应在10个字符以内", Toast.LENGTH_LONG).show();
                    saveButton.reset();
                }
                else if (nameEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(MyGameCreatorActivity.this, "比赛名不能为空！", Toast.LENGTH_LONG).show();
                    saveButton.reset();
                }
                else if (introEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(MyGameCreatorActivity.this, "介绍不能为空！", Toast.LENGTH_LONG).show();
                    saveButton.reset();
                } else {
                    saveButton.doResult(true);
                    Intent intent = getIntent();
                    intent.putExtra("name", nameEditText.getText().toString())
                            .putExtra("intro", introEditText.getText().toString())
                            .putExtra("date", s.getSelectedItem().toString())
                            .putStringArrayListExtra("admin", A);
                    setResult(RESULT_OK, intent);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }
        });

        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, A);
        l.setAdapter(adp);
    }

    protected void validateUser(final String username) throws JSONException {
        Http<JSONArray> h = new Http<>();
        h.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) throws JSONException {
                JSONObject j = jsonArray.getJSONObject(0);
                int flag = Integer.parseInt(j.getString("result"));
                for (String name : A) {
                    if (name.contentEquals(username)) {
                        Toast.makeText(MyGameCreatorActivity.this, "重复添加（创建者默认存在）", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (username.contentEquals("")) {
                    Toast.makeText(MyGameCreatorActivity.this, "请输入管理员用户名", Toast.LENGTH_LONG).show();
                    return;
                }
                if (flag != 1) {
                    Toast.makeText(MyGameCreatorActivity.this, "该用户不存在", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MyGameCreatorActivity.this, "添加成功", Toast.LENGTH_LONG).show();
                    A.add(username);
                    adp = new ArrayAdapter<>(MyGameCreatorActivity.this, android.R.layout.simple_list_item_1, A);
                    l.setAdapter(adp);
                }
            }
        });
        JSONObject p = new JSONObject();
        p.put("method", "user");
        p.put("username", username);
        h.execute("Validate", p.toString());
    }

    protected void CreateDialog() {
        LayoutInflater li = LayoutInflater.from(MyGameCreatorActivity.this);
        final View v = li.inflate(R.layout.new_administrator_dialog, null);
        AlertDialog.Builder ad = new AlertDialog.Builder(MyGameCreatorActivity.this);
        ad.setIcon(R.drawable.basketball);
        ad.setTitle("新的管理员");
        ad.setMessage("请输入新管理员的用户名");
        ad.setView(v);
        ad.setNegativeButton("取消", null);
        ad.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText e = v.findViewById(R.id.new_administrator_edit);
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
