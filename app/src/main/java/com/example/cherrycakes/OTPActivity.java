package com.example.cherrycakes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPActivity extends AppCompatActivity {

    Button verify_no;
    TextView go_to_phone,header,t4;
    EditText otp;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        verify_no = findViewById(R.id.verify_no);
        go_to_phone = findViewById(R.id.go_to_phone);
        otp = findViewById(R.id.otp);
        header = findViewById(R.id.header);
        progressBar = findViewById(R.id.progress_bar);
        firebaseAuth = FirebaseAuth.getInstance();



        verify_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String mobile_Nos = intent.getStringExtra("phoneNo");
                String verify_code = otp.getText().toString();
                if(!verify_code.isEmpty()){
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mobile_Nos, verify_code);
                    signIn(credential);
                }else{
                    Toast.makeText(OTPActivity.this, "Enter One Time Password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void signIn(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendToMain();
                }else{
                    Toast.makeText(OTPActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendToMain() {
        Intent intent = new Intent(OTPActivity.this, homeActivity.class);
        startActivity(intent);
        finish();
    }

}