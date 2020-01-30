package com.example.imtiaz.mist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Edit_profile extends AppCompatActivity {

    private static final int CHOOSE_Image = 101;
    private ProgressDialog progressDialog;
    Button sButton;
    ImageView propic;
    Uri profilepicture;
    EditText etfmane, etid, etcontact, etprofession, etdesignation,etlocation;
    String profileImageUrl,displayName;
    FirebaseAuth mAuth;
    Spinner spdepartment, spyr;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        sButton = findViewById(R.id.submitbtn);
        propic = findViewById(R.id.profile_pic);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        etfmane = findViewById(R.id.fullname);
        etid = findViewById(R.id.roll);
        etcontact = findViewById(R.id.mobile_no);
        etprofession = findViewById(R.id.profession);
        etdesignation = findViewById(R.id.designation);
        etlocation = findViewById(R.id.location);
        //spdepartment = findViewById(R.id.department);
        //spyr = findViewById(R.id.year_of_pass);
        databaseReference = FirebaseDatabase.getInstance().getReference("student");

        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getApplicationContext(),Morepage.class);
                //startActivity(i);
                saveUserInformation();
                addUser();
            }
        });

        propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });
    }

    private void saveUserInformation() {
        displayName = etfmane.getText().toString();

        if(displayName.isEmpty()){
            etfmane.setError("Name required!");
            etfmane.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null && profileImageUrl != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profileChangeRequest)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Profile is Updated", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(),Morepage.class);
                                startActivity(i);
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_Image && resultCode == RESULT_OK && data != null && data.getData() != null){
            profilepicture = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),profilepicture);
                propic.setImageBitmap(bitmap);

                uploadImagetoFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImagetoFirebaseStorage() {
        String id = etid.getText().toString();
        StorageReference profilepicRef = FirebaseStorage.getInstance().getReference("profilepics/"+id+ ".jpg");

        if(profilepicture != null){
            progressDialog.setMessage("Please Wait! ! !");
            progressDialog.show();
            profilepicRef.putFile(profilepicture)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
            progressDialog.dismiss();
        }
    }

    public void showImageChooser(){
        Intent i = new Intent();
        i.setType("image/*");
        Intent intent = i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Profile Picture"),CHOOSE_Image);
    }

    private  void addUser(){
        String fname = etfmane.getText().toString();
        String id = etid.getText().toString();
        //String department = spdepartment.getSelectedItem().toString();
        //String year = spyr.getSelectedItem().toString();
        String contact = etcontact.getText().toString();
        String profession = etprofession.getText().toString();
        String designation = etdesignation.getText().toString();
        String location = etlocation.getText().toString();
        String email = mAuth.getCurrentUser().getEmail();

        if(!TextUtils.isEmpty(fname)){
            student st = new student(fname, id, contact, profession, designation, location,email);
            databaseReference.child(id).setValue(st);
        }
        else{
        }
    }
}
