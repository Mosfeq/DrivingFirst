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
    private val todos: MutableList<Instructor>
): RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder>() {

    private lateinit var binding: InstructorItemBinding

    class InstructorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorViewHolder {
        return InstructorViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.instructor_item,
                parent,
                false
            )
        )
    }

    fun addTodo(instructor: Instructor) {
        todos.add(instructor)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
        val currentTodo = todos[position]
        holder.itemView.apply {
            binding.tvInstructorName.text = currentTodo.firstname
            binding.cbDone.isChecked = currentTodo.isChecked
            toggleStrikeThrough(binding.tvInstructorName, currentTodo.isChecked)
            binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(binding.tvInstructorName, isChecked)
                currentTodo.isChecked = !currentTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }


}