package com.example.myapplication

import android.content.SharedPreferences
import com.google.firebase.database.FirebaseDatabase

object Utils {
    var USER_ID : String = "userId"
    const val IS_USER_LOGGED_IN : String = "isUserLoggedIn"
    const val DB_URL = "https://trabalhocm-d3be1-default-rtdb.europe-west1.firebasedatabase.app"
    const val API_KEY = "AIzaSyAcfZD-LUDgYcnBOxhuPC4l2LT5Mj5yLWg"
    const val SEARCH_RESULT_CODE = 102

    fun writeNewUser(user : User, userUid: String) {
        val database = FirebaseDatabase.getInstance(DB_URL).getReference("Users")
        database.child(userUid).setValue(user)
    }

    fun writeNewTrip(userUid: String, trip : Trip){
        val database = FirebaseDatabase.getInstance(DB_URL).getReference("Trip")
        database.child(userUid).setValue(trip)
    }

    fun getUserId(sharedPreferences: SharedPreferences) : String {
        return sharedPreferences.getString(USER_ID, "").toString()
    }

    fun saveLoginUser(sharedPreferences: SharedPreferences, userId: String) {
        sharedPreferences.edit().putString(USER_ID, userId).apply()
    }
}

enum class Trips {
    DEPARTURE, DESTINATION
}