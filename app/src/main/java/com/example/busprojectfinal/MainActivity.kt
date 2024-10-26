package com.example.busprojectfinal

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var busRecyclerView: RecyclerView
    private lateinit var busAdapter: BusAdapter
    private val busList = mutableListOf<Bus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val fromLocationInput = findViewById<EditText>(R.id.fromCity)
        val toLocationInput = findViewById<EditText>(R.id.toCity)
        val travelDateInput = findViewById<EditText>(R.id.travelDateInput)
        val bookButton = findViewById<Button>(R.id.bookButton)
        // Cities List
        val cities = listOf("Nairobi", "Kisumu", "Mombasa", "Eldoret", "Nakuru")

        // AutoCompleteTextView for "From" City
        val fromCityAutoComplete = findViewById<AutoCompleteTextView>(R.id.fromCity)
        val fromAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cities)
        fromCityAutoComplete.setAdapter(fromAdapter)

        // AutoCompleteTextView for "To" City
        val toCityAutoComplete = findViewById<AutoCompleteTextView>(R.id.toCity)
        val toAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cities)
        toCityAutoComplete.setAdapter(toAdapter)

        val fromLocation = intent.getStringExtra("FROM_LOCATION")
        val toLocation = intent.getStringExtra("TO_LOCATION")
        val travelDate = intent.getStringExtra("TRAVEL_DATE")

        val resultText = findViewById<TextView>(R.id.resultText)
        resultText.text = "Available buses from $fromLocation to $toLocation on $travelDate"

        // Set up Date Picker for travel date input
        travelDateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val date = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    travelDateInput.setText(date)
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        // Set up Book Button functionality
        bookButton.setOnClickListener {
            val fromLocation = fromLocationInput.text.toString()
            val toLocation = toLocationInput.text.toString()
            val travelDate = travelDateInput.text.toString()

            if (fromLocation.isEmpty() || toLocation.isEmpty() || travelDate.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
            busRecyclerView = findViewById(R.id.busRecyclerView)
            busRecyclerView.layoutManager = LinearLayoutManager(this)

            database = FirebaseDatabase.getInstance().getReference("buses")

            // Fetch bus data from Firebase
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    busList.clear()
                    for (busSnapshot in snapshot.children) {
                        val bus = busSnapshot.getValue(Bus::class.java)
                        bus?.let { busList.add(it) }
                    }
                    busAdapter = BusAdapter(this@MainActivity,busList) { bus ->
                        // Handle seat booking logic
                        val intent = Intent(this@MainActivity, SeatSelectionActivity::class.java)
                        // Pass the busId (assuming bus has an id property)
                        intent.putExtra("busType", bus.busType)

                        // Start the SeatSelectionActivity
                        startActivity(intent)

                    }
                    busRecyclerView.adapter = busAdapter
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })

        }



    }
}