package com.mosfeq.drivingfirstgithub.instructor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mosfeq.drivingfirstgithub.R
import com.mosfeq.drivingfirstgithub.dataClasses.Booking
import de.hdodenhof.circleimageview.CircleImageView

class TimetableInstructorAdapter(
    private var timeLineList: ArrayList<Booking>, private val listener: TimetableFragment
) : RecyclerView.Adapter<TimetableInstructorAdapter.InstructorViewHolder>() {

    interface ClickListener {
        fun onClick(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.timeline_item, parent, false)
        return InstructorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
        val currentItem = timeLineList[position]
        holder.name.text = currentItem.learnName
        holder.time.text = currentItem.time
        holder.date.text = currentItem.date
        if (currentItem.feedback !="") {
            holder.fd.text = "Feedback" + "\n" + currentItem.feedback.toString()
        }
        holder.price.text = "£" + currentItem.pricePerLesson.toString() + "/H"
        Glide.with(holder.image).load(timeLineList[position].uriLearn).into(holder.image)

    }

    override fun getItemCount(): Int {
        return timeLineList.size
    }

    inner class InstructorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //        val lastName: TextView = itemView.findViewById(R.id.tvInstructorLastname)
        val time: TextView = itemView.findViewById(R.id.time)
        val date: TextView = itemView.findViewById(R.id.date)
        val name: TextView = itemView.findViewById(R.id.name)
        val price: TextView = itemView.findViewById(R.id.price)
        val fd: TextView = itemView.findViewById(R.id.feedback)

        val image: CircleImageView = itemView.findViewById(R.id.img1)

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                listener.onClick(position)
            }
        }

    }

}