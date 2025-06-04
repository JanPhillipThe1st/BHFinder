package com.yamatoapps.bhfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EditBoarding extends AppCompatActivity {
    Uri fileUri;
    String document_id = "";
    Button btnPost;
    String image_url = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ProgressDialog progressDialog;
    ImageView iv;
    TextView tvName,tvLocation,tvMonthlyRate,tvCapacity,tvOtherFeatures;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView iv = findViewById(R.id.ivListingPhoto);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 22 && resultCode == RESULT_OK && data != null && data != null) {
            fileUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),fileUri);
                iv.setImageBitmap(bitmap);
                uploadImage();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_boarding);
        androidx.appcompat.widget.Toolbar toolbar2 = findViewById(R.id.toolbar2) ;
        btnPost = findViewById(R.id.btnPost) ;
        Button btnUploadImage = findViewById(R.id.btnUploadImage) ;
        tvName = findViewById(R.id.textInputName);
        tvLocation= findViewById(R.id.textInputLocation);
        tvMonthlyRate= findViewById(R.id.textInputRate);
        tvCapacity= findViewById(R.id.textInputCapacity);
        tvOtherFeatures= findViewById(R.id.textInputFeatures);
         iv = findViewById(R.id.ivListingPhoto);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.setMessage("Posting your Boarding House..");

         document_id = getIntent().getStringExtra("document_id");
        db.collection("listings").document(document_id).get().addOnSuccessListener(
                documentSnapshot -> {
                    tvName.setText(documentSnapshot.getString("name"));
                    tvLocation.setText(documentSnapshot.getString("location"));
                    tvMonthlyRate.setText(documentSnapshot.getLong("rate").toString());
                    tvCapacity.setText(documentSnapshot.get("capacity").toString());
                    tvOtherFeatures.setText(documentSnapshot.getString("other_features"));
                    Picasso.get().load(documentSnapshot.getString("image_url")).into(iv);
                    image_url = documentSnapshot.getString("image_url");
                }
        );
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        Intent imageIntent = new Intent();
        toolbar2.setNavigationOnClickListener(view ->{
            finish();
        });

        btnUploadImage.setOnClickListener(view ->{
            imageIntent.setType("image/*");
            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(imageIntent,"Pick image to upload"),22);
        });
        btnPost.setOnClickListener(view ->{
            Map<String, Object> listing = new HashMap<>();
            listing.put("name", tvName.getText().toString());
            listing.put("location",  tvLocation.getText().toString());
            listing.put("rate",  Double.parseDouble(tvMonthlyRate.getText().toString()));
            listing.put("capacity", Integer.parseInt(tvCapacity.getText().toString()));
            listing.put("other_features",  tvOtherFeatures.getText().toString());
            listing.put("image_url",  image_url);
            DocumentReference newListingRef = db.collection("listings").document(document_id);
            newListingRef.update(listing).addOnSuccessListener(unused -> {
                Toast.makeText(getApplicationContext(), "Boarding house information successfully updated!", Toast.LENGTH_SHORT).show();
            });

        });

    }
    public  void uploadImage(){
        if (fileUri != null){
            progressDialog.show();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child(UUID.randomUUID().toString());
            UploadTask uploadTask = (UploadTask) storageReference.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {

            }).addOnFailureListener(listener->{
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Fail to Upload Image..", Toast.LENGTH_SHORT)
                        .show();
            });
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return storageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        image_url = task.getResult().toString();
                        btnPost.setEnabled(true);
                        progressDialog.dismiss();
                 } else {
                        // Handle failures
                        // ...
                    }
                }
            });

        }
    }
}