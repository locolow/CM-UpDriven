package com.example.myapplication.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.Localization
import org.jetbrains.annotations.NotNull

@Entity(tableName = "Trip")
data class TripEntity (

    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "departure")
    @NotNull
    var departure: Localization,

    @ColumnInfo(name = "destination")
    @NotNull
    var destination: Localization,

    @ColumnInfo(name = "price")
    @NotNull
    var price: String,

    @ColumnInfo(name = "avaiableSeats")
    @NotNull
    var avaiableSeats: String,

    )