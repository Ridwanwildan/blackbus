package com.example.blackbus;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ViewUsersActivity extends AppCompatActivity {

    private LinearLayout layoutUsers;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        // Menghubungkan variabel dengan view pada layout
        layoutUsers = findViewById(R.id.layoutUsers);

        myDb = new DatabaseHelper(this);

        // Menampilkan semua pengguna dengan role "user"
        displayUsers();
    }

    // Fungsi untuk menampilkan semua pengguna dengan role "user"
    private void displayUsers() {
        Cursor res = myDb.getAllUsersWithRole("user");

        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                String fullName = res.getString(res.getColumnIndex("FULLNAME"));
                String username = res.getString(res.getColumnIndex("USERNAME"));
                String dateOfBirth = res.getString(res.getColumnIndex("DATE_OF_BIRTH"));
                String email = res.getString(res.getColumnIndex("EMAIL"));
                String phoneNumber = res.getString(res.getColumnIndex("PHONE_NUMBER"));

                // Membuat tampilan dinamis untuk setiap pengguna
                View userView = getLayoutInflater().inflate(R.layout.item_user, null);

                TextView tvFullName = userView.findViewById(R.id.tvFullName);
                TextView tvUsername = userView.findViewById(R.id.tvUsername);
                TextView tvDateOfBirth = userView.findViewById(R.id.tvDateOfBirth);
                TextView tvEmail = userView.findViewById(R.id.tvEmail);
                TextView tvPhoneNumber = userView.findViewById(R.id.tvPhoneNumber);

                // Mengisi data ke dalam tampilan
                tvFullName.setText("Full Name: " + fullName);
                tvUsername.setText("Username: " + username);
                tvDateOfBirth.setText("Date of Birth: " + dateOfBirth);
                tvEmail.setText("Email: " + email);
                tvPhoneNumber.setText("Phone Number: " + phoneNumber);

                // Menambahkan tampilan ke dalam layout
                layoutUsers.addView(userView);
            }
        } else {
            TextView tvNoUsers = new TextView(this);
            tvNoUsers.setText("No users found.");
            layoutUsers.addView(tvNoUsers);
        }
    }
}
