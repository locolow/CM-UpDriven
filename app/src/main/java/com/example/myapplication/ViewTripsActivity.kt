package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityViewTripsBinding
import com.example.myapplication.db.AppDatabase
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ViewTripsActivity :  AppCompatActivity() {

    private lateinit var binding: ActivityViewTripsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var databaseTripReference: DatabaseReference
    private lateinit var database : AppDatabase
    private lateinit var tripsList : ArrayList<Trip>
    val sdf = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewTripsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("FILE_1", Context.MODE_PRIVATE)
        databaseTripReference = FirebaseDatabase.getInstance(Utils.DB_URL).reference
        database = AppDatabase(this)

        binding.tripsListRecyclerView.layoutManager = LinearLayoutManager(this)
        databaseTripReference.child("Trip").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    tripsList = ArrayList()
                    snapshot.children.forEach {
                        val dataSnapshot: Trip = it.getValue(Trip::class.java) as Trip
                        val date = sdf.parse(dataSnapshot.date)
                        val isOutdated = Date().after(date)
                        if(!isOutdated) tripsList.add(dataSnapshot)
                        }
                    binding.tripsListRecyclerView.adapter = TripListAdapter(tripsList)
                    }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewTripsActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}