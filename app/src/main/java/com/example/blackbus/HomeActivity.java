package com.example.blackbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.database.Cursor;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private EditText etOriginCity, etDestinationCity, etTravelDate;
    private Button btnSubmit, btnAdminPanel, btnLogout;
    private DatabaseHelper myDb;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Menghubungkan variabel dengan view pada layout
        tvWelcome = findViewById(R.id.tvWelcome);
        etOriginCity = findViewById(R.id.etOriginCity);
        etDestinationCity = findViewById(R.id.etDestinationCity);
        etTravelDate = findViewById(R.id.etTravelDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnAdminPanel = findViewById(R.id.btnAdminPanel);
        btnLogout = findViewById(R.id.btnLogout);

        myDb = new DatabaseHelper(this);

        // Mengambil username dari Intent
        username = getIntent().getStringExtra("USERNAME");

        // Menampilkan ucapan selamat datang
        tvWelcome.setText("Welcome, " + username + "!");

        // Mengecek role user dari database
        checkUserRole();

        // Fungsi untuk tombol Submit pencarian
        btnSubmit.setOnClickListener(v -> {
            String originCity = etOriginCity.getText().toString();
            String destinationCity = etDestinationCity.getText().toString();
            String travelDate = etTravelDate.getText().toString();

            if (originCity.isEmpty() || destinationCity.isEmpty() || travelDate.isEmpty()) {
                Toast.makeText(HomeActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                intent.putExtra("DEPARTURE_DATE", travelDate);
                startActivity(intent);
            }
        });

        // Fungsi untuk tombol Admin Panel
        btnAdminPanel.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AdminHomeActivity.class);
            startActivity(intent);
        });

        // Fungsi untuk tombol Logout
        btnLogout.setOnClickListener(v -> {
            // Mengarahkan pengguna kembali ke halaman login
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    // Fungsi untuk mengecek role user
    private void checkUserRole() {
        Cursor res = myDb.getUser(username); // Tidak perlu lagi menggunakan password
        if (res != null && res.getCount() > 0) {
            res.moveToFirst();
            String role = res.getString(res.getColumnIndex("ROLE"));
            if ("Admin".equalsIgnoreCase(role)) {
                btnAdminPanel.setVisibility(View.VISIBLE); // Tampilkan tombol jika role adalah Admin
            } else {
                btnAdminPanel.setVisibility(View.GONE); // Sembunyikan tombol jika bukan Admin
            }
        } else {
            // Jika user tidak ditemukan, berikan pesan error
            Toast.makeText(this, "User not found in database", Toast.LENGTH_SHORT).show();
        }
    }

}
