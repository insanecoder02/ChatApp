package com.example.chatapp.Activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chatapp.ArrayList.User
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
//    private val PICK_IMAGE_REQUEST = 1
//    private var selectedImageUri: Uri? = Uri.parse("android.resource://com.example.chatapp.Activity/" + R.drawable.account)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference
//        binding.userImage.setOnClickListener {
//            pickImage()
//        }

        binding.signButton.setOnClickListener {
            val name = binding.editName.text.toString()
            val email = binding.editEmail.text.toString()
            val pass = binding.editPass.text.toString()

                // If an image is selected, proceed with sign-in
//                val imageName = UUID.randomUUID().toString()
//                val imageRef = storageRef.child("images/$imageName.jpg")
//                val uploadTask = imageRef.putFile(selectedImageUri!!)
//
//                uploadTask.continueWithTask { task ->
//                    if (!task.isSuccessful) {
//                        task.exception?.let {
//                            throw it
//                        }
//                    }
//                    imageRef.downloadUrl
//                }.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val downloadUri = task.result
//                        val imageUrl = downloadUri.toString()

                        // Call the signin function with the imageUrl
                        signin(name, email, pass)
//                    } else /
                        // Handle failures
//                        Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
//                    }
//                }
        }

        binding.logButton.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

//    private fun pickImage() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, PICK_IMAGE_REQUEST)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
//            val imageUri = data.data
//            if (imageUri != null) {
//                selectedImageUri = imageUri
//                val imageName = UUID.randomUUID().toString()
//
//                val imageRef = storageRef.child("images/$imageName.jpg")
//                val uploadTask = imageRef.putFile(selectedImageUri!!)
//
//                uploadTask.continueWithTask { task ->
//                    if (!task.isSuccessful) {
//                        task.exception?.let {
//                            throw it
//                        }
//                    }
//                    imageRef.downloadUrl
//                }.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val downloadUri = task.result
//                        Glide.with(this)
//                            .load(selectedImageUri)
//                            .placeholder(R.drawable.account)
//                            .circleCrop()
//                            .error(R.drawable.ic_launcher_foreground)
//                            .apply(RequestOptions().override(300, 300)) // Adjust the size as needed
//                            .into(binding.userImage)
//
//                        // Continue with your logic here, if needed
//                    } else {
//                        // Handle failures
//                        Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } else {
//                Toast.makeText(this, "Failed to retrieve image", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }


    private fun signin(name: String, email: String, pass: String) {
            auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, auth.currentUser?.uid!!)
                    startActivity(Intent(this@SignUp, Main::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@SignUp,
                        task.exception?.localizedMessage,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        db = FirebaseDatabase.getInstance().getReference()
        db.child("user").child(uid).setValue(User(name, email, uid))
    }
}