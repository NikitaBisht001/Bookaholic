package com.internshla.bookaholicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    FirebaseAuth  mAuth;
    FirebaseUser mUser;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    EditText inputEmail, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        ImageView button1=findViewById(R.id.image);
        inputEmail = findViewById(R.id.editTextTextEmailAddress);
        inputPassword = findViewById(R.id.editTextpassword);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        Button btnLogin = findViewById(R.id.button);
        progressDialog = new ProgressDialog(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        button1.setOnClickListener(v -> {
            Intent intent=new Intent(login.this,MainActivity.class);
            startActivity(intent);
        });
        Button signupButton;
        signupButton = findViewById(R.id.signup);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(login.this,sing_up.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(v -> perforloing());


    }

    private void perforloing() {


            String email= inputEmail.getText().toString();
            String password= inputPassword.getText().toString();
            if(!email.matches(emailPattern)){
                inputEmail.setError("ENTER CORRECT EMAIL");
            }
            else if(password.isEmpty()||password.length()<6){
                inputPassword.setError("ENTER VALID PASSWORD");
            }
            else {
                progressDialog.setMessage("login.....");
                progressDialog.setTitle("login");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(login.this,"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(login.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                    }

                });

            }
    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(login.this,draw.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}