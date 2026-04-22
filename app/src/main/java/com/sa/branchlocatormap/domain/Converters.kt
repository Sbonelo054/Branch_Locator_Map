package com.sa.branchlocatormap.domain

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromServicesList(services: List<String>): String {
        return gson.toJson(services)
    }

    @TypeConverter
    fun toServicesList(data: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, type) ?: emptyList()
    }
}