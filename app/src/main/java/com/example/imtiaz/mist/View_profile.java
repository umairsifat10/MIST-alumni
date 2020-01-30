package com.example.imtiaz.mist;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_profile extends AppCompatActivity {

    Button eButton;
    FirebaseAuth mAuth;
    ImageView view_propic;
    TextView vname, vid, vdepartment, vyear, vprofession, vdesignation, vemail, vcontact, vlocation,vemailverification;
    DatabaseReference databaseReference;
    List<student> allStudent;
    student finalst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        eButton = findViewById(R.id.editbtn);
        mAuth = FirebaseAuth.getInstance();
        view_propic = findViewById(R.id.view_profile_pic);

        vname = findViewById(R.id.view_name);
        vid = findViewById(R.id.view_id_number);
        vdepartment = findViewById(R.id.view_department);
        vyear = findViewById(R.id.view_year_of_pass);
        vprofession = findViewById(R.id.view_profession);
        vdesignation = findViewById(R.id.view_designation);
        vemail = findViewById(R.id.view_email);
        vcontact = findViewById(R.id.view_contact_no);
        vlocation = findViewById(R.id.view_location);
        vemailverification = findViewById(R.id.view_varified);
        allStudent = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("student");


        loadUserInformation();

        eButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Edit_profile.class);
                startActivity(i);
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if(mAuth.getCurrentUser() == null){
//            Intent i = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(i);
//        }
//    }

    private void loadUserInformation() {

        final FirebaseUser user = mAuth.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    student value = data.getValue(student.class);
                    Toast.makeText(getApplicationContext(),value.getScontact(),Toast.LENGTH_SHORT).show();
                    allStudent.add(value);
                        if(value.semail.equals(mAuth.getCurrentUser().getEmail()) ){
                            finalst = value;
                            vid.setText(finalst.sid);
                            vprofession.setText(finalst.sprofession);
                            vdesignation.setText(finalst.sdesignation);
                            vemail.setText(finalst.semail);
                            vcontact.setText(finalst.scontact);
                            vlocation.setText(finalst.slocation);
                        }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(user != null){
            if(user.getPhotoUrl() != null){
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(view_propic);
            }
            if(user.getDisplayName()!= null){
                vname.setText(user.getDisplayName());
            }

            if(user.isEmailVerified()){
                vemailverification.setText("User is varified");
            }
            else {
                vemailverification.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //Toast.makeText(getApplicationContext(),"Verification Email Send",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }

        }
    }
}
