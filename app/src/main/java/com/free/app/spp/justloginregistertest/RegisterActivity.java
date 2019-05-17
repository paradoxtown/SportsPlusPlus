package com.free.app.spp.justloginregistertest;

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

import com.free.app.spp.Http;
import com.free.app.spp.NaviActivity;
import com.free.app.spp.R;

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
    private int Result_gec;
    private int Result_rg;
    private String username, password1, password2, emailCode, email;

    private void getEmailCode() {
        Http<JSONArray> http1 = new Http<>();
        http1.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray emailCode) throws JSONException, IOException {
                System.out.println("email_result: " + emailCode.getJSONObject(0).get("result"));
                System.out.println("email " + email);
                System.out.println("isEmail" + isEmail(email));
                Result_gec = Integer.parseInt(String.valueOf(emailCode.getJSONObject(0).get("result")));
                if (isEmail(email)) {
                    if (Result_gec == 1) {
                        Toast.makeText(RegisterActivity.this, "已发送验证码至邮箱", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "请输入有效的邮箱", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入正确格式的邮箱", Toast.LENGTH_SHORT).show();
                }
            }
        });
        http1.execute("GetEmailCode", email);
    }

    private void Register() {
        Http<JSONArray> http2 = new Http<>();
        http2.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray user) throws JSONException {
                System.out.println("register_result:" + user.getJSONObject(0).get("result"));
                Result_rg = Integer.parseInt(String.valueOf(user.getJSONObject(0).get("result")));
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password1)
                        && !TextUtils.isEmpty(password2) && !TextUtils.isEmpty(emailCode)
                        && !TextUtils.isEmpty(email)) {
                    if (password1.equals(password2)) {
                        if (Result_rg == 1) {
                            Intent intent2 = new Intent(RegisterActivity.this, NaviActivity.class);
                            intent2.putExtra("UserName", username);
                            startActivity(intent2);
                            finish();
                            Toast.makeText(RegisterActivity.this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败，用户名或邮箱已经被注册", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(RegisterActivity.this, "请输入前后一致的密码", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        http2.execute("Register", username, password2, email, emailCode);
    }

    private boolean checkUsername(String name) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9-]{3,20}$");
        Matcher m = pattern.matcher(name);

        return m.matches();
    }

    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
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
            case R.id.bt_registeractivity_codebutton:
                email = registeractivityemail.getText().toString().trim();//获取验证码
                getEmailCode();
                break;
            case R.id.bt_registeractivity_register:    //注册按钮
                //获取用户输入的用户名、密码、邮箱、验证码
                username = mEtRegisteractivityUsername.getText().toString().trim();
                password1 = mEtRegisteractivityPassword1.getText().toString().trim();
                password2 = mEtRegisteractivityPassword2.getText().toString().trim();
                emailCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
                email = registeractivityemail.getText().toString().trim();
                //注册验证
                Register();
                break;
        }
    }
}

