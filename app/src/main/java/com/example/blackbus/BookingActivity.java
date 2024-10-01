package com.example.blackbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class BookingActivity extends AppCompatActivity {

    private ImageView ivBusLogo;
    private TextView tvBusName, tvBusClass, tvDepartureDate, tvDepartureTime, tvArrivalTime, tvPrice, tvTotalSeats, tvFacilities, tvAboutBus, tvTermsAndConditions;
    private Spinner spSeatSelection;
    private Button btnBookTicket;
    private DatabaseHelper myDb;

    private String busName, departureTime, arrivalTime;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Menghubungkan variabel dengan view pada layout
        ivBusLogo = findViewById(R.id.ivBusLogo);
        tvBusName = findViewById(R.id.tvBusName);
        tvBusClass = findViewById(R.id.tvBusClass);
        tvDepartureDate = findViewById(R.id.tvDepartureDate);
        tvDepartureTime = findViewById(R.id.tvDepartureTime);
        tvArrivalTime = findViewById(R.id.tvArrivalTime);
        tvPrice = findViewById(R.id.tvPrice);
        tvTotalSeats = findViewById(R.id.tvTotalSeats);
        tvFacilities = findViewById(R.id.tvFacilities);
        tvAboutBus = findViewById(R.id.tvAboutBus);
        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);
        spSeatSelection = findViewById(R.id.spSeatSelection);
        btnBookTicket = findViewById(R.id.btnBookTicket);

        myDb = new DatabaseHelper(this);

        // Mengambil detail bus dari Intent
        busName = getIntent().getStringExtra("BUS_NAME");
        price = getIntent().getIntExtra("PRICE", 0);
        departureTime = getIntent().getStringExtra("DEPARTURE_TIME");
        arrivalTime = getIntent().getStringExtra("ARRIVAL_TIME");

        // Menampilkan detail bus secara lengkap
        displayBusDetails(busName);

        // Tombol "Pesan Tiket"
        btnBookTicket.setOnClickListener(v -> {
            String selectedSeat = spSeatSelection.getSelectedItem().toString();
            if (!selectedSeat.isEmpty()) {
                boolean isInserted = myDb.insertBookingData(busName, selectedSeat);
                if (isInserted) {
                    Toast.makeText(BookingActivity.this, "Berhasil memesan tiket", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(BookingActivity.this, "Gagal memesan tiket", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Fungsi untuk menampilkan detail bus
    private void displayBusDetails(String busName) {
        Cursor res = myDb.getBusDetails(busName);

        if (res != null && res.moveToFirst()) {
            String busClass = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_3));
            String departureDate = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_4));
            String totalSeats = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_8));
            String facilities = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_9));
            String aboutBus = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_10));
            String termsAndConditions = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_11));

            // Menampilkan data ke UI
            tvBusName.setText("Bus Name: " + busName);
            tvBusClass.setText("Class: " + busClass);
            tvDepartureDate.setText("Departure Date: " + departureDate);
            tvDepartureTime.setText("Departure Time: " + departureTime);
            tvArrivalTime.setText("Arrival Time: " + arrivalTime);
            tvPrice.setText("Price: " + price);
            tvTotalSeats.setText("Total Seats: " + totalSeats);
            tvFacilities.setText("Facilities: " + facilities);
            tvAboutBus.setText("About Bus: " + aboutBus);
            tvTermsAndConditions.setText("Terms and Conditions: " + termsAndConditions);

            // Menyiapkan dropdown untuk memilih kursi yang masih tersedia
            prepareSeatDropdown(busName);
        }
    }

    // Fungsi untuk menyiapkan dropdown kursi yang masih tersedia
    private void prepareSeatDropdown(String busName) {
        Cursor res = myDb.getAvailableSeats(busName);
        ArrayList<String> availableSeats = new ArrayList<>();

        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                availableSeats.add(res.getString(0)); // Mengambil nomor kursi yang masih tersedia
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availableSeats);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSeatSelection.setAdapter(adapter);
    }
}
