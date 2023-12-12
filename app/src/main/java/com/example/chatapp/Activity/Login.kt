package com.example.chatapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.logButton.setOnClickListener {
            login(
                binding.editEmail.text.toString(), binding.editPass.text.toString()
            )
        }
        binding.signButton.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    private fun login(
        email: String, pass: String
    ) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this, Home::class.java))
                finish()
            } else {
                Toast.makeText(
                    this@Login,
                    task.exception?.localizedMessage,
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}