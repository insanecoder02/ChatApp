package com.example.chatapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.chatapp.ArrayList.Message
import com.example.chatapp.R
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessageAdapter(val context: Context, val message: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIEVE = 1
    val ITEM_SENT = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val view = LayoutInflater.from(context).inflate(R.layout.reciever_layout, parent, false)
            return RecieverViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false)
            return SenderViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return message.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = message[position]
        if (holder.javaClass == SenderViewHolder::class.java) {
            val viewHolder = holder as SenderViewHolder
            holder.sentMessage.text = currentMessage.message
            Glide.with(context).load(R.drawable.account).into(holder.sentImg)

            val formattedTime = currentMessage.time?.let {Date(it)}?.let { SimpleDateFormat("HH:mm", Locale.getDefault()).format(it) }
            holder.sendTime.text = formattedTime
        } else {
            val viewHolder = holder as RecieverViewHolder
            holder.reciverMessage.text = currentMessage.message
            Glide.with(context).load(R.drawable.account).into(holder.recImg)
            val formattedTime = currentMessage.time?.let {Date(it)}?.let { SimpleDateFormat("HH:mm", Locale.getDefault()).format(it) }
            holder.recTime.text = formattedTime
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = message[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.uid)) {
            return ITEM_SENT
        } else {
            return ITEM_RECIEVE
        }
    }

    class SenderViewHolder(itemView: View) : ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.senderMessage)
        val sentImg: ImageView = itemView.findViewById(R.id.senderImage)
        val sendTime: TextView = itemView.findViewById(R.id.sendertime)


    }

    class RecieverViewHolder(itemView: View) : ViewHolder(itemView) {
        val reciverMessage = itemView.findViewById<TextView>(R.id.recieverMessage)
        val recImg: ImageView = itemView.findViewById(R.id.recieverImage)
        val recTime: TextView = itemView.findViewById(R.id.rectime)
    }

}