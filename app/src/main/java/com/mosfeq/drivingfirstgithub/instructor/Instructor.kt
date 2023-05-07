package com.mosfeq.drivingfirstgithub.instructor

data class Instructor(
    var id: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var email: String? = null,
    var phoneNumber: Int? = null,
    var isChecked: Boolean = false
){}
