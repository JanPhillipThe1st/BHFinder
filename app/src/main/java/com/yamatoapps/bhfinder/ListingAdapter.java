package com.yamatoapps.bhfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListingAdapter extends ArrayAdapter<BoardingHouse> {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public ListingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BoardingHouse> objects) {
        super(context, 0, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BoardingHouse boardingHouse = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bh_card, parent, false);
        }

        ImageView ivBH = (ImageView)convertView.findViewById(R.id.ivBH);
        TextView tvBHName = (TextView)convertView.findViewById(R.id.tvBHName);
        TextView tvBHLocation = (TextView)convertView.findViewById(R.id.tvBHLocation);
        TextView tvRate = (TextView)convertView.findViewById(R.id.tvRate);
        TextView tvCapacity = (TextView)convertView.findViewById(R.id.tvCapacity);
        TextView tvType = (TextView)convertView.findViewById(R.id.tvType);
        TextView tvFeatures = (TextView)convertView.findViewById(R.id.tvFeatures);
        Button btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
        Button btnEdit = (Button) convertView.findViewById(R.id.btnEdit);

        //Since it's an adapter, we must bind the data associated with it yeah?

        Picasso.get().load(boardingHouse.image).into(ivBH);
        tvCapacity.setText("Max: "+boardingHouse.capacity);
        tvBHLocation.setText(boardingHouse.location);
        tvBHName.setText(boardingHouse.name);
        tvRate.setText("Monthly: "+ String.valueOf(boardingHouse.rate));
        tvType.setText(boardingHouse.type);
        tvFeatures.setText(boardingHouse.features);
        btnDelete.setOnClickListener(view -> {
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
            alertDialogBuilder.setTitle("Delete Listing");
            alertDialogBuilder.setMessage("Are you sure you want to delete this information?");
            alertDialogBuilder.setPositiveButton("NO", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            alertDialogBuilder.setNegativeButton("YES", (dialogInterface, i) -> {

                MaterialAlertDialogBuilder deleteDialogBuilder = new MaterialAlertDialogBuilder(parent.getContext());
                deleteDialogBuilder.setTitle("Delete success");
                deleteDialogBuilder.setMessage("Boarding house information deleted successfully!");
                deleteDialogBuilder.setPositiveButton("OK", (deleteDialogBuilderDialogInterface,j)->{
                    deleteDialogBuilderDialogInterface.dismiss();
                    Activity context = (Activity) parent.getContext();
                });
                db.collection("listings").document(boardingHouse.id).delete().addOnSuccessListener(unused -> {
                    deleteDialogBuilder.create().show();
                    dialogInterface.dismiss();
                });
            });
            alertDialogBuilder.create().show();
        });
        btnEdit.setOnClickListener(view -> {
            Intent intent = new Intent(parent.getContext(), EditBoarding.class);
            intent.putExtra("document_id",boardingHouse.id);
            parent.getContext().startActivity(intent);
        });
        return convertView;
    }
}
