package com.mosfeq.drivingfirstgithub.chatForBoth.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.dataClasses.Message
import com.mosfeq.drivingfirstgithub.databinding.ReceivedTextItemBinding
import com.mosfeq.drivingfirstgithub.databinding.SendTextItemBinding

class ChatAdapter: ListAdapter<Message, ChatHolder>(ChatDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        return when(viewType){
            R.layout.received_text_item-> ChatHolder
                .ReceivedText(
                    ReceivedTextItemBinding
                        .inflate(LayoutInflater.from(parent.context),parent,false)
                )
            R.layout.send_text_item-> ChatHolder
                .SendText(
                    SendTextItemBinding
                        .inflate(LayoutInflater.from(parent.context),parent,false)
                )
            else -> throw IllegalArgumentException("Illegal View type")
        }
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        when(holder){
            is ChatHolder.SendText-> holder.bind(getItem(position))
            is ChatHolder.ReceivedText -> holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message= getItem(position)
        if(message.sentBy== Firebase.auth.currentUser?.uid){
            return R.layout.send_text_item
        }
        return R.layout.received_text_item
    }

    class ChatDiffUtil: DiffUtil.ItemCallback<Message>(){
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem==newItem
        }

    }
}