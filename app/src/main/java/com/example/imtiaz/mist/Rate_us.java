package com.example.imtiaz.mist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rate_us extends AppCompatActivity {
    private RatingBar ratingBar;
    private Button rateSubmit;
    DatabaseReference databaseReference;
    String key = null;
    ProgressDialog progressdialogue;
    SharedPreferences sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        ratingBar = findViewById(R.id.ratebar);
        rateSubmit = findViewById(R.id.ratesubmit);
        databaseReference = FirebaseDatabase.getInstance().getReference("student");

        sid = this.getApplicationContext().getSharedPreferences("sid", MODE_PRIVATE);

        progressdialogue = new ProgressDialog(this);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    student value = data.getValue(student.class);
                    Toast.makeText(getApplicationContext(), value.getScontact(), Toast.LENGTH_SHORT).show();
                    key = value.getSid();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rateSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                String key = sid.getString("sid", "");
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rating");
                reference.child(key).setValue(String.valueOf(ratingBar.getRating()));
                progressdialogue.setMessage("Saving");
                progressdialogue.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Rate_us.this, Morepage.class));
                        progressdialogue.dismiss();
                    }
                }, 2000);
            }
        });

    }


}
