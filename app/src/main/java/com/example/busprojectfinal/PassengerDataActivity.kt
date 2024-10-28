package com.example.busprojectfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PassengerDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_passenger_data)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the seat number from the Intent
        val selectedSeatNumber = intent.getStringExtra("selectedSeatNumber")

        // Find and set the TextView for the seat number
        val seatNumberTextView = findViewById<TextView>(R.id.seat_number)
        seatNumberTextView.text = selectedSeatNumber

        findViewById<Button>(R.id.continue_button).setOnClickListener {
            // Create an Intent to start PassengerDetailsActivity
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)

        }

        // Cancel button action
        findViewById<Button>(R.id.back_button).setOnClickListener {
            finish()  // Close the activity
            // Create an Intent to start PassengerDetailsActivity
            val intent = Intent(this,SeatSelectionActivity::class.java)
            startActivity(intent)
        }
    }
}