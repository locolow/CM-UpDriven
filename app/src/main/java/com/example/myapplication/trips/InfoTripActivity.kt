package com.example.myapplication.trips

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.Utils
import com.example.myapplication.databinding.ActivityInfoBoleiaBinding

class InfoTripActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBoleiaBinding
    private lateinit var tripInfo: Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBoleiaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tripInfo = intent.getSerializableExtra(Utils.TRIP_INFO) as Trip
        setCustomTrip()
    }

    private fun setCustomTrip() {
        binding.partida.text = buildDesignatedString(getString(R.string.from), tripInfo.departure)
        binding.destino.text = buildDesignatedString(getString(R.string.from), tripInfo.destination)
        binding.txtDate.text = buildDesignatedString(getString(R.string.from), tripInfo.date)
        binding.txtTime.text = buildDesignatedString(getString(R.string.from), tripInfo.time)
        binding.txtPrice.text = buildDesignatedString(getString(R.string.from), tripInfo.price)
        binding.txtSeats.text = buildDesignatedString(getString(R.string.from), tripInfo.availableSeats)
    }

    private fun buildDesignatedString (str : String, value : String) : String {
        return buildString {
            this.append(str)
            this.append(" ")
            this.append(value)
        }
    }
}