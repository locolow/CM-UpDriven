package com.example.myapplication

data class Trip(
    var departure: Localization,
    var destination: Localization,
    var price: String,
    var avaiableSeats: String
)

data class Localization(
    var city: String,
    var street: String,
    var lat: String,
    var long: String
)