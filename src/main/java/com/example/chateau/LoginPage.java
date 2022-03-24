package com.example.chateau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginPage extends AppCompatActivity {


    EditText lemailbox, lpassbox;
    Button loginbtn, signupbtn;
    FirebaseAuth auth;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        dialog = new ProgressDialog(this);
        dialog.setMessage("please wait...");

        auth = FirebaseAuth.getInstance();

        lemailbox = findViewById(R.id.loginemailbox);
        lpassbox = findViewById(R.id.loginpassbox);

        loginbtn = findViewById(R.id.LoginBtn);
        signupbtn = findViewById(R.id.SignupBtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String email,pass;
                email = lemailbox.getText().toString();
                pass = lpassbox.getText().toString();

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(LoginPage.this,Dashboard.class));
                            Toast.makeText(LoginPage.this, "Logged in", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(LoginPage.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, Signup.class));
            }
        });

    }
}