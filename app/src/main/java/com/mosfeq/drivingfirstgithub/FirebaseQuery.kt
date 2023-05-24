package com.mosfeq.drivingfirstgithub

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mosfeq.drivingfirstgithub.dataClasses.Message
import com.mosfeq.drivingfirstgithub.dataClasses.Message.Companion.toMessage
import com.mosfeq.drivingfirstgithub.dataClasses.User
import com.mosfeq.drivingfirstgithub.dataClasses.UserGroup
import com.mosfeq.drivingfirstgithub.dataClasses.UserGroup.Companion.toGroup
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseQuery {

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
    suspend fun loadInitialChat(groupId:String):List<Message>{
        val groupMessage =db.collection("Message").document(groupId).collection("messages")
        val list = mutableListOf<Message>()
        try{
            val hold = groupMessage
                .orderBy("sendAt")
                .get()
                .await()

            for(document in hold.documents){
                document.toObject(Message::class.java)?.let { list.add(it) }
            }
        }catch (e:Exception){
//            Log.d(TAG, "loadInitialChat: error")
        }
        return list
    }

    fun postMessage(groupId:String,messageTextNew:String){
        val messageId = db.collection("Message").document(groupId).collection("messages").document().id

        val newMessage = Message(
            messageText = messageTextNew,
            sentBy = Firebase.auth.currentUser?.uid,
            sendAt = Timestamp.now(),
            senderName = Firebase.auth.currentUser?.displayName,
            id = messageId
        )
        db.collection("Message").document(groupId).collection("messages").document(messageId).set(newMessage)
    }

    @ExperimentalCoroutinesApi
    fun listenChat(groupId:String):Flow<List<Message>?>{
        return callbackFlow {
            val listener = db
                .collection("Message")
                .document(groupId)
                .collection("messages")
                .orderBy("sendAt")
                .addSnapshotListener { value, error ->
                    if (error != null) {
//                                Log.d(TAG, "listenChat: error!")
                        return@addSnapshotListener
                    }
                    val map = value?.documents?.map {
                        it.toMessage()
                    }
                    map?.let {
                        if (map.isNotEmpty()){
                            updateGroupFinalMessage(map.last(),groupId)
                        }
                    }
                    trySend(map).isSuccess
                }
            awaitClose {
                listener.remove()
            }
        }
    }

    private fun updateGroupFinalMessage(finalMessage: Message,groupId: String){
        db.collection("Groups").document(groupId).update("recentMessage",finalMessage.messageText)
        db.collection("Groups").document(groupId).update("timeOfLastMessage",finalMessage.sendAt?.toDate()?.toString("yyyy/MM/dd HH:mm:ss"))
    }
    //Updates message and time in User Group according to message sent
}