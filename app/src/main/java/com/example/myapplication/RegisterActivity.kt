package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Utils.USER_ID
import com.example.myapplication.Utils.writeNewUser
import com.example.myapplication.databinding.ActivityRegisterBinding
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.entities.UserEntity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var name: String

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase(this)

        binding.btnRegister.setOnClickListener {
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()
            username = binding.etUsername.text.toString()
            name = binding.etName.text.toString()
            if (email.isEmpty() || password.isEmpty() || username.isEmpty() || name.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter your crendentials",
                    Toast.LENGTH_SHORT
                ).show()
            } else registerUser()
        }
    }

    private fun registerUser() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                      val userUid: String? = task.result.user?.uid
                    Toast.makeText(
                        this,
                        "Registado Com Sucesso !",
                        Toast.LENGTH_SHORT
                    ).show()

                    writeNewUser(username, email, name, userUid!!)
                    database.userDao().insertOrReplace(
                        UserEntity(userUid,username,email,name)
                    )

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra(USER_ID, userUid)
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