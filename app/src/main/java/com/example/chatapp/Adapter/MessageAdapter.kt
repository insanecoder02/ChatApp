package com.example.chatapp.Adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.ArrayList.Message

class MessageAdapter(val context: Context, val message: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

    }

    override fun getItemCount(): Int {
        return message.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentUser = Fi
        if (holder.javaClass == SenderViewHolder::class.java) {

        } else {

        }
    }

    class SenderViewHolder(itemView: View) : ViewHolder(itemView) {

    }

    class RecieverViewHolder(itemView: View) : ViewHolder(itemView) {

    }

}