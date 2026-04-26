package com.sa.branchlocatormap.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Type converters for Room database.
 *
 * Room does not support complex types like List<String> out of the box.
 * This class converts unsupported types into a storable format (String)
 * and back again when reading from the database.
 *
 * In this case:
 * - List<String> ⇄ JSON String
 */
class Converters {

    private val gson = Gson()

    /**
     * Converts a list of services into a JSON string for storage in Room.
     *
     * @param services List of service names (e.g. ["ATM", "Loans"])
     * @return JSON representation of the list
     */
    @TypeConverter
    fun fromServicesList(services: List<String>): String {
        return gson.toJson(services)
    }

    /**
     * Converts a JSON string back into a list of services.
     *
     * @param data JSON string stored in the database
     * @return Parsed list of service names
     *
     * Behavior:
     * - If parsing fails or data is null/invalid, returns an empty list
     */
    @TypeConverter
    fun toServicesList(data: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return try {
            gson.fromJson<List<String>>(data, type) ?: emptyList()
        } catch (_: Exception) {
            emptyList()
        }
    }
}