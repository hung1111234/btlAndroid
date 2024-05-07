package com.example.hung075.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.hung075.Domains.User;
import com.example.hung075.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextName, editTextEmail, editTextPassword, editTextUsername;
    TextView login;
    Button SignUpBtn;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()){
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("users");
                    String name = editTextName.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String username = editTextUsername.getText().toString();
                    String password = editTextPassword.getText().toString();
                    User user = new User(name, email, username, password);

                    reference.child(name).setValue(user);

                    Toast.makeText(RegisterActivity.this, "You have signup successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    public void init(){
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUsername = findViewById(R.id.editTextUsername);
        SignUpBtn = findViewById(R.id.SignUpBtn);
        login = findViewById(R.id.login);
    }

    public  Boolean validateData(){
        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        if(email.isEmpty()){
            editTextEmail.setError("Email cannot be empty");
            return false;
        }
        if(!email.contains("@gmail.com") && email.length() < 13){
            editTextEmail.setError("Email has an incorrect format.");
            return false;
        }

        if(name.length() < 6){
            editTextName.setError("The length of the name must be at least 6 characters");
            return false;
        }

        if(username.contains(" ")){
            editTextUsername.setError("Username must not contain whitespace.");
            return false;
        }

        if(username.length() < 4) {
            editTextUsername.setError("The length of the username must be at least 4 characters");
            return false;
        }

        if(password.length() < 6){
            editTextPassword.setError("The length of the password must be at least 6 characters");
            return false;
        }
        return true;
    }
}