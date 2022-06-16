package com.example.myapplication.db

import androidx.room.TypeConverter
import com.example.myapplication.trips.Trip
import com.google.gson.Gson

class Converter {
    @TypeConverter
    fun stringtoLocalization(value : String) : Trip {
            return Gson().fromJson(value, Trip::class.java)
    }

    @TypeConverter
    fun localizationToString(value : Trip) : String{
        return Gson().toJson(value)
    }
}