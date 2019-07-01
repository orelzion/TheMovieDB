package com.github.orelzion.themoviedb.view_model

import androidx.lifecycle.ViewModel
import com.github.orelzion.themoviedb.repository.MoviesRepository

class MovieViewModel(val repository: MoviesRepository) : ViewModel()