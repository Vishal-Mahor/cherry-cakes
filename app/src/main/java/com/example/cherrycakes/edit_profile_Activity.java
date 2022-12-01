package com.example.cherrycakes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_profile_Activity extends AppCompatActivity {

    TextView verify_no;
    EditText name,email,address,pincode,mobile;
    Button update;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    TextView users;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_);

        verify_no = findViewById(R.id.verify_nos);
        name = findViewById(R.id.txts_name);
        email = findViewById(R.id.txts_email);
        mobile = findViewById(R.id.txts_mobile);
        address = findViewById(R.id.txts_Address);
        pincode = findViewById(R.id.txts_pincode);
        update = findViewById(R.id.update_profile);
        users = findViewById(R.id.users);

        Intent intent = getIntent();
        String name1 = intent.getStringExtra("name");
        String email1 = intent.getStringExtra("email");
        String mobile1 = intent.getStringExtra("mobile");
        String address1 = intent.getStringExtra("address");
        String pincode1 = intent.getStringExtra("pincode");

//        name.setText(name1);
//        email.setText(email1);
//        mobile.setText(mobile1);
//        address.setText(address1);
//        pincode.setText(pincode1);

        verify_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobiles = mobile.getText().toString().trim();

                Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                intent.putExtra("phoneNo", mobiles);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Users");

                String Name11 = name.getText().toString().trim();
                String Email11 = email.getText().toString().trim();
                String Mobile11 = mobile.getText().toString().trim();
                String Address11 = address.getText().toString().trim();
                String Pincode11 = pincode.getText().toString().trim();
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                get UID
                uid = firebaseUser.getUid();
                
                userHelperClass helperClass = new userHelperClass(Name11, Email11 ,Mobile11, Address11, Pincode11);
                databaseReference.child(uid).setValue(helperClass);
                Toast.makeText(edit_profile_Activity.this, "Data Save Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Profile_Activity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}