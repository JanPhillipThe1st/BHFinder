package com.yamatoapps.bhfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    boolean btnStudentsSelected = false;
    boolean btnRoomSelected = false;
    boolean btnFamilySelected = false;
    boolean btnBedSpacerSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Button btnStudents = (Button) findViewById(R.id.btnStudents);
        Button btnRoom = (Button)findViewById(R.id.btnRoom);
        Button btnList = (Button)findViewById(R.id.btnList);
        Button btnFamily = (Button)findViewById(R.id.btnFamily);
        Button btnBedSpacer = (Button)findViewById(R.id.btnBedspacer);
        Button btnSearch = (Button)findViewById(R.id.btnSearch);
        Intent searchIntent = new Intent(this,Search.class);
        Intent addListing = new Intent(this,AddListing.class);


        btnList.setOnClickListener(view -> {
            startActivity(addListing);
        });
        btnStudents.setOnClickListener(view -> {
            if (btnStudentsSelected){
                btnStudentsSelected = false;
                searchIntent.removeExtra("good_for");
                btnStudents.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            else{
                btnStudentsSelected = true;
                searchIntent.putExtra("good_for","Student");
                btnStudents.setBackgroundColor(Color.parseColor("#ffafff"));
            }


        });
        btnRoom.setOnClickListener(view -> {
            if (btnRoomSelected){
                btnRoomSelected = false;
                searchIntent.removeExtra("search_type");
                btnRoom.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            else{
                btnRoomSelected = true;
                searchIntent.putExtra("search_type","Room");
                btnRoom.setBackgroundColor(Color.parseColor("#ffafff"));
            }
        });
        btnFamily.setOnClickListener(view -> {
            if (btnFamilySelected){
                btnFamilySelected = false;
                searchIntent.removeExtra("good_for");
                btnFamily.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            else{
                btnFamilySelected = true;
                searchIntent.putExtra("good_for","Family");
                btnFamily.setBackgroundColor(Color.parseColor("#ffafff"));
            }
        });
        btnBedSpacer.setOnClickListener(view -> {
            if (btnBedSpacerSelected){
                btnBedSpacerSelected = false;
                searchIntent.removeExtra("search_type");
                btnBedSpacer.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            else{
                btnBedSpacerSelected = true;
                searchIntent.putExtra("search_type","Bedspacer");
                btnBedSpacer.setBackgroundColor(Color.parseColor("#ffafff"));
            }
        });
        btnSearch.setOnClickListener(view ->{

            if (btnBedSpacerSelected){
                searchIntent.removeExtra("search_type");
                btnRoom.setBackgroundColor(Color.parseColor("#ffffff"));
                searchIntent.putExtra("search_type","Bedspacer");
                btnBedSpacer.setBackgroundColor(Color.parseColor("#ffafff"));
            }
            else{
                searchIntent.removeExtra("search_type");
                btnBedSpacer.setBackgroundColor(Color.parseColor("#ffffff"));
                searchIntent.putExtra("search_type","Room");
                btnRoom.setBackgroundColor(Color.parseColor("#ffafff"));
            }
            startActivity(searchIntent);
        });
    }
}