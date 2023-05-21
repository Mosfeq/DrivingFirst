package com.mosfeq.drivingfirstgithub

import android.content.Context
import android.content.SharedPreferences

object Preference {
    const val PREF_NAME = "PREFERENCES_APP"
    const val MODE = Context.MODE_PRIVATE

    private fun getPreferences(context: Context): SharedPreferences {
        return context
            .getSharedPreferences(PREF_NAME, MODE)
    }
    private fun getEditor(context: Context): SharedPreferences.Editor {
        return getPreferences(context).edit()
    }
    fun readString(context: Context, key: String?): String? {
        return getPreferences(context).getString(key, "")
    }
    fun writeString(context: Context, key: String, value: String?): String {
        getEditor(context).putString(key, value).apply()
        return key
    }
}