package com.yamatoapps.bhfinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.LogRecord;

public class BHAdapter extends ArrayAdapter<BoardingHouse> {

    public BHAdapter(@NonNull Context context, ArrayList<BoardingHouse> boardingHouses) {
        super(context, 0, boardingHouses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BoardingHouse boardingHouse = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.house_card_item, parent, false);
        }

        ImageView image = (ImageView)convertView.findViewById(R.id.ivPhoto);
        TextView tvCapacity = (TextView)convertView.findViewById(R.id.tvCapacity);
        TextView tvBHName = (TextView)convertView.findViewById(R.id.tvBHName);
        TextView tvLocation = (TextView)convertView.findViewById(R.id.tvLocation);
        TextView tvRate = (TextView)convertView.findViewById(R.id.tvRate);
        TextView tvType = (TextView)convertView.findViewById(R.id.tvType);
        TextView tvFeatures = (TextView)convertView.findViewById(R.id.tvFeatures);

        //Since it's an adapter, we must bind the data associated with it yeah?

            Picasso.get().load(boardingHouse.image).into(image);
        Log.e("Image","Successfully loaded image!" + image.getResources());
        tvCapacity.setText("Max: "+boardingHouse.capacity);
        tvLocation.setText(boardingHouse.location);
        tvBHName.setText(boardingHouse.name);
        tvRate.setText("Monthly: "+ String.valueOf(boardingHouse.rate));
        tvType.setText(boardingHouse.type);
        tvFeatures.setText(boardingHouse.features);
        return convertView;
    }


}
