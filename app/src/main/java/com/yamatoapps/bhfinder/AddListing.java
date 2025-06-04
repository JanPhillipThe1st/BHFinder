package com.yamatoapps.bhfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AddListing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_listing);
        Button btnRoomBooking = findViewById(R.id.btnRoomBooking), btnBedspacerBooking= findViewById(R.id.btnBedspacerBooking);
        Button btnManage = findViewById(R.id.btnManage);
        Intent addBoarding = new Intent(this,AddBoarding.class);

        btnRoomBooking.setOnClickListener(view ->{
        addBoarding.putExtra("type","room");
        startActivity(addBoarding);
        });

        btnBedspacerBooking.setOnClickListener(view ->{
            addBoarding.putExtra("type","bedspacer");
            startActivity(addBoarding);
        });
        btnManage.setOnClickListener(view -> {

            Intent intent = new Intent(AddListing.this, ManageBoardingHouse.class);
            startActivity(intent);
        });
    }
}