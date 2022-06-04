package com.example.myapplication

data class Trip(
    var departure: String = "",
    var departureLatitude: String = "",
    var departureLongitude: String = "",
    var destination: String = "",
    var destinationLatitude: String = "",
    var destinationLongitude: String = "",
    var date: String = "",
    var time: String = "",
    var price: String = "",
    var availableSeats: String = "",
)