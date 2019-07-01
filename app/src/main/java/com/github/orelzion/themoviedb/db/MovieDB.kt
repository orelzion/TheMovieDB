package com.github.orelzion.themoviedb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.orelzion.themoviedb.db.entity.GenreEntity
import com.github.orelzion.themoviedb.db.entity.MovieDetailsEntity
import com.github.orelzion.themoviedb.db.entity.MovieListEntity
import com.github.orelzion.themoviedb.model.MovieCategory

@Database(entities = [MovieDetailsEntity::class, MovieListEntity::class, GenreEntity::class], version = 1)
@TypeConverters(com.github.orelzion.themoviedb.db.TypeConverters::class)
abstract class MovieDB : RoomDatabase() {
    abstract fun movieDAO(): MovieDBDao
}