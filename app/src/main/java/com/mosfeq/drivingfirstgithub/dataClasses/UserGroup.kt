package com.mosfeq.drivingfirstgithub.dataClasses

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserGroup(
    val createdAt:String?="",
    val createdBy:String?="",
    val groupId:String?="",
    val name:String?="",
    var recentMessage:String?="",
    var lemail:String?="",
    var iemail:String?="",
    var iuri:String?="",
    var luri:String?="",
    var iname:String?="",
    var lname:String?="",
    val timeOfLastMessage:String?="",
): Parcelable {
    //Send object though function
    companion object {
        fun DocumentSnapshot.toGroup(): UserGroup {
            val createdAt = getString("createdAt")
            val createdBy = getString("CreatedBy")
            val groupId = getString("groupId")
            val name = getString("name")
            val recentMessage = getString("recentMessage")
            val lemail = getString("lemail")
            val iemail = getString("iemail")
            val iuri = getString("iuri")
            val luri = getString("luri")
            val iname = getString("iname")
            val lname = getString("lname")
            val timeOfLastMessage = getString("timeOfLastMessage")
        return UserGroup(createdAt,createdBy,groupId, name, recentMessage,lemail,iemail,iuri,luri,iname,lname, timeOfLastMessage)
        }
    }
}
