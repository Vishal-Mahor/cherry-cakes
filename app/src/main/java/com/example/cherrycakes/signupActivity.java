package com.example.cherrycakes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signupActivity extends AppCompatActivity {

    Button register;
    EditText email, password, cpassword;
    TextView go_to_login;
    ImageView imageView;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        register = findViewById(R.id.btn_register);
        email = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        cpassword = findViewById(R.id.txt_cpassword);
        go_to_login = findViewById(R.id.go_to_login);
        imageView = findViewById(R.id.imageView2);

        go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signupActivity.this, phoneActivity.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String cpass = cpassword.getText().toString().trim();

                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(signupActivity.this, "Enter Email-Address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(signupActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(cpass)){
                    Toast.makeText(signupActivity.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(pass.length()<6){
                    Toast.makeText(signupActivity.this, "Choose strong Password", Toast.LENGTH_SHORT).show();
                }

                if(pass.equals(cpass)){

                    firebaseAuth.createUserWithEmailAndPassword(mail,pass)
                            .addOnCompleteListener(signupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        startActivity(new Intent(getApplicationContext(), phoneActivity.class));
                                        Toast.makeText(signupActivity.this, "Successfully Regestered", Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(signupActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }else{
                    Toast.makeText(signupActivity.this, "Password Not Match", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}