package com.example.myapplication.db

import androidx.room.TypeConverter
import com.example.myapplication.Localization
import com.google.gson.Gson

class Converter {
    @TypeConverter
    fun stringtoLocalization(value : String) : Localization {
            return Gson().fromJson(value, Localization::class.java)
    }

    @TypeConverter
    fun localizationToString(value : Localization) : String{
        return Gson().toJson(value)
    }
}