package com.github.orelzion.themoviedb.db

import androidx.room.TypeConverter
import com.github.orelzion.themoviedb.model.MovieCategory
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class TypeConverters {

    private val dateFormat = SimpleDateFormat("yyyy-dd-MM", Locale.ENGLISH)

    @TypeConverter
    fun getMovieCategory(ordinal: Int): MovieCategory {
        return MovieCategory.values()[ordinal]
    }

    @TypeConverter
    fun getMovieCategoryOrdinal(category: MovieCategory): Int {
        return category.ordinal
    }

    @TypeConverter
    fun getIntListAsString(list: List<Int>): String {
        return list.joinToString {"$it"}
    }

    @TypeConverter
    fun getIntListFromString(listStr: String): List<Int> {
        return if (listStr.isEmpty())
            listOf()
        else
            listStr.split(",").map { it.trim() }.map { it.toInt() }
    }

    @ToJson
    @TypeConverter
    fun getDateAsString(date: Date): String {
        return dateFormat.format(date)
    }

    @FromJson
    @TypeConverter
    fun getDateFromString(dateStr: String): Date? {
        return dateFormat.parse(dateStr)
    }
}