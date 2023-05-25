package com.mosfeq.drivingfirstgithub.learner

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mosfeq.drivingfirstgithub.Preference
import com.mosfeq.drivingfirstgithub.dataClasses.Booking
import com.mosfeq.drivingfirstgithub.databinding.BookingPageBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.Random

class BookingPage: AppCompatActivity() {

    private lateinit var binding: BookingPageBinding
    private var datePicked = ""
    private var timePicked = ""
    private var datetimepicked = ""
    private var userEmail = ""
    private var userEmailID = ""
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        intent = intent

        binding.instructorName.text = "Instructor Name: ${intent.getStringExtra("instructorName")}"
        binding.instructorPrice.text = "Price Per Hour: £${intent.getStringExtra("price")}"

        userEmailID = Preference.readString(this@BookingPage, "email").toString().replace(".", "%").trim()
        userEmail = Preference.readString(this@BookingPage, "email").toString()

        binding.setDate.setOnClickListener() {
            val calender = Calendar.getInstance()
            val mYear = calender[Calendar.YEAR]
            val mMonth = calender[Calendar.MONTH]
            val mDay = calender[Calendar.DAY_OF_MONTH]

            val mDateSetListener = OnDateSetListener { view, year, month, day ->
                datePicked = (month + 1).toString() + " " + day + ", " + year
//                    Toast.makeText(this, "date $datePicked", Toast.LENGTH_SHORT).show()
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

        db = FirebaseDatabase.getInstance("https://driving-first-github-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("booking")

        binding.booking.setOnClickListener(){
            if (datePicked.isEmpty() && timePicked.isEmpty()){
                Toast.makeText(this,"Select Date and Time", Toast.LENGTH_SHORT).show()
            } else if(datePicked.isEmpty() || timePicked.isEmpty()){
                Toast.makeText(this,"Select Date and Time", Toast.LENGTH_SHORT).show()
            } else{
                val calendar = Calendar.getInstance().time
                Log.e("New Test", "Time: $calendar")
                val df = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                val currentDate = df.format(calendar)
                val selectedDate: String = newDateFormat(datePicked)
                booking(currentDate, selectedDate)
            }
        }
    }
    fun booking(currentDate: String, selectedDate: String): Int{
        val bookingID = (Random().nextInt(900000) + 100000).toString()
        val comparedDate: Int = currentDate.compareTo(selectedDate)

        if (comparedDate > 0) {
            //Current date is ahead of selected date
            Toast.makeText(this, "Can't select date from past!", Toast.LENGTH_LONG).show()
            return 1

        }
        else if (comparedDate == 0){
            // Both dates are equal
            if (datePicked.isNotEmpty() && timePicked.isNotEmpty()) {
                db.child(userEmailID).child(bookingID).setValue(
                    Booking(
                        intent.getStringExtra("price").toString(),
                        userEmail,
                        intent.getStringExtra("instName").toString(),
                        intent.getStringExtra("instructor").toString(),
                        datePicked,
                        timePicked,
                        intent.getStringExtra("instUri").toString(),
                        intent.getStringExtra("lname").toString(),
                        intent.getStringExtra("luri").toString(),
                        datetimepicked,
                        "",
                        bookingID
                    )
                )
                Toast.makeText(this, "Lesson Booked", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Date or Time required!", Toast.LENGTH_LONG).show()
            }
            return 0
        }
        //Current date is behind of selected date
        if (datePicked.isNotEmpty() && timePicked.isNotEmpty()) {
            db.child(userEmailID).child(bookingID).setValue(
                Booking(
                    intent.getStringExtra("price").toString(),
                    userEmail,
                    intent.getStringExtra("instructorName").toString(),
                    intent.getStringExtra("instructorEmail").toString(),
                    datePicked,
                    timePicked,
                    intent.getStringExtra("instructorUri").toString(),
                    intent.getStringExtra("learnerName").toString(),
                    intent.getStringExtra("learnerUri").toString(),
                    datetimepicked,
                    "",
                    bookingID
                )
            )
            Toast.makeText(this, "Lesson Booked", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Date or Time required!", Toast.LENGTH_LONG).show()
        }
        return -1
    }
    fun newDateFormat(datePicked: String): String {
        val currentDateFormat = SimpleDateFormat("MM dd, yyyy")
        val date: Date = currentDateFormat.parse(datePicked)
        val newDateFormat = SimpleDateFormat("yyyy/MM/dd")
        val newDate: String = newDateFormat.format(date)
        return newDate
    }

}