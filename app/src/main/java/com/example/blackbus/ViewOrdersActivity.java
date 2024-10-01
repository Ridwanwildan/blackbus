package com.example.blackbus;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.view.View;


public class ViewOrdersActivity extends AppCompatActivity {

    private LinearLayout layoutOrders;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        // Menghubungkan variabel dengan view pada layout
        layoutOrders = findViewById(R.id.layoutOrders);

        myDb = new DatabaseHelper(this);

        // Menampilkan semua pesanan
        displayOrders();
    }

    // Fungsi untuk menampilkan semua pesanan yang telah dilakukan oleh pengguna
    private void displayOrders() {
        Cursor res = myDb.getAllBookings();

        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                String fullName = res.getString(res.getColumnIndex("FULLNAME"));
                String email = res.getString(res.getColumnIndex("EMAIL"));
                String phoneNumber = res.getString(res.getColumnIndex("PHONE_NUMBER"));
                String busName = res.getString(res.getColumnIndex("BUS_NAME"));
                String originCity = res.getString(res.getColumnIndex("ORIGIN_CITY"));
                String destinationCity = res.getString(res.getColumnIndex("DESTINATION_CITY"));
                String departureDate = res.getString(res.getColumnIndex("DEPARTURE_DATE"));
                String departureTime = res.getString(res.getColumnIndex("DEPARTURE_TIME"));
                String arrivalTime = res.getString(res.getColumnIndex("ARRIVAL_TIME"));
                int price = res.getInt(res.getColumnIndex("PRICE"));
                String seatNumber = res.getString(res.getColumnIndex("SEAT_NUMBER"));

                // Membuat tampilan dinamis untuk setiap pesanan
                View orderView = getLayoutInflater().inflate(R.layout.item_order, null);

                TextView tvFullName = orderView.findViewById(R.id.tvFullName);
                TextView tvEmail = orderView.findViewById(R.id.tvEmail);
                TextView tvPhoneNumber = orderView.findViewById(R.id.tvPhoneNumber);
                TextView tvBusName = orderView.findViewById(R.id.tvBusName);
                TextView tvOriginCity = orderView.findViewById(R.id.tvOriginCity);
                TextView tvDestinationCity = orderView.findViewById(R.id.tvDestinationCity);
                TextView tvDepartureDate = orderView.findViewById(R.id.tvDepartureDate);
                TextView tvDepartureTime = orderView.findViewById(R.id.tvDepartureTime);
                TextView tvArrivalTime = orderView.findViewById(R.id.tvArrivalTime);
                TextView tvPrice = orderView.findViewById(R.id.tvPrice);
                TextView tvSeatNumber = orderView.findViewById(R.id.tvSeatNumber);

                // Mengisi data ke dalam tampilan
                tvFullName.setText("Full Name: " + fullName);
                tvEmail.setText("Email: " + email);
                tvPhoneNumber.setText("Phone Number: " + phoneNumber);
                tvBusName.setText("Bus Name: " + busName);
                tvOriginCity.setText("Origin City: " + originCity);
                tvDestinationCity.setText("Destination City: " + destinationCity);
                tvDepartureDate.setText("Departure Date: " + departureDate);
                tvDepartureTime.setText("Departure Time: " + departureTime);
                tvArrivalTime.setText("Arrival Time: " + arrivalTime);
                tvPrice.setText("Price: " + price);
                tvSeatNumber.setText("Seat Number: " + seatNumber);

                // Menambahkan tampilan ke dalam layout
                layoutOrders.addView(orderView);
            }
        } else {
            TextView tvNoOrders = new TextView(this);
            tvNoOrders.setText("No orders found.");
            layoutOrders.addView(tvNoOrders);
        }
    }
}
