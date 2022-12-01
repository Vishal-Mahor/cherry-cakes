package com.example.cherrycakes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phoneActivity extends AppCompatActivity {


    Spinner spin;
    Button generate;
    EditText phone_no;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        spin = findViewById(R.id.spin);
        generate = findViewById(R.id.generate);
        phone_no = findViewById(R.id.number);
        firebaseAuth = FirebaseAuth.getInstance();


        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileNo = phone_no.getText().toString();
                String phoneNo = "+91" + mobileNo;
                if(!phoneNo.isEmpty()){
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(phoneNo)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(phoneActivity.this)
                            .setCallbacks(mCallbacks)
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }else{
                    Toast.makeText(phoneActivity.this, "Phone Number is Missing", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signIn(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(phoneActivity.this, "Authentication Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(phoneActivity.this, "OTP has been Sent", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },2000);

                Intent intent = new Intent(phoneActivity.this, OTPActivity.class);
                intent.putExtra("phoneNo", s);
                startActivity(intent);
            }
        };


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.country_code, android.R.layout.simple_spinner_item);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       spin.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
//           sendToMain();
        }
    }
    private void sendToMain(){
        Intent intent = new Intent(phoneActivity.this, OTPActivity.class);
        startActivity(intent);
        finish();
    }

    private void signIn(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    sendToMain();
                }else{
                    Toast.makeText(phoneActivity.this, "OTP not Generate", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}