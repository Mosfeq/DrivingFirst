package com.mosfeq.drivingfirstgithub.chatForBoth.adapters

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mosfeq.drivingfirstgithub.dataClasses.Message
import com.mosfeq.drivingfirstgithub.databinding.ReceivedTextItemBinding
import com.mosfeq.drivingfirstgithub.databinding.SendTextItemBinding
import com.mosfeq.drivingfirstgithub.toString

sealed class ChatHolder(binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

    class ReceivedText(private val binding: ReceivedTextItemBinding): ChatHolder(binding){
        fun bind(message: Message){
            binding.apply {
                nameText.text=message.senderName
                textMessageIncoming.text=message.messageText
                timeText.text=message.sendAt?.toDate()?.toString("HH:mm")
            }
        }
    }

    class SendText(private val binding: SendTextItemBinding): ChatHolder(binding){
        fun bind(message: Message){
            binding.apply {
                textMessageOutgoing.text=message.messageText
                timeTextSent.text=message.sendAt?.toDate()?.toString("HH:mm")
            }
        }
    }
}