package com.example.influshop

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class PreferenceUtility {
    companion object {
        fun putString(application: Application, key: String, value: String) {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString(key, value)
                apply()
            }
        }

        fun getString(application: Application, key: String, default: String): String {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            return sharedPref.getString(key, default).toString()
        }

        fun putInt(application: Application, key: String, value: Int) {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putInt(key, value)
                apply()
            }
        }

        fun getInt(application: Application, key: String, default: Int): Int {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            return sharedPref.getInt(key, default)
        }

        fun putFloat(application: Application, key: String, value: Float) {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putFloat(key, value)
                apply()
            }
        }

        fun getFloat(application: Application, key: String, default: Float): Float {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            return sharedPref.getFloat(key, default)
        }

        fun putBoolean(application: Application, key: String, value: Boolean) {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putBoolean(key, value)
                apply()
            }
        }

        fun getBoolean(application: Application, key: String, default: Boolean): Boolean {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            return sharedPref.getBoolean(key, default)
        }

        fun putLong(application: Application, key: String, value: Long) {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putLong(key, value)
                apply()
            }
        }

        fun getLong(application: Application, key: String, default: Long): Long {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            return sharedPref.getLong(key, default)
        }

        fun clear(application: Application) {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                clear()
                apply()
            }
        }

        fun putJson(application: Application, key: String, value: Any) {
            val sharedPref =
                application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                val gson = Gson()
                val jsonValue = gson.toJson(value)
                putString(key, jsonValue)
                apply()
            }
        }

        inline fun <reified T> getJson(application: Application, key: String, default: T?): T? {
            val sharedPref = application.getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
            val gson = Gson()
            val jsonValue = sharedPref.getString(key, null)

            if (jsonValue != null) {
                try {
                    return gson.fromJson(jsonValue, T::class.java)
                } catch (e: JsonSyntaxException) {
                    // Handle the case where JSON couldn't be deserialized
                    e.printStackTrace()
                }
            }

            return default
        }

    }
}