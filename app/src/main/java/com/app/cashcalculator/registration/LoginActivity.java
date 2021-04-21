package com.app.cashcalculator.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.cashcalculator.R;

public class LoginActivity extends AppCompatActivity {
    private EditText email,password;
    private TextView forgotPassword,login,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        listners();
    }

    private void listners() {
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        email=findViewById(R.id.activity_signin_edittext_email);
        password=findViewById(R.id.activity_signin_edittext_password);
        forgotPassword=findViewById(R.id.activity_signin_text_forgot);
        login=findViewById(R.id.activity_signin_button_login);
        signup=findViewById(R.id.activity_signin_text_signup);
    }
}