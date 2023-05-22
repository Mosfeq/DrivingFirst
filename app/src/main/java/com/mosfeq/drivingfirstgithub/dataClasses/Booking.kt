package com.mosfeq.drivingfirstgithub.dataClasses

data class Booking(
    var pricePerLesson: String? = null,
    var email: String? = null,
    var instructorName: String? = null,
    var emailInstructor: String? = null,
    var date: String? = null,
    var time: String? = null,
    var uriInstructor: String? = null,

    var learnName: String? = null,
    var uriLearn: String? = null,

    var dateTime: String? = null,
    var feedback: String? = null,
    var randomKey: String? = null
) {}