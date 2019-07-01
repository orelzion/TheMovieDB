package com.github.orelzion.themoviedb.model

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class MovieDetails(
    val id: Int,
    val title: String,
    val vote_count: Long,
    val vote_average: Double,
    val popularity: Double,
    val poster_path: String?,
    val genre_ids: List<Int>,
    val overview: String,
    val release_date: Date)