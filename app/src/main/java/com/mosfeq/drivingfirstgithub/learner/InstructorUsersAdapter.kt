package com.mosfeq.drivingfirstgithub.learner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.instructor.Instructor

class InstructorUsersAdapter(
    val instructorList_Messaging: ArrayList<Instructor>
): RecyclerView.Adapter<InstructorUsersAdapter.InstructorUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorUserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.instructor_item_messaging, parent, false)
        return InstructorUserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InstructorUserViewHolder, position: Int) {
        val currentInstructor = instructorList_Messaging[position]

        holder.name.text = currentInstructor.firstname
    }

    override fun getItemCount(): Int {
        return instructorList_Messaging.size
    }

    class InstructorUserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.tvInstructorName_Messaging)
    }
}