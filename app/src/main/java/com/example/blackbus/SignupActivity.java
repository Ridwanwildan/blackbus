package com.example.blackbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText etFullName, etSignupUsername, etDateOfBirth, etEmail, etPhoneNumber, etSignupPassword;
    private Button btnCreateAccount;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        myDb = new DatabaseHelper(this);

        // Menghubungkan variabel dengan view pada layout
        etFullName = findViewById(R.id.etFullName);
        etSignupUsername = findViewById(R.id.etSignupUsername);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etSignupPassword = findViewById(R.id.etSignupPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        // Fungsi untuk button Create Account
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etFullName.getText().toString();
                String username = etSignupUsername.getText().toString();
                String dateOfBirth = etDateOfBirth.getText().toString();
                String email = etEmail.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();
                String password = etSignupPassword.getText().toString();

                if (fullName.isEmpty() || username.isEmpty() || dateOfBirth.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = myDb.insertData(fullName, username, dateOfBirth, email, phoneNumber, password);
                    if (isInserted) {
                        Toast.makeText(SignupActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Kembali ke layar login
                    } else {
                        Toast.makeText(SignupActivity.this, "Error occurred while creating account", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
