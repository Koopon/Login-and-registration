package com.example.koopon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.koop_login.R;

public class WelcomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);

        sharedPreferences = getSharedPreferences("prefs",MODE_PRIVATE);
        firstTime = sharedPreferences.getBoolean("firstTime",true);

        if(firstTime){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            firstTime = false;
            editor.putBoolean("firstTime",firstTime);
            editor.apply();

            if (Build.VERSION.SDK_INT >= 21) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }

            // Bind the XML views to Java Code Elements
            Button eSignIn = findViewById(R.id.btnSigninI);
            Button eLogin = findViewById(R.id.btnLoginI);

            // switch that allows going into the next activity by clicking the button
            eSignIn.setOnClickListener(view -> startActivity(new Intent(WelcomeActivity.this, com.example.koopon.RegistrationActivity.class)));
            eLogin.setOnClickListener(view -> startActivity(new Intent(WelcomeActivity.this, com.example.koopon.LoginActivity.class)));


        }else{
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            finish();
        }

    }







}