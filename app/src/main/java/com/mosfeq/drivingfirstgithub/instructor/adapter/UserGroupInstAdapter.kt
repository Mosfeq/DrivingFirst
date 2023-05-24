package com.mosfeq.drivingfirstgithub.instructor.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mosfeq.drivingfirstgithub.dataClasses.UserGroup
import com.mosfeq.drivingfirstgithub.databinding.ItemGroupBinding

class UserGroupInstAdapter: ListAdapter<UserGroup, UserGroupInstHolder>(DiffCallback()) {

    var onClickHandler:((item: UserGroup)->Unit)?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGroupInstHolder {
        val binding = ItemGroupBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserGroupInstHolder(binding)
    }

    override fun onBindViewHolder(holder: UserGroupInstHolder, position: Int) {
        val item=this.getItem(position)
        holder.onClickHandler=onClickHandler

        holder.bind(item)
    }

    class DiffCallback: DiffUtil.ItemCallback<UserGroup>(){
        override fun areItemsTheSame(oldItem: UserGroup, newItem: UserGroup): Boolean {
            return oldItem.groupId==newItem.groupId
        }

        override fun areContentsTheSame(oldItem: UserGroup, newItem: UserGroup): Boolean {
            return oldItem==newItem
        }

    }
}