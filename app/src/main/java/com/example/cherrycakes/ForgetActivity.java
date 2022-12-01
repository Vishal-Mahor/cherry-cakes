package com.example.cherrycakes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {

    TextView loginbtn;
    EditText txtemail;
    Button resetbtn;
    FirebaseAuth firebaseAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        loginbtn = findViewById(R.id.loginbtn);
        txtemail = findViewById(R.id.forgettext);
        resetbtn = findViewById(R.id.resetbtn);
        firebaseAuth = FirebaseAuth.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }

    private void validate() {

        email = txtemail.getText().toString().trim();
        if(email.isEmpty()){
            Toast.makeText(this, "Email-Address is Required", Toast.LENGTH_SHORT).show();
        }else{
            forgetpass();
        }

    }

    private void forgetpass() {
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgetActivity.this, "Check Registered Email's Box", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(ForgetActivity.this, "Failed to Send link "+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}