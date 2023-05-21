package com.mosfeq.drivingfirstgithub.dataClasses

data class Booking(
    var pricePerLesson: String? = null,
    var email: String? = null,
    var instructorName: String? = null,
    var instructorEmail: String? = null,
    var date: String? = null,
    var time: String? = null,
    var instructorUri: String? = null,

    var learnerName: String? = null,
    var learnerUri: String? = null,

    var dateTime: String? = null,
    var feedback: String? = null,
    var randomKey: String? = null
) {}