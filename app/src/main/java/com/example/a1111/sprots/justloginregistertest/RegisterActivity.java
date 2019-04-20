package com.example.a1111.sprots.justloginregistertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.a1111.sprots.NaviActivity;
import com.example.a1111.sprots.R;
import com.example.justloginregistertest.httptest.Http;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.rl_registeractivity_top)
    RelativeLayout mRlRegisteractivityTop;
    @BindView(R.id.iv_registeractivity_back)
    ImageView mIvRegisteractivityBack;
    @BindView(R.id.ll_registeractivity_body)
    LinearLayout mLlRegisteractivityBody;
    @BindView(R.id.et_registeractivity_username)
    EditText mEtRegisteractivityUsername;
    @BindView(R.id.et_registeractivity_password1)
    EditText mEtRegisteractivityPassword1;
    @BindView(R.id.et_registeractivity_password2)
    EditText mEtRegisteractivityPassword2;
    @BindView(R.id.et_registeractivity_email1)
    EditText registeractivityemail;
    @BindView(R.id.et_registeractivity_phoneCodes)
    EditText mEtRegisteractivityPhonecodes;
    @BindView(R.id.rl_registeractivity_bottom)
    RelativeLayout mRlRegisteractivityBottom;
    private String realCode = "0000";
    //private DBOpenHelper mDBOpenHelper;
    private int Result_gec;
    private int Result_rg;

    private int getemailCode(String email) {
        Http<JSONArray> http1 = new Http<>();
        http1.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray emailCode) throws JSONException, IOException {
                System.out.println("result:" + emailCode.getJSONObject(0).get("result"));
                Result_gec = Integer.parseInt(String.valueOf(emailCode.getJSONObject(0).get("result")));
            }
        });
        http1.execute("GetEmailCode", email);
        return Result_gec;
    }

    private int Register(String username, String password, String email, String emailcode) {
        Http<JSONArray> http2 = new Http<>();
        http2.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray user) throws JSONException, IOException {
                System.out.println("result:" + user.getJSONObject(0).get("result"));
                Result_rg = Integer.parseInt(String.valueOf(user.getJSONObject(0).get("result")));
            }
        });
        http2.execute("Register", username, password, email, emailcode);
        return Result_rg;
    }

    private boolean checkUsername(String name) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9-]{3,20}$");
        Matcher m = pattern.matcher(name);

        return m.matches();
    }

    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        // mDBOpenHelper = new DBOpenHelper(this);


    }

    /**
     * 注册页面能点击的就三个地方
     * top处返回箭头、获取验证码、注册按钮
     */
    @OnClick({
            R.id.iv_registeractivity_back,
            R.id.bt_registeractivity_codebutton,
            R.id.bt_registeractivity_register
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_registeractivity_back: //返回登录页面
                Intent intent1 = new Intent(this, loginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.bt_registeractivity_codebutton:    //获取验证码
                String email1 = registeractivityemail.getText().toString().trim();
                if (isEmail(email1)) {
                    int a = getemailCode(email1);
                    System.out.println("返回值是：" + a);

                    //    if(a == 1){
                    //       Toast.makeText(this,"已发送验证码至邮箱",Toast.LENGTH_SHORT).show();
                    //}
                    //   else{
                    //        Toast.makeText(this,"请输入有效的邮箱",Toast.LENGTH_SHORT).show();
                    //   }
                } else {
                    Toast.makeText(this, "请输入正确格式的邮箱", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、密码、邮箱、验证码
                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String password1 = mEtRegisteractivityPassword1.getText().toString().trim();
                String password2 = mEtRegisteractivityPassword2.getText().toString().trim();
                String Code = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
                String email2 = registeractivityemail.getText().toString().trim();
                //注册验证
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password1) && !TextUtils.isEmpty(password2) && !TextUtils.isEmpty(Code) && !TextUtils.isEmpty(email2)) {
                    if (password1.equals(password2)) {
                        int a = Register(username, password2, email2, Code);
                        System.out.println("返回值是：" + a);
                        // mDBOpenHelper.add(username, password2,email2);
                        if (Code.equals(realCode)) {
                            Intent intent2 = new Intent(this, NaviActivity.class);
                            startActivity(intent2);
                            finish();
                            Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "验证错误,注册失败", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "请输入前后一致的密码", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

