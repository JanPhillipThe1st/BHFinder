package com.yamatoapps.bhfinder;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ManageBoardingHouse extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_boarding_house);
        ListView lvBoardingHouses = findViewById(R.id.lvBoardinghouses);
        ArrayList<BoardingHouse> boardingHouseArrayList= new ArrayList<BoardingHouse>();
        ListingAdapter adapter = new ListingAdapter(this,0, boardingHouseArrayList);
        db.collection("listings").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    adapter.add(new BoardingHouse(document.getString("name"),document.getString("image_url"),
                            document.getLong("capacity").intValue(),document.getString("location"),Double.parseDouble(document.get("rate").toString()),"Room",document.getString("other_features"),
                            document.getId()));
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
        lvBoardingHouses.setAdapter(adapter);
    }
}