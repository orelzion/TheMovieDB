package com.github.orelzion.themoviedb.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieVideo(val id: String, val name: String, val site: String, val key: String)