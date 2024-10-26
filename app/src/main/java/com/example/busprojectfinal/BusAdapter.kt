package com.example.busprojectfinal

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.busprojectfinal.R
import com.example.busprojectfinal.Bus

class BusAdapter(
    private val context: Context,
    private val busList: List<Bus>,
    private val onBookClick: (Bus) -> Unit) :
    RecyclerView.Adapter<BusAdapter.BusViewHolder>() {

    class BusViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val busTypeText: TextView = view.findViewById(R.id.busTypeText)
        val routeText: TextView = view.findViewById(R.id.routeText)
        val departureTimeText: TextView = view.findViewById(R.id.departureTimeText)
        val seatsAvailableText: TextView = view.findViewById(R.id.seatsAvailableText)
        val priceText: TextView = view.findViewById(R.id.priceText)
        val bookSeatButton: Button = view.findViewById(R.id.bookSeatButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bus_item, parent, false)
        return BusViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
        val bus = busList[position]
        holder.busTypeText.text = "Bus Type: ${bus.busType}"
        holder.routeText.text = "Route: ${bus.route}"
        holder.departureTimeText.text = "Departure: ${bus.departureTime}"
        holder.seatsAvailableText.text = "Seats Available: ${bus.seatsAvailable}"
        holder.priceText.text = "Price: $${bus.price}"

        holder.bookSeatButton.setOnClickListener {
            onBookClick(bus)
            val intent = Intent(context, SeatSelectionActivity::class.java)
            intent.putExtra("busType", bus.busType) // Pass the busId to SeatSelectionActivity
            context.startActivity(intent)
        }


    }

    override fun getItemCount() = busList.size
}
