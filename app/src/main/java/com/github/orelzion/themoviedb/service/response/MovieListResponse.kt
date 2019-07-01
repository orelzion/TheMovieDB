package com.github.orelzion.themoviedb.service.response

import com.github.orelzion.themoviedb.model.MovieDetails

data class MovieListResponse(
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<MovieDetails>
)