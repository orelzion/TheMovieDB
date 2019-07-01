package com.github.orelzion.themoviedb.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.orelzion.themoviedb.db.entity.MovieDetailsEntity
import com.github.orelzion.themoviedb.db.entity.MovieListEntity
import com.github.orelzion.themoviedb.model.MovieCategory
import io.reactivex.Single

@Dao
interface MovieDBDao {

    @Query("SELECT * FROM MovieDetailsEntity WHERE id in (:ids) ORDER BY localId")
    fun getMovieListById(ids: List<Int>): Single<List<MovieDetailsEntity>>

    @Query("SELECT * FROM MovieListEntity ORDER BY page")
    fun getMovieList(): Single<List<MovieListEntity>>

    @Query("SELECT * FROM MovieDetailsEntity")
    fun getAllMovies(): DataSource.Factory<Int, MovieDetailsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieDetailsEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movieListEntity: MovieListEntity)
}