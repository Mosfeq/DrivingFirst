package com.mosfeq.drivingfirstgithub.learner.messaging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mosfeq.drivingfirstgithub.FirebaseQueryWorkChat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class UserGroupViewModel: ViewModel() {

    private val firebaseQuery = FirebaseQueryWorkChat()

    @ExperimentalCoroutinesApi
    val groups = firebaseQuery.getGroups().asLiveData()

    fun addNewGroup(iname:String,Lname:String,iemail: String,lemail:String,insUri:String,lUri:String)= viewModelScope.launch {
        firebaseQuery.createGroup(iname,Lname,iemail,lemail,insUri,lUri)
    }
}