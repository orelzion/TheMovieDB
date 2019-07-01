package com.github.orelzion.themoviedb.repository

import androidx.paging.DataSource
import com.github.orelzion.themoviedb.db.entity.MovieDetailsEntity
import com.github.orelzion.themoviedb.db.entity.MovieListEntity
import com.github.orelzion.themoviedb.model.MovieCategory
import com.github.orelzion.themoviedb.service.response.MovieListResponse
import io.reactivex.Completable
import io.reactivex.Single

interface MoviesRepository {
    fun getMovieListFromLocal(): DataSource.Factory<Int, MovieDetailsEntity>
    fun fetchMovieListFromLocal(): Single<List<MovieListEntity>>
    fun fetchMovieListFromService(page: Int): Single<MovieListResponse>
    fun insertAllMovieDetails(movieDetailsEntity: List<MovieDetailsEntity>): Completable
    fun insertMovieList(movieListEntity: MovieListEntity): Completable
    fun fetchMovieListItems(movieIdList: List<Int>): Single<List<MovieDetailsEntity>>
}