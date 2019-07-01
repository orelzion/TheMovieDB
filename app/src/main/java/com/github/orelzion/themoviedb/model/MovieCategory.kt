package com.github.orelzion.themoviedb.model

enum class MovieCategory(private val urlParams: String) {
    MOST_POPULAR("popular"),
    NOW_PLAYING("now_playing"),
    LATEST("latest"),
    TOP_RATED("top_rated");

    override fun toString(): String {
        return urlParams
    }
}