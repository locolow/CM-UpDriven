package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Utils.SEARCH_RESULT_CODE
import com.example.myapplication.databinding.ActivityTestBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*


class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Places.initialize(applicationContext, Utils.API_KEY)

        val fieldList : List<Place.Field> = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
        val intent : Intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(this@SearchActivity)
        startActivityForResult(intent, SEARCH_RESULT_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*if(requestCode == SEARCH_RESULT_CODE && resultCode == RESULT_OK) {
            val place : Place = Autocomplete.getPlaceFromIntent(data)
            val intent = Intent()
            intent.putExtra("address", place.address)
            intent.putExtra("lat", place.latLng.latitude)
            intent.putExtra("long", place.latLng.longitude)
            setResult(SEARCH_RESULT_CODE, intent)
            finish()*/
        if(requestCode == SEARCH_RESULT_CODE) {
            val intent = Intent()
            intent.putExtra("address", "Rua do Outeiro")
            intent.putExtra("lat", "-1")
            intent.putExtra("long", "24")
            setResult(SEARCH_RESULT_CODE, intent)
            finish()
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Toast.makeText(
                this,
                "There was an error opening the search view",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
