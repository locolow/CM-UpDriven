package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.Utils.DB_URL
import com.example.myapplication.Utils.USER_ID
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.dao.UserDao
import com.example.myapplication.db.entities.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var userRemote : User
    private lateinit var userLocal : UserEntity
    private lateinit var userUid : String
    private lateinit var roomDatabase : AppDatabase
    private lateinit var userDao : UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = Firebase.database(DB_URL).reference
        roomDatabase = AppDatabase(this)
        userDao = roomDatabase.userDao()
        intent.getStringExtra(USER_ID).let {
            if (it != null) {
                userUid = it
            }
        }

        database.child("Users").child(userUid).get().addOnSuccessListener { data ->
            userRemote = data.getValue(User::class.java)!!
            userDao.insertOrReplace(
                UserEntity(
                id = userUid, username = userRemote.username, name = userRemote.name, email = userRemote.email)
            )
        }.addOnFailureListener { exception ->
            Toast.makeText(this, exception.message.toString(), Toast.LENGTH_SHORT)
        }.addOnCompleteListener {
            userLocal = userDao.getUserById(userUid)
            binding.lblUsername.text = userLocal.username
            binding.lblEmail.text = userLocal.email
        }

        binding.btnLogout.setOnClickListener { logout() }

        println(roomDatabase.userDao().getAllUsers().get(0))


    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


}