package com.mosfeq.drivingfirstgithub.chatForBoth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mosfeq.drivingfirstgithub.FirebaseQuery
import com.mosfeq.drivingfirstgithub.dataClasses.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class ChatViewModel(
    val groupId:String
): ViewModel() {

    private val firebaseQuery: FirebaseQuery = FirebaseQuery()

    private var _loadInitialChat = MutableLiveData<List<Message>>()
    val loadInitialChat: LiveData<List<Message>>
    get() = _loadInitialChat

    init {
        groupId.let {
            viewModelScope.launch {
                _loadInitialChat.postValue(firebaseQuery.loadInitialChat(it))
            }
        }
    }

    fun putData(message:String)=viewModelScope.launch {
        groupId.let {
            firebaseQuery.postMessage(it, message)
        }

    }

    @ExperimentalCoroutinesApi
    val chats = firebaseQuery.listenChat(groupId).asLiveData()
}