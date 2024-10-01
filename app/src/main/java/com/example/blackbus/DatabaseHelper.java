package com.example.blackbus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "blackbus.db";

    // Tabel Users
    public static final String TABLE_NAME_USERS = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FULLNAME";
    public static final String COL_3 = "USERNAME";
    public static final String COL_4 = "DATE_OF_BIRTH";
    public static final String COL_5 = "EMAIL";
    public static final String COL_6 = "PHONE_NUMBER";
    public static final String COL_7 = "PASSWORD";
    public static final String COL_8 = "ROLE";

    // Tabel Buses
    public static final String TABLE_NAME_BUSES = "buses";
    public static final String BUS_COL_1 = "BUS_ID";
    public static final String BUS_COL_2 = "BUS_NAME";
    public static final String BUS_COL_3 = "BUS_CLASS";
    public static final String BUS_COL_4 = "DEPARTURE_DATE";
    public static final String BUS_COL_5 = "DEPARTURE_TIME";
    public static final String BUS_COL_6 = "ARRIVAL_TIME";
    public static final String BUS_COL_7 = "PRICE";
    public static final String BUS_COL_8 = "TOTAL_SEATS";
    public static final String BUS_COL_9 = "FACILITIES";
    public static final String BUS_COL_10 = "ABOUT_BUS";
    public static final String BUS_COL_11 = "TERMS_AND_CONDITIONS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public Cursor getAllBookings() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM bookings";
        return db.rawQuery(query, null);
    }

    public Cursor validateUser(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_USERS + " WHERE USERNAME = ? AND PASSWORD = ?";
        return db.rawQuery(query, new String[]{username, password});
    }


    public Cursor getAllUsersWithRole(String role) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE ROLE = ?";
        return db.rawQuery(query, new String[]{role});
    }

    public boolean insertBookingData(String busName, String seatNumber) {
        if (busName == null || busName.isEmpty()) {
            throw new IllegalArgumentException("Bus name cannot be null or empty");
        }
        if (seatNumber == null || seatNumber.isEmpty()) {
            throw new IllegalArgumentException("Seat number cannot be null or empty");
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BUS_NAME", busName);
        contentValues.put("SEAT_NUMBER", seatNumber);
        contentValues.put("BOOKING_DATE", getCurrentDate()); // Optional: simpan tanggal pemesanan

        long result = db.insert("bookings", null, contentValues);
        return result != -1; // Jika `result` -1 berarti gagal
    }


    // Fungsi untuk mendapatkan tanggal saat ini
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    public Cursor getBusDetails(String busName) {
        if (busName == null || busName.isEmpty()) {
            throw new IllegalArgumentException("Bus name cannot be null or empty");
        }

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_BUSES + " WHERE BUS_NAME = ?";
        return db.rawQuery(query, new String[]{busName});
    }


    public Cursor getAvailableSeats(String busName) {
        if (busName == null || busName.isEmpty()) {
            throw new IllegalArgumentException("Bus name cannot be null or empty");
        }

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SEAT_NUMBER FROM seats WHERE BUS_NAME = ? AND IS_BOOKED = 0"; // IS_BOOKED = 0 artinya kursi tersedia
        return db.rawQuery(query, new String[]{busName});
    }


    public Cursor searchBusData(String departureDate) {
        if (departureDate == null || departureDate.isEmpty()) {
            throw new IllegalArgumentException("Departure date cannot be null or empty");
        }

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_BUSES + " WHERE DEPARTURE_DATE = ?";
        return db.rawQuery(query, new String[]{departureDate});
    }








    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel users
        db.execSQL("CREATE TABLE " + TABLE_NAME_USERS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, FULLNAME TEXT, USERNAME TEXT, DATE_OF_BIRTH TEXT, EMAIL TEXT, PHONE_NUMBER TEXT, PASSWORD TEXT, ROLE TEXT DEFAULT 'user')");

        // Membuat tabel buses
        db.execSQL("CREATE TABLE " + TABLE_NAME_BUSES + " (BUS_ID INTEGER PRIMARY KEY AUTOINCREMENT, BUS_NAME TEXT, BUS_CLASS TEXT, DEPARTURE_DATE TEXT, DEPARTURE_TIME TEXT, ARRIVAL_TIME TEXT, PRICE INTEGER, TOTAL_SEATS INTEGER, FACILITIES TEXT, ABOUT_BUS TEXT, TERMS_AND_CONDITIONS TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_BUSES);
        onCreate(db);
    }

    // Fungsi untuk menyimpan data user (tidak diubah)
    public boolean insertData(String fullName, String username, String dateOfBirth, String email, String phoneNumber, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, fullName);
        contentValues.put(COL_3, username);
        contentValues.put(COL_4, dateOfBirth);
        contentValues.put(COL_5, email);
        contentValues.put(COL_6, phoneNumber);
        contentValues.put(COL_7, password);
        contentValues.put(COL_8, "user");
        long result = db.insert(TABLE_NAME_USERS, null, contentValues);
        return result != -1; // return true jika data berhasil disimpan
    }

    // Fungsi untuk mendapatkan data user
    public Cursor getUser(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_USERS + " WHERE USERNAME = ?";
        return db.rawQuery(query, new String[]{username});
    }


    // Fungsi untuk menyimpan data bus
    public boolean insertBusData(String busName, String busClass, String departureDate, String departureTime, String arrivalTime, int price, int totalSeats, String facilities, String aboutBus, String termsAndConditions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BUS_COL_2, busName);
        contentValues.put(BUS_COL_3, busClass);
        contentValues.put(BUS_COL_4, departureDate);
        contentValues.put(BUS_COL_5, departureTime);
        contentValues.put(BUS_COL_6, arrivalTime);
        contentValues.put(BUS_COL_7, price);
        contentValues.put(BUS_COL_8, totalSeats);
        contentValues.put(BUS_COL_9, facilities);
        contentValues.put(BUS_COL_10, aboutBus);
        contentValues.put(BUS_COL_11, termsAndConditions);
        long result = db.insert(TABLE_NAME_BUSES, null, contentValues);
        return result != -1; // return true jika data berhasil disimpan
    }
}
