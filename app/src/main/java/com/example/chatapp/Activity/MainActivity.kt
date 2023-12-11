package com.example.chatapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
                loadFragment(Login())
        }

        binding.signinButton.setOnClickListener{
            loadFragment(Signin())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment) // R.id.fragmentContainer is the ID of the FrameLayout in your layout where you want to load fragments
        transaction.addToBackStack(null) // This adds the transaction to the back stack, so the user can navigate back to the previous fragment
        transaction.commit()
    }
}