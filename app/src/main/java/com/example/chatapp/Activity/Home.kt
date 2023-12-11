package com.example.chatapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Adapter.UserAdapter
import com.example.chatapp.User
import com.example.chatapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var user:ArrayList<User>
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        user = ArrayList()
        binding.userRV.adapter = UserAdapter(this,user)
        binding.userRV.layoutManager = LinearLayoutManager(this)
    }
}