package com.mosfeq.drivingfirstgithub.learner.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mosfeq.drivingfirstgithub.dataClasses.Instructor
import com.mosfeq.drivingfirstgithub.R
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class InstructorAdapter(
    private var instructorList: ArrayList<Instructor>,
    private val listener: ClickListener
): RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder>() {

    interface ClickListener{
        fun onClick(position: Int)
    }

    fun mfilterList(filteredList: ArrayList<Instructor>) {
        instructorList = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.instructor_item, parent, false)
        return InstructorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
        val currentItem = instructorList[position]
        holder.name.text = currentItem.name
        holder.location.text = currentItem.location
        holder.gender.text = currentItem.gender
        holder.price.text = "£" + currentItem.pricePerLesson.toString() + "/H"
        holder.description.text = currentItem.description
        holder.transmission.text = currentItem.transmission
        Glide.with(holder.image).load(instructorList[position].uri)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return instructorList.size
    }

    inner class InstructorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.tvInstructorFirstname)
        val location: TextView = itemView.findViewById(R.id.tvLocation)
        val gender: TextView = itemView.findViewById(R.id.gender)
        val price: TextView = itemView.findViewById(R.id.price)
        val description: TextView = itemView.findViewById(R.id.description)
        val transmission: TextView = itemView.findViewById(R.id.transmission)
        val image: CircleImageView = itemView.findViewById(R.id.img1)

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                listener.onClick(position)
            }
        }
    }
}


