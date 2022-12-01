package com.example.cherrycakes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Profile_Activity extends AppCompatActivity {

    Button edi_profile;
    TextView name, email,mobile,address,pincode,logout_txt,changepass;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    String uid;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

        changepass = findViewById(R.id.changepass);
        logout_txt = findViewById(R.id.logout_txt);
        edi_profile = findViewById(R.id.edit_profile);
        name = findViewById(R.id.Auth_name);
        email = findViewById(R.id.Auth_email);
        mobile = findViewById(R.id.txt_mob);
        address = findViewById(R.id.txt_add);
        pincode = findViewById(R.id.txt_pin);
        imageView = findViewById(R.id.current_img);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("Users").child(uid).child("name").getValue(String.class));
                email.setText(snapshot.child("Users").child(uid).child("email").getValue(String.class));
                mobile.setText(snapshot.child("Users").child(uid).child("mobile").getValue(String.class));
                address.setText(snapshot.child("Users").child(uid).child("address").getValue(String.class));
                pincode.setText(snapshot.child("Users").child(uid).child("pincode").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile_Activity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        edi_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String names = name.getText().toString().trim();
                String emails = email.getText().toString().trim();
                String mobiles = mobile.getText().toString().trim();
                String addresss = address.getText().toString().trim();
                String pincodes = pincode.getText().toString().trim();


                Intent intent = new Intent(Profile_Activity.this, edit_profile_Activity.class);
                intent.putExtra("name",names);
                intent.putExtra("email",emails);
                intent.putExtra("mobile",mobiles);
                intent.putExtra("address",addresss);
                intent.putExtra("pincode",pincodes);
                startActivity(intent);
            }
        });

        logout_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Profile_Activity.this, welcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Profile_Activity.this, ForgetActivity.class);
                startActivity(intent);
            }
        });

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            Picasso.get().load(signInAccount.getPhotoUrl()).into(imageView);
        }

    }
}