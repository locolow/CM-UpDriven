package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.myapplication.databinding.ActivityAddTripBinding
import com.example.myapplication.db.AppDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.*

class AddTripActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var binding : ActivityAddTripBinding
    private lateinit var database: AppDatabase
    @SuppressLint("NewApi")
    var dateFormat = SimpleDateFormat("dd MMM YYYY")
    var timeFormat = SimpleDateFormat("HH:mm")
    var now = Calendar.getInstance()
    var now2 = Calendar.getInstance()
    var style = AlertDialog.THEME_HOLO_DARK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTripBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val date = dateFormat.format(selectedDate.time)

                    Toast.makeText(this, "date : $date", Toast.LENGTH_SHORT).show()

                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
        binding.etTime.setOnClickListener{
                val timePicker = TimePickerDialog(this,style,TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    var time = timeFormat.format(selectedTime.time)
                    binding.testecalendar.text = time
                    Toast.makeText(
                        this,
                        "time: $time",
                        Toast.LENGTH_SHORT
                    ).show() },
                        now2.get(Calendar.HOUR_OF_DAY),now2.get(Calendar.MINUTE),true
                )
                        timePicker.show()
        }

        //GET LOCATION

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        tvLatitude=findViewById(R.id.testeLat)
        tvLongitude=findViewById(R.id.testeLong)


        getCurrentLocation()

    }


     private fun getCurrentLocation(){
        if(checkPermission())
        {
            if(isLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(this){ task ->
                    val location: Location?=task.result
                    if(location==null){
                        Toast.makeText(this,"Null",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Sucess",Toast.LENGTH_SHORT).show()
                        tvLatitude.text=""+location.latitude
                        tvLongitude.text=""+location.longitude
                    }
                }


            }else{
                Toast.makeText(this,"Turn on Location",Toast.LENGTH_SHORT).show()
                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }else{
                requestPermission()
        }
    }

    private fun isLocationEnabled(): Boolean{
        val locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
    }

    private fun checkPermission() : Boolean
    {
        if(ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)
            ==PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                return true
            }
            return false
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== PERMISSION_REQUEST_ACCESS_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(applicationContext,"Garanted",Toast.LENGTH_SHORT).show()
            }
        }   else{
            Toast.makeText(applicationContext,"NOT Garanted",Toast.LENGTH_SHORT).show()

        }
    }
}
