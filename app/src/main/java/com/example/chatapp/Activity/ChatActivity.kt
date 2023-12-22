package com.example.chatapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Adapter.MessageAdapter
import com.example.chatapp.ArrayList.Message
import com.example.chatapp.databinding.ActivityChatBinding
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

        messageList = ArrayList()

        binding.chatRv.adapter = MessageAdapter(this, messageList)
        binding.chatRv.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }

        db = FirebaseDatabase.getInstance().getReference()
        val name = intent.getStringExtra("name")
        val reciverUid = intent.getStringExtra("uid")
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        binding.toolName.text = name
        senderRoom = reciverUid + senderUid
        recieverRoom = senderUid + reciverUid
        supportActionBar?.title = name
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
            val messageText = binding.chatText.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val messageObject = Message(binding.chatText.text.toString(), senderUid)

                db.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(messageObject)
                    .addOnSuccessListener {
                        db.child("chats").child(recieverRoom!!).child("messages").push()
                            .setValue(messageObject)
                        MessageAdapter(this, messageList).notifyItemInserted(messageList.size - 1)
                        binding.chatRv.smoothScrollToPosition(messageList.size - 1)
                    }
                binding.chatText.setText("")
            }

        }
    }
}