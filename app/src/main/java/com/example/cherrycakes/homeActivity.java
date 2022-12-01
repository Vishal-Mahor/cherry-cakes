package com.example.cherrycakes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class homeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FirebaseAuth firebaseAuth;
    SearchView searchView;
    TextView location,loc;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView navigation_bar,carts,locationIcon,shopping;
    private long backPresed;
    ImageView round,heart,square,photo,customize,chocolate,red,sponge,fruit;
    FusedLocationProviderClient fusedLocationProviderClient;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<DataSet> list;
    grid_postAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        shopping = findViewById(R.id.shopping_bag);
        locationIcon = findViewById(R.id.locationIcon);
        carts = findViewById(R.id.shopping_bag);
        location = findViewById(R.id.location_bar);
        loc = findViewById(R.id.loc);
        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigation_bar = findViewById(R.id.nav_bar);
        round = findViewById(R.id.fruits);
        heart = findViewById(R.id.heart);
        square = findViewById(R.id.square);
        photo = findViewById(R.id.photo);
        customize = findViewById(R.id.customize);
        chocolate = findViewById(R.id.chocolate);
        searchView = findViewById(R.id.search_view_bar);
        recyclerView = findViewById(R.id.grid_design_layout);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        list = new ArrayList<DataSet>();
        searchView = findViewById(R.id.search_view_bar);


        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        locationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, locationActivity.class);
                startActivity(intent);
            }
        });



        databaseReference = FirebaseDatabase.getInstance().getReference().child("home_screen_cakes/home_screen_cakes");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<DataSet>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    DataSet d = dataSnapshot1.getValue(DataSet.class);
                    list.add(d);
                }
                adapter = new grid_postAdapter(homeActivity.this,list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(homeActivity.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(homeActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(homeActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        Intent intent = getIntent();
        String add = intent.getStringExtra("address");


        navigationDrawer();

//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(homeActivity.this, locationActivity.class);
//                startActivity(intent);
//            }
//        });

        round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, RoundcakesActivity.class);
                startActivity(intent);
            }
        });

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, Heart_cakes_Activity.class);
                startActivity(intent);
            }
        });

        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, square_cakes_Activity.class);
                startActivity(intent);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, photo_cake_Activity.class);
                startActivity(intent);
            }
        });

        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, customize_cakes_Activity.class);
                startActivity(intent);
            }
        });

        chocolate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(homeActivity.this, Chocolate_Activity.class);
                startActivity(intent);
            }
        });


        carts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(homeActivity.this, cart_Activity.class);
//                startActivity(intent);
            }
        });
    }


    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

        navigation_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
            super.onBackPressed();
        super.onBackPressed();


        if(backPresed + 2000 > System.currentTimeMillis()){
            Toast.makeText(this, "Press Back Again To Exit", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            finish();
        }
        backPresed = System.currentTimeMillis();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if(id == R.id.home) {
            Intent intent = new Intent(homeActivity.this, homeActivity.class);
            startActivity(intent);
        }
        if(id == R.id.account) {
            Intent intent = new Intent(homeActivity.this, Profile_Activity.class);
            startActivity(intent);
        }
        if(id == R.id.orders) {
            Intent intent = new Intent(homeActivity.this, OrderActivity.class);
            startActivity(intent);
        }
        if(id == R.id.exit) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(homeActivity.this, welcomeActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null){

                    try {
                        Geocoder geocoder = new Geocoder(homeActivity.this,
                                Locale.getDefault());

                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        loc.setText(Html.fromHtml(String.valueOf(addresses.get(0).getLocality())));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}