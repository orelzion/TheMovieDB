package com.github.orelzion.themoviedb

import com.github.orelzion.themoviedb.db.entity.MovieDetailsEntity
import com.github.orelzion.themoviedb.db.entity.MovieListEntity
import com.github.orelzion.themoviedb.model.MovieDetails
import com.github.orelzion.themoviedb.service.response.MovieListResponse

fun MovieDetails.toEntity() = MovieDetailsEntity(
    null,
    this.id,
    this.title,
    this.vote_count,
    this.vote_average,
    this.popularity,
    this.poster_path,
    this.genre_ids,
    this.overview,
    this.release_date
)

fun MovieListResponse.toEntity() = MovieListEntity(
    id = null, // Keep null so it'll be auto generated
    page = this.page,
    maxPage = this.total_pages,
    movieIdList = this.results.map { it.id }
)