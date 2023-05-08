package com.mosfeq.drivingfirstgithub.instructor

import com.google.type.DateTime
import java.time.LocalDate

data class Instructor(
    var age: Int? = null,
    var email: String? = null,
    var firstname: String? = null,
    var gender: String? = null,
    var lastname: String? = null,
    var marketingText: String? = null,
    var phoneNumber: Long? = null,
    var price: Int? = null,
//    var dob: LocalDate? = null,
    var id: String? = null
)
