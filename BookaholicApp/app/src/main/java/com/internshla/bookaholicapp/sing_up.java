package com.internshla.bookaholicapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class sing_up extends AppCompatActivity {

    EditText inputUsername, inputPassword,inputEmail,inputConfirmPassword,inputPhoneNumber;
    EditText inputState ;
    EditText inputCity ;
    Button button;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth  mAuth;
    FirebaseUser mUser;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        inputUsername=findViewById(R.id.username);
        inputPassword=findViewById(R.id.password);
        inputEmail=findViewById(R.id.emailid);
        inputConfirmPassword=findViewById(R.id.confirmpassword);
        inputPhoneNumber=findViewById(R.id.phonenum);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        fStore=FirebaseFirestore.getInstance();
        inputState =findViewById(R.id.dB);
        inputCity = findViewById(R.id.city);
        button=findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PerforAuth();
            }
        });


    }

    private void PerforAuth() {
        String Username= inputUsername.getText().toString();
        String email= inputEmail.getText().toString();
        String phone= inputPhoneNumber.getText().toString();
        String state= inputState.getText().toString();
        String city= inputCity.getText().toString();
        String password= inputPassword.getText().toString();
        String confirm= inputConfirmPassword.getText().toString();
        if(!email.matches(emailPattern)){
            inputEmail.setError("ENTER CORRECT EMAIL");

        }
        else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("ENTER VALID PASSWORD");
        }
        else if (!password.equals(confirm)){
            inputConfirmPassword.setError("PASSWORD NOT MATCHED");
        }
        else if (phone.length()!=10){
            inputPhoneNumber.setError("ENTER VALID PHONE NUMBER");
        }
        else{
            progressDialog.setMessage("Registering.....");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(sing_up.this,"REGISTRATION SUCCESSFUL",Toast.LENGTH_SHORT).show();
                        userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                        DocumentReference documentReference= fStore.collection("users").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("Name",Username);
                        user.put("email",email);
                        user.put("PhoneNumber",phone);
                        user.put("State",state);
                        user.put("City",city);
                        documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(sing_up.this, "REGISTRATION SUCCESSFUL", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(sing_up.this, "Failed to create user document", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        sendUserToNextActivity();

                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(sing_up.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(sing_up.this,draw.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}