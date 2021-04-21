package com.app.cashcalculator.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cashcalculator.MainActivity;
import com.app.cashcalculator.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText fullname, email, password, confirmPassword;
    private String sname, semail, spassword, sconfirm;
    private TextView signup, signin;
    FirebaseAuth mauth, firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mauth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        init();
        listners();
    }

    private void listners() {
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {
        sname = fullname.getText().toString().trim();
        semail = email.getText().toString().trim();
        spassword = password.getText().toString().trim();
        sconfirm = confirmPassword.getText().toString().trim();

        if (sname.equals("")) {
            fullname.setError("Enter fullname");
        } else if (semail.equals("")) {
            email.setError("Enter Email");
        } else if (spassword.equals("")) {
            password.setError("Enter password");
        } else if (sconfirm.equals("")) {
            confirmPassword.setError("Enter confirm password");
        } else {
            MakeAccount();
        }

    }

    private void MakeAccount() {
        mauth.createUserWithEmailAndPassword(semail, spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    String user_id = FirebaseAuth.getInstance().getUid();

                    Map<String, Object> map = new HashMap<>();
                    map.put("email", semail);
                    map.put("Fullname", spassword);

                    firebaseFirestore.collection("userdata").document(firebaseAuth.getCurrentUser().getUid())
                            .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(SignupActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();

                            }

                        }
                    });


                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(SignupActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void init() {
        fullname = findViewById(R.id.activity_signup_edit_text_fullname);
        email = findViewById(R.id.activity_signup_edit_text_email);
        password = findViewById(R.id.activity_signup_edit_text_password);
        confirmPassword = findViewById(R.id.activity_signup_edit_text_confirm_password);
        signup = findViewById(R.id.activity_signup_button_signup);
        signin = findViewById(R.id.activity_signup_text_signin);

    }
}