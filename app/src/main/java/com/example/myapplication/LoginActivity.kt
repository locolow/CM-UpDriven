package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("FILE_1", Context.MODE_PRIVATE)

        if(Utils.getUserId(sharedPreferences).isNotEmpty()) startActivity(Intent(this, MainActivity::class.java))

        binding.here.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter your crendentials",
                    Toast.LENGTH_SHORT
                ).show()
            } else authenticateLogin()
        }

    }

    private fun authenticateLogin() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userUid: String? = task.result.user?.uid
                    userUid?.let { Utils.saveLoginUser(sharedPreferences, it) }
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(Utils.USER_ID, userUid)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        task.exception?.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
