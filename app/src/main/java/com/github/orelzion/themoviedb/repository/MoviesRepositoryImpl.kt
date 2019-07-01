package com.github.orelzion.themoviedb.repository

import androidx.paging.DataSource
import com.github.orelzion.themoviedb.db.MovieDBDao
import com.github.orelzion.themoviedb.db.entity.MovieDetailsEntity
import com.github.orelzion.themoviedb.db.entity.MovieListEntity
import com.github.orelzion.themoviedb.model.MovieCategory
import com.github.orelzion.themoviedb.service.MovieDBService
import com.github.orelzion.themoviedb.service.response.MovieListResponse
import com.github.orelzion.themoviedb.toEntity
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesRepositoryImpl(private val movieDBService: MovieDBService, private val movieDBDao: MovieDBDao) :
    MoviesRepository {
    override fun fetchMovieListFromLocal(): Single<List<MovieListEntity>> {
        return movieDBDao.getMovieList()
    }

    override fun fetchMovieListFromService(page: Int): Single<MovieListResponse> {
        return movieDBService.fetchMovieList(page)
    }

    override fun insertAllMovieDetails(movieDetailsEntity: List<MovieDetailsEntity>): Completable {
        return Completable.fromCallable {
            movieDBDao.insertAll(movieDetailsEntity)
        }
    }

    override fun insertMovieList(movieListEntity: MovieListEntity): Completable {
        return Completable.fromCallable {
            movieDBDao.insertMovieList(movieListEntity)
        }
    }

    override fun getMovieListFromLocal(): DataSource.Factory<Int, MovieDetailsEntity> {
        return movieDBDao.getAllMovies()
    }

    override fun fetchMovieListItems(movieIdList: List<Int>): Single<List<MovieDetailsEntity>> {
        return movieDBDao.getMovieListById(movieIdList)
    }
}