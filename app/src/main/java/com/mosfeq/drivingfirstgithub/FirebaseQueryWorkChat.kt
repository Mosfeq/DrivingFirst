package com.mosfeq.drivingfirstgithub

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mosfeq.drivingfirstgithub.dataClasses.User
import com.mosfeq.drivingfirstgithub.dataClasses.UserGroup
import com.mosfeq.drivingfirstgithub.dataClasses.UserGroup.Companion.toGroup
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseQueryWorkChat {

    private val db = Firebase.firestore

    fun addUserIntoFireStore(user: User){
        db.collection("User").document(user.uid).set(user)
    }

    fun createGroup(iname:String,lname:String,iemail:String,lemail:String,iuri:String,luri:String){
        val id = db.collection("Groups").document().id
        val newGroup = UserGroup(
            createdAt = getCurrentDateTime().toString(),
            createdBy = Firebase.auth.currentUser?.uid,
            groupId = id,
            name ="",
            recentMessage = "",
            lemail = lemail,
            iemail = iemail,
            iuri = iuri,
            luri = luri,
            iname = iname,
            lname = lname,
            timeOfLastMessage = ""
        )
        db.collection("Groups").document(id).set(newGroup)
    }

    @ExperimentalCoroutinesApi
    fun getGroups(): Flow<List<UserGroup>?> {
        return callbackFlow {
            val listener = db.collection("Groups")
                .orderBy("name")
                .addSnapshotListener { value, error ->
                    if(error!=null){
//                    Log.d(TAG, "getGroups: error occurred ")
                        return@addSnapshotListener
                    }
                    val map = value?.documents?.map {
                        it.toGroup()
                    }
                    this.trySend(map).isSuccess
                }
            awaitClose {
                listener.remove()
            }
        }
    }
}