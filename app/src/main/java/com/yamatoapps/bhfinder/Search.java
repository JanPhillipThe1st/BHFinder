package com.yamatoapps.bhfinder;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_search);
        ListView lvBoardingHouses = findViewById(R.id.bhCards);
        ArrayList<BoardingHouse> boardingHouseArrayList= new ArrayList<BoardingHouse>();
        BHAdapter adapter = new BHAdapter(this, boardingHouseArrayList);
        Button btnBack = findViewById(R.id.btnBack);
        TextView tvlSearch = findViewById(R.id.tvlSearch);
        Intent intent = getIntent();
        tvlSearch.setText("Search "+intent.getStringExtra("search_type"));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("listings").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    adapter.add(new BoardingHouse(document.getString("name"),document.getString("image_url"),
                            Integer.parseInt(document.get("capacity").toString()),document.getString("location"),Double.parseDouble(document.get("rate").toString()),"Room",document.getString("other_features"),
                            document.getId()));
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });

       lvBoardingHouses.setAdapter(adapter);
        btnBack.setOnClickListener(view -> {
            finish();
        });
    }
}