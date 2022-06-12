package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ViewTripListItemBinding

class TripListAdapter(var item  : List<Trip>):RecyclerView.Adapter<TripListAdapter.UserHolder>() {

    class UserHolder(private val binding: ViewTripListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trip: Trip) {
            binding.txtDepartureInfo.text = trip.departure
            binding.txtDestinationInfo.text = trip.destination
            binding.txtSeats.text = trip.availableSeats
            binding.txtDate.text = "${trip.date}, ${trip.time}"
            binding.txtPrice.text = "${trip.price} €"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val binding = ViewTripListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(binding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }

}