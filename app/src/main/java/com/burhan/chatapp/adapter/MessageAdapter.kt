package com.burhan.chatapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.burhan.chatapp.data.Message
import com.burhan.chatapp.databinding.ReceiveBinding
import com.burhan.chatapp.databinding.SendBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_SENT = 1
    val ITEM_RECEIVE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val binding = SendBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return SentViewHolder(binding)
        } else {
            val binding = ReceiveBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return ReceiveViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            holder.sentMessage.text = currentMessage.message
        } else {
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_SENT
        } else {
            return ITEM_RECEIVE
        }
    }

    class SentViewHolder(private val binding: SendBinding) : RecyclerView.ViewHolder(binding.root) {
        val sentMessage = binding.sendMsg

    }

    class ReceiveViewHolder(private val binding: ReceiveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val receiveMessage = binding.receiveMsg

    }

}