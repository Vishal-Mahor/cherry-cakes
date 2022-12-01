package com.example.cherrycakes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoundcakesActivity extends AppCompatActivity {

    ImageView back;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<DataSet> list;
    postAdapter adapter;
    Button buynow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roundcakes);

        back = findViewById(R.id.backtodashboard);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<DataSet>();



        databaseReference = FirebaseDatabase.getInstance().getReference().child("anniversary/anniversary_cake");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<DataSet>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    DataSet d = dataSnapshot1.getValue(DataSet.class);
                    list.add(d);
                }
                adapter = new postAdapter(RoundcakesActivity.this,list);
                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RoundcakesActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoundcakesActivity.this, homeActivity.class);
                startActivity(intent);
            }
        });

    }

}