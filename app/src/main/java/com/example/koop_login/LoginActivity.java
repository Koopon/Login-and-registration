package com.example.koop_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.koop_login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    /* Define the UI elements */
    private EditText eEmail;
    private EditText ePassword;
    //private TextView btnReg;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Bind the XML views to Java Code Elements */
        eEmail = findViewById(R.id.emailLA);
        ePassword = findViewById(R.id.passLA);
        Button btnLogin = findViewById(R.id.btnLoginLA);
        //btnReg = findViewById(R.id."")
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null){
            finish();
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }

        /* Describe the logic when the login button is clicked */
        btnLogin.setOnClickListener(view -> validate(eEmail.getText().toString(), ePassword.getText().toString()));

    }

    /* Validate the credentials */
    private void validate(String userEmail, String userPassword) {

        progressDialog.setMessage("Get ready to swipe!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                progressDialog.dismiss();
                //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                checkEmailVerification();
            }else{
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        boolean emailflag = firebaseUser.isEmailVerified();

        if(emailflag){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else{
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}