package com.example.blackbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class InputBusDataActivity extends AppCompatActivity {

    private ImageView ivBusLogo;
    private Button btnUploadLogo, btnSubmitBusData;
    private EditText etBusName, etBusClass, etDepartureDate, etDepartureTime, etArrivalTime, etPrice, etTotalSeats, etFacilities, etAboutBus, etTermsAndConditions;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_bus_data);

        // Menghubungkan variabel dengan view pada layout
        ivBusLogo = findViewById(R.id.ivBusLogo);
        btnUploadLogo = findViewById(R.id.btnUploadLogo);
        btnSubmitBusData = findViewById(R.id.btnSubmitBusData);
        etBusName = findViewById(R.id.etBusName);
        etBusClass = findViewById(R.id.etBusClass);
        etDepartureDate = findViewById(R.id.etDepartureDate);
        etDepartureTime = findViewById(R.id.etDepartureTime);
        etArrivalTime = findViewById(R.id.etArrivalTime);
        etPrice = findViewById(R.id.etPrice);
        etTotalSeats = findViewById(R.id.etTotalSeats);
        etFacilities = findViewById(R.id.etFacilities);
        etAboutBus = findViewById(R.id.etAboutBus);
        etTermsAndConditions = findViewById(R.id.etTermsAndConditions);

        // Inisialisasi DatabaseHelper
        myDb = new DatabaseHelper(this);

        // Fungsi untuk tombol Upload Logo (untuk saat ini hanya menampilkan Toast)
        btnUploadLogo.setOnClickListener(v -> {
            // Implementasi upload logo akan dikembangkan lebih lanjut
            Toast.makeText(InputBusDataActivity.this, "Upload Logo Clicked", Toast.LENGTH_SHORT).show();
        });

        // Fungsi untuk tombol Submit Bus Data
        btnSubmitBusData.setOnClickListener(v -> {
            String busName = etBusName.getText().toString();
            String busClass = etBusClass.getText().toString();
            String departureDate = etDepartureDate.getText().toString();
            String departureTime = etDepartureTime.getText().toString();
            String arrivalTime = etArrivalTime.getText().toString();
            String priceStr = etPrice.getText().toString();
            String totalSeatsStr = etTotalSeats.getText().toString();
            String facilities = etFacilities.getText().toString();
            String aboutBus = etAboutBus.getText().toString();
            String termsAndConditions = etTermsAndConditions.getText().toString();

            if (busName.isEmpty() || busClass.isEmpty() || departureDate.isEmpty() || departureTime.isEmpty() || arrivalTime.isEmpty() ||
                    priceStr.isEmpty() || totalSeatsStr.isEmpty() || facilities.isEmpty() || aboutBus.isEmpty() || termsAndConditions.isEmpty()) {
                Toast.makeText(InputBusDataActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                int price = Integer.parseInt(priceStr);
                int totalSeats = Integer.parseInt(totalSeatsStr);

                boolean isInserted = myDb.insertBusData(busName, busClass, departureDate, departureTime, arrivalTime, price, totalSeats, facilities, aboutBus, termsAndConditions);
                if (isInserted) {
                    Toast.makeText(InputBusDataActivity.this, "Bus data submitted successfully", Toast.LENGTH_SHORT).show();
                    finish(); // Kembali ke halaman sebelumnya
                } else {
                    Toast.makeText(InputBusDataActivity.this, "Error occurred while submitting bus data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
