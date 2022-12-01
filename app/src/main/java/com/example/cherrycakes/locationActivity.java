package com.example.cherrycakes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class locationActivity extends AppCompatActivity {

    Button getloc;
    ImageView back_to_home;
    TextView t1, t2, t3, t4, t5;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        back_to_home = findViewById(R.id.back_to_home);
        getloc = findViewById(R.id.btngetloc);
        t1 = findViewById(R.id.latitudes);
        t2 = findViewById(R.id.longitudes);
        t3 = findViewById(R.id.address1);
        t4 = findViewById(R.id.localitys);
        t5 = findViewById(R.id.country1);


        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = t3.getText().toString();
                Intent intent = new Intent(getApplicationContext(), homeActivity.class);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(locationActivity.this
                        , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(locationActivity.this
                            , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
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
                        Geocoder geocoder = new Geocoder(locationActivity.this,
                                Locale.getDefault());

                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );

                        t1.setText(Html.fromHtml(String.valueOf(addresses.get(0).getLatitude())));
                        t2.setText(Html.fromHtml(String.valueOf(addresses.get(0).getLongitude())));
                        t3.setText(Html.fromHtml(String.valueOf(addresses.get(0).getAddressLine(0))));
                        t4.setText(Html.fromHtml(String.valueOf(addresses.get(0).getLocality())));
                        t5.setText(Html.fromHtml(String.valueOf(addresses.get(0).getCountryName())));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}