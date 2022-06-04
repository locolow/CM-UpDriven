package com.example.myapplication

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import com.example.myapplication.Utils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Utils.SEARCH_RESULT_CODE
import com.example.myapplication.databinding.ActivityAddTripBinding
import com.example.myapplication.db.AppDatabase
import java.text.SimpleDateFormat
import java.util.*


class AddTripActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTripBinding
    private lateinit var database: AppDatabase
    private lateinit var sharedPreferences: SharedPreferences
    @RequiresApi(Build.VERSION_CODES.N)
    private var dateFormat = SimpleDateFormat("dd MMM YYYY")
    private var timeFormat = SimpleDateFormat("HH:mm")
    private val dateCalendar = Calendar.getInstance()
    private val timeCalendar = Calendar.getInstance()
    private val style = AlertDialog.THEME_HOLO_DARK
    private var tripType = Trips.DEPARTURE
    private var trip = Trip()



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTripBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("FILE_1", Context.MODE_PRIVATE)
        binding.btnDeparture.setOnClickListener {
            tripType = Trips.DEPARTURE
            startActivityForResult(Intent(this, SearchActivity::class.java), SEARCH_RESULT_CODE)
        }
        binding.btnDestination.setOnClickListener {
            tripType = Trips.DESTINATION
            startActivityForResult(Intent(this, SearchActivity::class.java), SEARCH_RESULT_CODE)
        }

        binding.btnSubmit.setOnClickListener {
            if(trip.departure.isNotEmpty() &&
                trip.departureLatitude.isNotEmpty() &&
                trip.departureLongitude.isNotEmpty() &&
                trip.destination.isNotEmpty() &&
                trip.destinationLatitude.isNotEmpty() &&
                trip.destinationLongitude.isNotEmpty() &&
                trip.time.isNotEmpty()
            )
            Utils.writeNewTrip(
                userUid = Utils.getUserId(sharedPreferences),
                trip = Trip(
                    departure = trip.departure,
                    departureLatitude = trip.departureLatitude,
                    departureLongitude = trip.departureLongitude,
                    destination = trip.destination,
                    destinationLatitude = trip.destinationLatitude,
                    destinationLongitude = trip.destinationLongitude,
                    date = "01-01-1996",
                    time = trip.time,
                    price = "3",
                    availableSeats = "4"
                )
            )
        }

        binding.btnDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    Toast.makeText(
                        this,
                        "date : $dateFormat.format(selectedDate.time)",
                        Toast.LENGTH_SHORT
                    ).show()
                    trip.date = dateFormat.format(selectedDate.time)
                    binding.btnDate.text = dateFormat.format(selectedDate.time)
                },
                dateCalendar.get(Calendar.YEAR),
                dateCalendar.get(Calendar.MONTH),
                dateCalendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        binding.btnTime.setOnClickListener {
            val timePicker = TimePickerDialog(
                this, style, { view, hourOfDay, minute ->
                    val selectedTime = Calendar.getInstance()
                    selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    selectedTime.set(Calendar.MINUTE, minute)
                    val time = timeFormat.format(selectedTime.time)
                    binding.testecalendar.text = time
                    Toast.makeText(
                        this,
                        "time: $time",
                        Toast.LENGTH_SHORT
                    ).show()
                    trip.time = time
                    binding.btnTime.text = time
                },

                timeCalendar.get(Calendar.HOUR_OF_DAY), timeCalendar.get(Calendar.MINUTE), true
            )
            timePicker.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       // if (resultCode == SEARCH_RESULT_CODE && resultCode == RESULT_OK) {
        if (resultCode == SEARCH_RESULT_CODE) {
            if(data != null) {
                val address = data.getStringExtra("address")
                val lat = data.getStringExtra("lat")
                val long = data.getStringExtra("long")
                if (tripType == Trips.DEPARTURE) {
                    binding.btnDeparture.text = address
                    trip.departure = address.toString()
                    trip.departureLatitude = lat.toString()
                    trip.departureLongitude = long.toString()
                }
                else {
                    binding.btnDestination.text = address
                    trip.destination = address.toString()
                    trip.destinationLatitude = lat.toString()
                    trip.destinationLongitude = long.toString()
                }
            }
        }
    }
}


/* private fun startSearchView() {
        Places.initialize(applicationContext, Utils.API_KEY)
        departures.add( "Viana")
        departures.add( "Porto")

        val adapter = ArrayAdapter<String>(this, R.layout.item_search_results, departures)
        binding.listviewDeparture.adapter = adapter

        adapter.sort { o1, o2 -> o1.compareTo(o2!!) }

        binding.searchDeparture.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val fieldList : List<Place.Field> = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
                val intent : Intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(this@AddTripActivity)
                startActivityForResult(intent, 100)
                departures.add( "Gondomar")
                adapter.addAll()
                adapter.notifyDataSetChanged()
                return false
            }
        })

        binding.listviewDeparture.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                binding.searchDeparture.setQuery(p0?.getItemAtPosition(p2).toString(), false)
            }
        }
    }*/
