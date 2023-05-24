package com.mosfeq.drivingfirstgithub.chatForBoth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatViewModelFactory(private val groupId: String): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(groupId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}