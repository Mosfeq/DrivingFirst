package com.mosfeq.drivingfirstgithub.learner

import android.content.Context
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mosfeq.drivingfirstgithub.instructor.Instructor
import com.mosfeq.drivingfirstgithub.R
import com.bumptech.glide.Glide
import com.mosfeq.drivingfirstgithub.databinding.InstructorItemBinding
import de.hdodenhof.circleimageview.CircleImageView

class InstructorAdapter(
    private var instructorList: ArrayList<Instructor>,
    private val listener: ClickListener
): RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder>() {

    interface ClickListener{
        fun onClick(position: Int)
    }

//    var onItemClick: ((instructor: Instructor) -> Unit)? = null

//    private lateinit var clickListener: OnItemClickListener
//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//    }
//
//    fun setOnItemClickListener(listener: OnItemClickListener){
//        clickListener = listener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.instructor_item, parent, false)
        return InstructorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
        val currentItem = instructorList[position]
        holder.firstName.text = currentItem.name
        holder.car.text = currentItem.carType
        holder.age.text = currentItem.age.toString() + "Y"
        holder.price.text = "£" + currentItem.pricePerLesson.toString() + "/H"
        holder.marketingText.text = currentItem.mInformation.toString()
        holder.des.text = currentItem.description
        Glide.with(holder.image).load(instructorList[position].uri)
            .into(holder.image)

//        holder.itemView.setOnClickListener {
//            onItemClick?.invoke(currentItem)
//        }

//        holder.itemView.setOnClickListener {
//            val position: Int = holder.absoluteAdapterPosition
//            listener.onItemClick(position)
//        }
    }

    override fun getItemCount(): Int {
        return instructorList.size
    }

    inner class InstructorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val firstName: TextView = itemView.findViewById(R.id.tvInstructorFirstname)
        val age: TextView = itemView.findViewById(R.id.tvInstructorAge)
        val price: TextView = itemView.findViewById(R.id.price)
        val des: TextView = itemView.findViewById(R.id.description)
        val car: TextView = itemView.findViewById(R.id.cartype)
        val marketingText: TextView = itemView.findViewById(R.id.marketing)
        val image: CircleImageView = itemView.findViewById(R.id.img1)

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                listener.onClick(position)
            }
        }

//        init {
//            itemView.setOnClickListener {
//                listener.onItemClick(bindingAdapterPosition)
//            }
//        }

    }

}


