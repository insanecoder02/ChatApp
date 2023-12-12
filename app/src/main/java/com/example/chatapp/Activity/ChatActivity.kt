package com.example.chatapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Adapter.MessageAdapter
import com.example.chatapp.ArrayList.Message
import com.example.chatapp.databinding.ActivityChatBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageList: ArrayList<Message>
    private lateinit var db: DatabaseReference

    var recieverRoom: String? = null
    var senderRoom: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance().getReference()
        val name = intent.getStringExtra("name")
        val reciverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        senderRoom = reciverUid + senderUid
        recieverRoom = senderUid + reciverUid
        supportActionBar?.title = name
        messageList = ArrayList()
        db.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children) {
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    binding.chatRv.adapter?.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        binding.sendChat.setOnClickListener {
            val messageObject = Message(binding.chatText.text.toString(), senderUid)

            db.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject)
                .addOnSuccessListener {
                    db.child("chats").child(recieverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.chatText.setText("")
        }
        binding.chatRv.adapter = MessageAdapter(this, messageList)
        binding.chatRv.layoutManager = LinearLayoutManager(this)
    }
}