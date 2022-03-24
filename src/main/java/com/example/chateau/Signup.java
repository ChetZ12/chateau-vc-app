package com.example.chateau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signup extends AppCompatActivity {

    FirebaseAuth auth;
    EditText emailbox, passbox, namebox;
    Button havebtn, createbtn;

    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseFirestore.getInstance();

        auth = FirebaseAuth.getInstance();

        emailbox = findViewById(R.id.signupemailbox);
        passbox = findViewById(R.id.signuppassbox);
        namebox = findViewById(R.id.signupnamebox);

        havebtn = findViewById(R.id.havebtn);
        createbtn = findViewById(R.id.createbtn);


        createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass,name;
                email = emailbox.getText().toString();
                pass = passbox.getText().toString();
                name = namebox.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            database.collection("Users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(Signup.this, LoginPage.class));
                                }
                            });
                            Toast.makeText(Signup.this,"Account created",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Signup.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


    }
}