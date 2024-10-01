package com.example.blackbus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.blackbus.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private LinearLayout layoutSearchResults;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Menghubungkan variabel dengan view pada layout
        layoutSearchResults = findViewById(R.id.layoutSearchResults);

        myDb = new DatabaseHelper(this);

        // Mengambil parameter pencarian dari Intent
        String departureDate = getIntent().getStringExtra("DEPARTURE_DATE");

        if (departureDate != null) {
            // Mencari data bus berdasarkan tanggal keberangkatan
            searchBuses(departureDate);
        }
    }

    // Fungsi untuk mencari data bus dan menampilkannya
    private void searchBuses(String departureDate) {
        Cursor res = myDb.searchBusData(departureDate);

        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                String busName = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_2));
                String busClass = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_3));
                String departureTime = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_5));
                String arrivalTime = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_6));
                int price = res.getInt(res.getColumnIndex(DatabaseHelper.BUS_COL_7));
                int totalSeats = res.getInt(res.getColumnIndex(DatabaseHelper.BUS_COL_8));
                String facilities = res.getString(res.getColumnIndex(DatabaseHelper.BUS_COL_9));

                // Menghitung durasi perjalanan
                String duration = calculateDuration(departureTime, arrivalTime);

                // Membuat tampilan dinamis untuk setiap hasil pencarian
                View busView = getLayoutInflater().inflate(R.layout.item_bus, null);

                TextView tvBusName = busView.findViewById(R.id.tvBusName);
                TextView tvBusPrice = busView.findViewById(R.id.tvBusPrice);
                TextView tvBusClass = busView.findViewById(R.id.tvBusClass);
                TextView tvTotalSeats = busView.findViewById(R.id.tvTotalSeats);
                TextView tvDepartureTime = busView.findViewById(R.id.tvDepartureTime);
                TextView tvArrivalTime = busView.findViewById(R.id.tvArrivalTime);
                TextView tvDuration = busView.findViewById(R.id.tvDuration);
                TextView tvFacilities = busView.findViewById(R.id.tvFacilities);
                Button btnBookNow = busView.findViewById(R.id.btnBookNow);

                // Mengisi data ke dalam tampilan
                tvBusName.setText("Bus Name: " + busName);
                tvBusPrice.setText("Price: " + price);
                tvBusClass.setText("Class: " + busClass);
                tvTotalSeats.setText("Total Seats: " + totalSeats);
                tvDepartureTime.setText("Departure: " + departureTime);
                tvArrivalTime.setText("Arrival: " + arrivalTime);
                tvDuration.setText("Duration: " + duration);
                tvFacilities.setText("Facilities: " + facilities);

                // Fungsi tombol Pesan Sekarang
                btnBookNow.setOnClickListener(v -> {
                    Intent intent = new Intent(SearchActivity.this, BookingActivity.class);
                    intent.putExtra("BUS_NAME", busName);
                    intent.putExtra("PRICE", price);
                    intent.putExtra("DEPARTURE_TIME", departureTime);
                    intent.putExtra("ARRIVAL_TIME", arrivalTime);
                    startActivity(intent);
                });

                // Menambahkan tampilan ke dalam layout
                layoutSearchResults.addView(busView);
            }
        } else {
            TextView tvNoResults = new TextView(this);
            tvNoResults.setText("No buses found for the selected date.");
            layoutSearchResults.addView(tvNoResults);
        }
    }

    // Fungsi untuk menghitung durasi perjalanan
    private String calculateDuration(String departureTime, String arrivalTime) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date departure = format.parse(departureTime);
            Date arrival = format.parse(arrivalTime);
            if (departure != null && arrival != null) {
                long durationMillis = arrival.getTime() - departure.getTime();
                long hours = durationMillis / (1000 * 60 * 60);
                long minutes = (durationMillis % (1000 * 60 * 60)) / (1000 * 60);
                return hours + "h " + minutes + "m";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
}
