package com.example.chatapp.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.chatapp.Activity.Login
import com.example.chatapp.databinding.FragmentProfileBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Profile : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private var userId:String?= null
    private lateinit var database:FirebaseDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseApp.initializeApp(requireContext())

        auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid

        database=FirebaseDatabase.getInstance()
        val databaseReference: DatabaseReference = database.reference.child("user").child(userId!!)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userName = dataSnapshot.child("name").getValue(String::class.java)
                    val userEmail = dataSnapshot.child("email").getValue(String::class.java)

                    if (userName != null) {
                        binding.name.setText(userName)
                    }
                    binding.email.text = userEmail
                    binding.password.text = userId
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })

        binding.name.setOnClickListener {
            val updatedText = binding.name.text.toString()
            binding.name.setText(updatedText)
            val usersReference: DatabaseReference = database.reference.child("user")
            usersReference.child(userId!!).child("name").setValue(updatedText)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "name updated succesgully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "failed" , Toast.LENGTH_SHORT).show()
                }

        }
        binding.logButt.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

    }
}