package com.example.busprojectfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SeatSelectionActivity : AppCompatActivity() {

    // Define seat arrays
    private val availableSeats = mutableListOf(
        "seat_1A", "seat_1B", "seat_1C", "seat_1D",
        "seat_2A", "seat_2B", "seat_2C", "seat_2D",
        "seat_3A", "seat_3B", "seat_3C", "seat_3D",
        "seat_4A", "seat_4B", "seat_4C", "seat_4D",
        "seat_5A", "seat_5B", "seat_5C", "seat_5D",
        "seat_6A", "seat_6B", "seat_6C", "seat_6D",
        "seat_7A", "seat_7B", "seat_7C", "seat_7D",
        "seat_8A", "seat_8B", "seat_8C", "seat_8D",
        "seat_9A", "seat_9B", "seat_9C", "seat_9D"
    )
    private val bookedSeats = mutableListOf<String>()
    private val selectedSeats = mutableListOf<String>()  // New list for selected seats

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_selection)

        // Enable edge-to-edge mode
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // List of seat IDs for click listener setup
        val seatIds = listOf(
            "seat_1A", "seat_1B", "seat_1C", "seat_1D",
            "seat_2A", "seat_2B", "seat_2C", "seat_2D",
            "seat_3A", "seat_3B", "seat_3C", "seat_3D",
            "seat_4A", "seat_4B", "seat_4C", "seat_4D",
            "seat_5A", "seat_5B", "seat_5C", "seat_5D",
            "seat_6A", "seat_6B", "seat_6C", "seat_6D",
            "seat_7A", "seat_7B", "seat_7C", "seat_7D",
            "seat_8A", "seat_8B", "seat_8C", "seat_8D",
            "seat_9A", "seat_9B", "seat_9C", "seat_9D"
        )

        // Set click listeners on each seat view
        seatIds.forEach { seatId ->
            val seatView = findViewById<ImageView>(resources.getIdentifier(seatId, "id", packageName))
            seatView?.setOnClickListener {
                // Call selectSeat with the corresponding seat ID
                toggleSeatSelection(seatId)
            }
        }

        // Continue button logic
        findViewById<Button>(R.id.continueButton).setOnClickListener {
            // Move selected seats to booked seats
            bookedSeats.addAll(selectedSeats)
            availableSeats.removeAll(selectedSeats)
            selectedSeats.clear()

            // Update UI to show all booked seats
            bookedSeats.forEach { seatId ->
                val seatView = findViewById<ImageView>(resources.getIdentifier(seatId, "id", packageName))
                seatView?.setBackgroundResource(R.drawable.booked_img)
            }

            Toast.makeText(this, "Seats booked successfully!", Toast.LENGTH_SHORT).show()
            // Check if there’s at least one selected seat
            if (bookedSeats.isNotEmpty()) {
                // Get the first selected seat (or customize as per requirements)
                val selectedSeat = bookedSeats[0]

                // Create an Intent to start PassengerDetailsActivity
                val intent = Intent(this, PassengerDataActivity::class.java)
                intent.putExtra("selectedSeatNumber", selectedSeat)  // Pass selected seat number
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please select a seat before continuing.", Toast.LENGTH_SHORT).show()
            }
        }

        // Cancel button action
        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            finish()  // Close the activity
            // Create an Intent to start PassengerDetailsActivity
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to toggle seat selection
    private fun toggleSeatSelection(seatId: String) {
        val seatView = findViewById<ImageView>(resources.getIdentifier(seatId, "id", packageName))

        if (selectedSeats.contains(seatId)) {
            // Deselect seat
            selectedSeats.remove(seatId)
            seatView?.setBackgroundResource(R.drawable.available_img)
        } else if (availableSeats.contains(seatId)) {
            // Select seat
            selectedSeats.add(seatId)
            seatView?.setBackgroundResource(R.drawable.your_seat_img)
        } else {
            // Show a message if the seat is already booked
            Toast.makeText(this, "Seat $seatId is already booked.", Toast.LENGTH_SHORT).show()
        }
    }
}
