package com.bwie.module;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MoreLoginActivity extends AppCompatActivity implements View.OnClickListener{


    private  EventHandler eventHandler;
    private EditText et_phone;
    private EditText et_yanzhengma;
    private TextView send_msg;
    private Button login;
    private int TIME=5;

    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            TIME--;
            if(TIME==0){
                handler.removeCallbacks(this);
                send_msg.setText("再次获取");
                send_msg.setEnabled(true);
            }else{
                send_msg.setEnabled(false);
                send_msg.setText(TIME+"s");
                handler.postDelayed(this,1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_login);
        initview();
        registerSMS();
    }

    private void registerSMS() {
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MoreLoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MoreLoginActivity.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                            //获取验证码成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MoreLoginActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                            //返回支持发送验证码的国家列表
                        }
                    }else{
                        ((Throwable)data).printStackTrace();
                    }
                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);


    }

    private void initview() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_yanzhengma = (EditText) findViewById(R.id.et_yanzhengma);
        send_msg = (TextView) findViewById(R.id.send_msg);
        login = (Button) findViewById(R.id.login);
        send_msg.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    public void login_finish(View view){
        finish();
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.send_msg:
                    if(!TextUtils.isEmpty(et_phone.getText().toString())){
                        SMSSDK.getVerificationCode("86",et_phone.getText().toString());
                        handler.postDelayed(runnable,1000);
                    }else{
                        Toast.makeText(this, "手机号为空", Toast.LENGTH_SHORT).show();
                    }
                    
                    break;
                case R.id.login:
                    SMSSDK.submitVerificationCode("86",et_phone.getText().toString(),et_yanzhengma.getText().toString());
                    break;
            }
    }
}
