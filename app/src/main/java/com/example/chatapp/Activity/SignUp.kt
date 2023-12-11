package com.example.chatapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.signButton.setOnClickListener {
            signin(
                binding.editEmail.toString(), binding.editPass.toString()
            )

        }
        binding.logButton.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    private fun signin(email: String, pass: String) {

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this, Home::class.java))
            } else {
                Toast.makeText(
                    this@SignUp,
                    task.exception?.localizedMessage,
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

    }
}