package com.internshla.bookaholicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profile extends AppCompatActivity {
    private TextView name,Lastname,email,city,state;
    private String NAME,LASTNAME,EMAIL,CITY,STATE;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name=findViewById(R.id.textView5);
        Lastname=findViewById(R.id.textView8);
        email=findViewById(R.id.textView10);
        city=findViewById(R.id.textView12);
        state=findViewById(R.id.textView14);

        FirebaseUser firebaseUser=authProfile.getInstance().getCurrentUser();
        if(firebaseUser==null){
            Toast.makeText(profile.this, "something went wrong", Toast.LENGTH_SHORT).show();
        }
        else{
            showUserProfile(firebaseUser);
        }
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID= firebaseUser.getUid();
        DocumentReference reference;
        FirebaseFirestore firestore =FirebaseFirestore.getInstance();
        reference=firestore.collection("users").document(userID);
        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.getResult().exists()){
                            NAME=task.getResult().getString("Name");
                            LASTNAME=task.getResult().getString("Name");
                            EMAIL=task.getResult().getString("email");
                            CITY=task.getResult().getString("City");
                            STATE=task.getResult().getString("State");

                            name.setText(NAME);
                            Lastname.setText(LASTNAME);
                            email.setText(EMAIL);
                            city.setText(CITY);
                            state.setText(STATE);

                        }else{
                            Toast.makeText(profile.this, "something went wrong", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}