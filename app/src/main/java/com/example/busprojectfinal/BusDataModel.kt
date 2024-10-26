package com.example.busprojectfinal

data class Bus(
    val busType: String = "",
    val totalSeats: Int = 0,
    val seatsAvailable: Int = 0,
    val departureTime: String = "",
    val route: String = "",
    val price: Double = 0.0
)