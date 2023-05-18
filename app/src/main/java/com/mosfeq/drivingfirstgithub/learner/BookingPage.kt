package com.mosfeq.drivingfirstgithub.learner

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mosfeq.drivingfirstgithub.databinding.BookingPageBinding
import java.text.ParseException
import java.util.Calendar

class BookingPage: AppCompatActivity() {

    private lateinit var binding: BookingPageBinding
    private var datePicked = ""
    private var timePicked = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.instructorName.text = "Instructor Name: ${intent.getStringExtra("name")}"
        binding.instructorPrice.text = "Price Per Hour: £${intent.getStringExtra("price")}"

        binding.setDate.setOnClickListener() {
            val calender = Calendar.getInstance()
            val mYear = calender[Calendar.YEAR]
            val mMonth = calender[Calendar.MONTH]
            val mDay = calender[Calendar.DAY_OF_MONTH]

            val mDateSetListener =
                OnDateSetListener { view, year, month, day ->
                    datePicked = day.toString() + " " + (month + 1).toString() + ", " + year.toString()
                    Toast.makeText(this, "date $datePicked", Toast.LENGTH_SHORT).show()
                    binding.dateTime.text = "$datePicked - $timePicked"
                    try {
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }
            val d = DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay)
            d.show()
        }

        binding.setTime.setOnClickListener() {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]

            val mTimePicker: TimePickerDialog = TimePickerDialog(
                this@BookingPage,
                { timePicker, hour, minute ->
                    timePicked = ("$hour:$minute")
                    binding.dateTime.text = "$datePicked - $timePicked"
                },
                hour,
                minute,
                true
            ) //Yes 24 hour time
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }
    }

}