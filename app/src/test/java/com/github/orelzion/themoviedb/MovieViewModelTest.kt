package com.github.orelzion.themoviedb

import com.github.orelzion.themoviedb.repository.MoviesRepositoryImpl
import com.github.orelzion.themoviedb.view_model.MovieViewModel
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.koin.test.mock.declareMock
import org.mockito.Mockito

class MovieViewModelTest : AutoCloseKoinTest() {

    val viewModel: MovieViewModel by inject()

    @Before
    fun before() {
        startKoin {
            modules(appModule)
        }
        declareMock<MovieViewModel>()
        declareMock<MoviesRepositoryImpl>()
    }

    @Test
    fun testMovieList() {
        val genreData = viewModel.repository.getGenre(12)
        Mockito.verify(genreData)
    }
}