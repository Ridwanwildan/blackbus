package com.example.blackbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnSignup;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        // Menghubungkan variabel dengan view pada layout
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        // Fungsi untuk button Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Menggunakan validateUser untuk memverifikasi username dan password
                    Cursor res = myDb.validateUser(username, password);
                    if (res != null && res.getCount() > 0) {
                        res.moveToFirst();
                        String role = res.getString(res.getColumnIndex("ROLE"));
                        Intent intent;
                        if ("Admin".equalsIgnoreCase(role)) {
                            // Jika role adalah Admin
                            intent = new Intent(MainActivity.this, AdminHomeActivity.class);
                        } else {
                            // Jika role adalah User
                            intent = new Intent(MainActivity.this, HomeActivity.class);
                        }
                        intent.putExtra("USERNAME", username);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Fungsi untuk button Sign Up
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuka halaman registrasi (signup)
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
