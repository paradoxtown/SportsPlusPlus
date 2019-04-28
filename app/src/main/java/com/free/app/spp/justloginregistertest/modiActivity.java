package com.free.app.spp.justloginregistertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.free.app.spp.Http;
import com.free.app.spp.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class modiActivity extends AppCompatActivity {
    private String username;
    private String password1,password2;
    private int Result_cw;

    private void  changePassword(){
        Http<JSONArray> http1 = new Http<>();
        http1.setListener(new Http.OnResponseListener<JSONArray>() {
            @Override
            public void onResponse(JSONArray Change) throws JSONException, IOException {
                System.out.println("result:"+Change.getJSONObject(0).get("result"));
                Result_cw = Integer.parseInt(String.valueOf(Change.getJSONObject(0).get("result")));
                if(!TextUtils.isEmpty(password1)  && !TextUtils.isEmpty(password2) && !TextUtils.isEmpty(username)){
                    if(Result_cw == 1){
                        Intent intent2 = new Intent(modiActivity.this, loginActivity.class);
                        startActivity(intent2);
                        finish();
                        Toast.makeText(modiActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(modiActivity.this, "密码修改失败，请检查是否正确输入", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(modiActivity.this, "输入信息不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });
        http1.execute("Changepassword",username,password1,password2);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi);

        ButterKnife.bind(this);

    }
    @BindView(R.id.rl_modiactivity_top)
    RelativeLayout ModiactivityTop;
    @BindView(R.id.iv_modiactivity_back)
    ImageView ModiactivityBack;
    @BindView(R.id.ll_modiactivity_body)
    LinearLayout ModiacttivityBody;
    @BindView(R.id.et_modiactivity_username)
    EditText Username;
    @BindView(R.id.et_modiactivity_password2)
    EditText Modipassword2;
    @BindView(R.id.et_modiactivity_password3)
    EditText Modipassword3;
    @BindView(R.id.bt_modiactivity_button)
    Button ModiButton;

    @OnClick({
            R.id.iv_modiactivity_back,
            R.id.bt_modiactivity_button
    })


    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_modiactivity_back:
                Intent intent1 = new Intent(modiActivity.this, loginActivity.class );
                startActivity(intent1);
                finish();
                break;
            case R.id.bt_modiactivity_button:
                username = Username.getText().toString().trim();
                password1 = Modipassword2.getText().toString().trim();
                password2 = Modipassword3.getText().toString().trim();
                changePassword();
                break;
        }

    }


}
