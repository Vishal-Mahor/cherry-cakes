package com.example.cherrycakes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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

public class OrderActivity extends AppCompatActivity {
    ImageView back;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<DataSet> list;
    postAdapter adapter;
    TextView blank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        blank = findViewById(R.id.blank);
        back = findViewById(R.id.backtodashboard);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<DataSet>();


        databaseReference = FirebaseDatabase.getInstance().getReference().child("order/ordered_cakes");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<DataSet>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    DataSet d = dataSnapshot1.getValue(DataSet.class);
                    list.add(d);
                }
                adapter = new postAdapter(OrderActivity.this,list);
                recyclerView.setAdapter(adapter);
                if (adapter==null){
                    blank.setVisibility(View.VISIBLE);
                }
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(OrderActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, homeActivity.class);
                startActivity(intent);
            }
        });

    }

}