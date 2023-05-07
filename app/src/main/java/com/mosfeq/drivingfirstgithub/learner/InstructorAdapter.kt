package com.mosfeq.drivingfirstgithub.learner

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mosfeq.drivingfirstgithub.instructor.Instructor
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.databinding.InstructorItemBinding

class InstructorAdapter(
    private val instructorList: ArrayList<Instructor>
): RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder>() {

    private lateinit var binding: InstructorItemBinding

    class InstructorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val firstName: TextView = itemView.findViewById(R.id.tvInstructorName)
        val lastName: TextView = itemView.findViewById(R.id.tvInstructorLastname)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.instructor_item, parent, false)
        return InstructorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
        val currentItem = instructorList[position]
        holder.firstName.text = currentItem.firstname
        holder.lastName.text = currentItem.lastname
    }

    override fun getItemCount(): Int {
        return instructorList.size
    }


}