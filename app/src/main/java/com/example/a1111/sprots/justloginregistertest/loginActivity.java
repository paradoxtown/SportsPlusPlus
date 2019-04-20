package com.example.a1111.sprots.justloginregistertest;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1111.sprots.NaviActivity;
import com.example.a1111.sprots.R;
import com.example.a1111.sprots.justloginregistertest.RegisterActivity;
import com.example.justloginregistertest.httptest.Http;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class loginActivity extends AppCompatActivity {
    private int Result_log;
    private int Login(String username,String password){
        Http<JSONArray> http2 = new Http<>();
        http2.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray user) throws JSONException, IOException {
                System.out.println("result:"+user.getJSONObject(0).get("result"));
                Result_log =  Integer.parseInt(String.valueOf(user.getJSONObject(0).get("result")));
            }
        });
        http2.execute("Login",username,password);
        return Result_log;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @BindView(R.id.tv_loginactivity_register)
    TextView mTvLoginactivityRegister;
    @BindView(R.id.rl_loginactivity_top)
    RelativeLayout mRlLoginactivityTop;
    @BindView(R.id.et_loginactivity_username)
    EditText mEtLoginactivityUsername;
    @BindView(R.id.et_loginactivity_password)
    EditText mEtLoginactivityPassword;
    @BindView(R.id.ll_loginactivity_two)
    LinearLayout mLlLoginactivityTwo;
    @OnClick({
            R.id.tv_loginactivity_register,
            R.id.bt_loginactivity_login,
    })

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_loginactivity_register:
                //TODO 注册界面跳转
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;

            case R.id.bt_loginactivity_login:
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                     int a = Login(name,password);
                     System.out.println("返回值是："+a);
                     //if(a == 1){
                     Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                     Intent intent = new Intent(this, NaviActivity.class);
                     startActivity(intent);
                     finish();
                    // }else{
                       //  Toast.makeText(this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                     //}

                //    ArrayList<User> data = mDBOpenHelper.getAllData();
                //    boolean match = false;
                //    for(int i=0;i<data.size();i++) {
                //        User user = data.get(i);
                //        if (name.equals(user.getName()) && password.equals(user.getPassword())){
                //            match = true;
                //            break;
                //        }else{
                //            match = false;
                //        }
                //    }
                //    if (match) {
                //        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                //        Intent intent = new Intent(this, MainActivity.class);
                //        startActivity(intent);
                //        finish();//销毁此Activity
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}



