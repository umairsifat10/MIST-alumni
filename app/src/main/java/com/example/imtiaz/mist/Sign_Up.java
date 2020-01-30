package com.example.imtiaz.mist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Sign_Up extends AppCompatActivity {

    private Button bRegister;
    private EditText etpass, etemail, etcpass;
    private String email, password, cpass;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);

        bRegister = findViewById(R.id.registerbtn);
        etemail = findViewById(R.id.email);
        etpass = findViewById(R.id.password1);
        etcpass = findViewById(R.id.confirm_password);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etemail.getText().toString().trim();
                password = etpass.getText().toString().trim();
                cpass = etcpass.getText().toString().trim();

                if(email.isEmpty()){
                    etemail.setError("Email is required!");
                    etemail.requestFocus();
                    return;
                }

                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etemail.setError("Please enter a valid email address");
                }

                else if(password.isEmpty()){
                    etpass.setError("Password is required!");
                    etpass.requestFocus();
                    return;
                }
                else if(password.length()<6){
                    etpass.setError("Minimum length of password is 6");
                    etpass.requestFocus();
                    return;
                }
                else if(!password.equals(cpass)){
                    etcpass.setError("Password do not match!");
                    etcpass.requestFocus();
                    return;
                }
                else{
                    createAccount();
                }
            }
        });
    }

    void createAccount(){


        if(!email.isEmpty() && !password.isEmpty()) {
            progressDialog.setMessage("Please wait!!!");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), " Create Success ", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),Sign_Up_Details.class);
                                startActivity(i);
                            } else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException ){
                                    Toast.makeText(getApplicationContext(),"You are already registered", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                            progressDialog.dismiss();
                        }}); }

    }
}
