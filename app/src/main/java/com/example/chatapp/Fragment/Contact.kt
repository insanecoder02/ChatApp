package com.example.chatapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Adapter.UserAdapter
import com.example.chatapp.ArrayList.User
import com.example.chatapp.R
import com.example.chatapp.databinding.FragmentContactBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Contact : Fragment() {
    private lateinit var user: ArrayList<User>
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var binding:FragmentContactBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        db = FirebaseDatabase.getInstance().getReference()
        db.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user.clear()
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if(currentUser?.uid != auth.currentUser?.uid){
                        user.add(currentUser!!)
                    }
                }
                binding.userRV.adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
        user = ArrayList()
        binding.userRV.adapter = UserAdapter(requireContext(), user)
        binding.userRV.layoutManager = LinearLayoutManager(requireContext())

    }
}