package com.organic.contractfarming.farmer;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.organic.contractfarming.DataClass;
import com.organic.contractfarming.R;

import java.text.DateFormat;
import java.util.Calendar;

public class FarmerUploadActivity extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText firstname, lastname, farmercode,wardname,
            clustername,gender,marital_status,dob,phonenumber,
            address,certification,product,id_number;
    String imageURL;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_farmer);

        uploadImage = findViewById(R.id.uploadImage);
        saveButton = findViewById(R.id.saveButton);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        farmercode=findViewById(R.id.farmercode);
        wardname=findViewById(R.id.wardname);
        clustername=findViewById(R.id.clustername);
        gender=findViewById(R.id.marital_status);
        marital_status=findViewById(R.id.marital_status);
        dob=findViewById(R.id.dob);
        phonenumber=findViewById(R.id.phonenumber);
        address=findViewById(R.id.address);
        certification=findViewById(R.id.certification);
        product=findViewById(R.id.product);
        id_number=findViewById(R.id.id_number);



        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadImage.setImageURI(uri);
                        } else {
                            Toast.makeText(FarmerUploadActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("farmer image")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(FarmerUploadActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadData(){

        String firstName = firstname.getText().toString();
        String lastName = lastname.getText().toString();
        String farmerCode = farmercode.getText().toString();
        String wardName = wardname.getText().toString();
        String clusterName = clustername.getText().toString();
        String farmerGender = gender.getText().toString();
        String maritalStatus = marital_status.getText().toString();
        String dateOfBirth = dob.getText().toString();
        String phoneNumber = phonenumber.getText().toString();
        String farmerAddress = address.getText().toString();
        String productCertification = certification.getText().toString();
        String productName = product.getText().toString();
        String idNumber = id_number.getText().toString();

        Farmer farmer=new Farmer(firstName,lastName,farmerCode,wardName,clusterName,farmerGender,maritalStatus,dateOfBirth,phoneNumber,farmerAddress,productCertification,productName,idNumber);




        //We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.

        String currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("Contract Farming").child(currentDate)
                .setValue(farmer).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(FarmerUploadActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FarmerUploadActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
