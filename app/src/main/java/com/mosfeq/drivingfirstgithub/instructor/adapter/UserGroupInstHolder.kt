package com.mosfeq.drivingfirstgithub.instructor.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mosfeq.drivingfirstgithub.dataClasses.UserGroup
import com.mosfeq.drivingfirstgithub.databinding.ItemGroupBinding

class UserGroupInstHolder(private val binding: ItemGroupBinding) :
    RecyclerView.ViewHolder(binding.root) {

    var onClickHandler: ((item: UserGroup) -> Unit)? = null

    fun bind(item: UserGroup) {
        binding.apply {
            groupNameTextView.text = item.lname
            latestTime.text = item.timeOfLastMessage
            recentMessage.text = item.recentMessage
            Glide.with(groupImageView).load(item.luri)
                .into(groupImageView)
            root.setOnClickListener {
                onClickHandler?.invoke(item)
            }
        }
    }
}