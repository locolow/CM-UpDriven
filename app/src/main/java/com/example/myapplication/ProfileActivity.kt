package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.example.myapplication.db.AppDatabase
import com.example.myapplication.db.entities.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var imagePreview: ImageView
    lateinit var btn_choose_image: Button
    lateinit var btn_upload_image: Button
    lateinit var currentFirebaseUser: String

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        btn_choose_image = findViewById(R.id.btn_choose_image)
        btn_upload_image = findViewById(R.id.btn_upload_image)
        imagePreview = findViewById(R.id.image_preview)

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        btn_choose_image.setOnClickListener { launchGallery() }
        btn_upload_image.setOnClickListener { uploadImage() }


        database = Firebase.database(Utils.DB_URL).reference

        //Get the UID from user
        currentFirebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imagePreview.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun uploadImage(){
        if(filePath != null){
            val ref = storageReference?.child("myImages/" + currentFirebaseUser)
            val uploadTask = ref?.putFile(filePath!!)

        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }
    }

    fun showEditNome(view: View) {
        val novoNome = findViewById<LinearLayout>(R.id.lleditName)
        novoNome.visibility = View.VISIBLE
    }

    fun editNome(view: View){
        var etNovoNome = findViewById<EditText>(R.id.etNovoNome)
        val name = etNovoNome.text.toString()

        database.child("Users").child(currentFirebaseUser).child("name").setValue(name)
        Toast.makeText(this, "Nome alterado com sucesso!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java).apply {}
        startActivity(intent)

    }

    fun showEditUsername(view: View) {
        val novoNome = findViewById<LinearLayout>(R.id.lleditUsername)
        novoNome.visibility = View.VISIBLE
    }

    fun EditUsername(view: View) {
        var etNovoNome = findViewById<EditText>(R.id.etNovoUsername)
        val name = etNovoNome.text.toString()

        database.child("Users").child(currentFirebaseUser).child("username").setValue(name)
        Toast.makeText(this, "Username alterado com sucesso!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java).apply {}
        startActivity(intent)

    }
}