package com.example.chatapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.ArrayList.User
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.signButton.setOnClickListener {
            signin(
                binding.editName.text.toString(),
                binding.editEmail.text.toString(),
                binding.editPass.text.toString()
            )
        }
        binding.logButton.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

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