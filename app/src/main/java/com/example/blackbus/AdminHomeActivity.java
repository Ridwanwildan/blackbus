package com.example.blackbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomeActivity extends AppCompatActivity {

    private Button btnInputBusData, btnViewOrders, btnViewUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Menghubungkan variabel dengan view pada layout
        btnInputBusData = findViewById(R.id.btnInputBusData);
        btnViewOrders = findViewById(R.id.btnViewOrders);
        btnViewUsers = findViewById(R.id.btnViewUsers);

        // Fungsi untuk tombol Input Data Bus
        btnInputBusData.setOnClickListener(v -> {
            // Buka halaman untuk input data bus (akan diimplementasikan)
            Intent intent = new Intent(AdminHomeActivity.this, InputBusDataActivity.class);
            startActivity(intent);
        });

        // Fungsi untuk tombol Lihat Pesanan
        btnViewOrders.setOnClickListener(v -> {
            // Buka halaman untuk melihat pesanan (akan diimplementasikan)
            Intent intent = new Intent(AdminHomeActivity.this, ViewOrdersActivity.class);
            startActivity(intent);
        });

        // Fungsi untuk tombol Lihat Data User
        btnViewUsers.setOnClickListener(v -> {
            // Buka halaman untuk melihat data user yang memiliki role "user"
            Intent intent = new Intent(AdminHomeActivity.this, ViewUsersActivity.class);
            startActivity(intent);
        });
    }
}
