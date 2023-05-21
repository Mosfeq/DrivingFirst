package com.mosfeq.drivingfirstgithub.learner

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mosfeq.drivingfirstgithub.Booking
import com.mosfeq.drivingfirstgithub.databinding.BookingPageBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Random

class BookingPage: AppCompatActivity() {

    private lateinit var binding: BookingPageBinding
    private var datePicked = ""
    private var timePicked = ""
    private var datetimepicked = ""
    private lateinit var db: DatabaseReference

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
                    datetimepicked = datePicked + timePicked
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
                    datetimepicked = datePicked + timePicked
                },
                hour,
                minute,
                true
                //Set time to 24 hour, not 12
            )
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }

        binding.booking.setOnClickListener(){
            if(datePicked.isNotEmpty() && timePicked.isNotEmpty()){
                booking()
                Toast.makeText(this,"Lesson Booked",Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this,"Select Date & Time",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun booking(){
        db = FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("booking")
        val i = (Random().nextInt(900000) + 100000).toString()

        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = df.format(c)
        val selectedDate: String = datePicked
        val comparedDate: Int = currentDate.compareTo(selectedDate)

        if (comparedDate > 0) {
            Toast.makeText(this, "Can't select date from past!", Toast.LENGTH_LONG).show()
        }else{

//            Toast.makeText(this, "Booked", Toast.LENGTH_LONG).show()
        }

    }

}