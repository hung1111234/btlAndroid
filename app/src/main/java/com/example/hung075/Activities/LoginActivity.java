package com.example.hung075.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung075.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    private Button loginBtn;
    TextView SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        init();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.loginBtn);
        SignUp = (TextView) findViewById(R.id.Register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateData()){

                }
                else checkUser();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }


    public void init(){
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.loginBtn);
        SignUp = findViewById(R.id.Register);
    }
    public  Boolean validateData(){
        String username = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(username.contains(" ")){
            editTextEmail.setError("Username must not contain whitespace.");
            return false;
        }

        if(username.length() < 4) {
            editTextEmail.setError("The length of the username must be at least 4 characters");
            return false;
        }

        if(password.length() < 6){
            editTextPassword.setError("The length of the password must be at least 6 characters");
            return false;
        }
        return true;
    }

    public void checkUser(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("email").equalTo(email);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    editTextEmail.setError(null);
                    String passfromDB = snapshot.child(email).child("password").getValue(String.class);
                    if(!Objects.equals(passfromDB, password)){
                        editTextEmail.setError(null);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        editTextPassword.setError("Invalid Credentials");
                        editTextPassword.requestFocus();
                    }
                }
                else{
                    editTextEmail.setError("User does not exist");
                    editTextPassword.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}