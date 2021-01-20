package com.example.koop_login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    /* Define the UI elements */
    private EditText eRegName;
    private EditText eEmail;
    private EditText eRegPassword;
    private Button eBtnReg;
    //private Button eBtnLog;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUpUIViews();
        firebaseAuth = FirebaseAuth.getInstance();

        /* A listener for click events on the Register Button */
        eBtnReg.setOnClickListener(v -> {
            if(validate()){
                String user_email = eEmail.getText().toString().trim();
                String user_pass = eRegPassword.getText().toString().trim();

                firebaseAuth.createUserWithEmailAndPassword(user_email,user_pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        sendEmailVerification();
                    }else{
                        Toast.makeText(RegistrationActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });

    }

    private void setUpUIViews() {
        eRegName = (EditText)findViewById(R.id.userNameRA);
        eRegPassword = (EditText)findViewById(R.id.passRA);
        eEmail = (EditText)findViewById(R.id.emailRA);
        eBtnReg = (Button)findViewById(R.id.btnRegisterRA);
        //userLogin = (TextView)findViewById(R.id.tvUserLogin);
    }

    private Boolean validate(){
        boolean result = false;

        String name = eRegName.getText().toString();
        String password = eRegPassword.getText().toString();
        String email = eEmail.getText().toString();


        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }


    private void sendEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                }else{
                    Toast.makeText(RegistrationActivity.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
