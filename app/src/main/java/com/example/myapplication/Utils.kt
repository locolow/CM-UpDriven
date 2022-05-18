package com.example.myapplication

import com.google.firebase.database.FirebaseDatabase

object Utils {
    var USER_ID : String = "userId"
    const val DB_URL = "https://trabalhocm-d3be1-default-rtdb.europe-west1.firebasedatabase.app"

    fun writeNewUser(username: String, email: String, name: String, userUid: String) {
        val user = User(username, email, name)
        val database = FirebaseDatabase.getInstance(DB_URL).getReference("Users")
        database.child(userUid).setValue(user)
    }
}