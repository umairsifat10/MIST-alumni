package com.example.imtiaz.mist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Create_Post extends AppCompatActivity {
    private EditText e1;
    private Button button;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private String se1,saveCurrentDate, saveCurrentTime,current_user_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        e1=(EditText) findViewById(R.id.e1);
        button=(Button) findViewById(R.id.button);
        databaseReference=FirebaseDatabase.getInstance().getReference();

        mAuth=FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savePost();
                e1.setText("");
                Intent i = new Intent(getApplicationContext(),Home.class);
                startActivity(i);

            }
        });
    }




    void savePost(){


        //setDate
        se1=e1.getText().toString().trim();
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());
        String postRandomName = saveCurrentDate + saveCurrentTime;
        ///

        ///getUsername


        ///


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String user=preferences.getString("username","");

        PostInformation post=new PostInformation(e1.getText().toString().trim(),user,postRandomName.toString());

        DatabaseReference d=FirebaseDatabase.getInstance().getReference("Post");

        d.child(current_user_id+postRandomName).setValue(post);
    }


}