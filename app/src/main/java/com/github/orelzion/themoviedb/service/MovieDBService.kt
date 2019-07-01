package com.github.orelzion.themoviedb.service

import com.github.orelzion.themoviedb.service.response.MovieListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBService {
    @GET("movie/top_rated")
    fun fetchMovieList(@Query("page") page: Int): Single<MovieListResponse>
}