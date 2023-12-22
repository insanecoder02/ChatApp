package com.example.chatapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.chatapp.Fragment.Call
import com.example.chatapp.Fragment.Home
import com.example.chatapp.Fragment.Profile
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityMainBinding

class Main : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.calls -> loadFragment(Call())
                R.id.chat -> loadFragment(Home())
                R.id.profile -> loadFragment(Profile())
            }
            true
        }
        loadFragment(Home())
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }
}