package com.bwie.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MoreLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_login);
    }
    public void login_finish(View view){
        finish();
    }
}
