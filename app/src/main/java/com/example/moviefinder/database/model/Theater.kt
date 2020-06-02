package com.example.moviefinder.database.model

data class Theater(
    val id: String,
    val name: String,
    val fullAddress: String,
    val lat: Double,
    val lng: Double,
    val phone: String
)