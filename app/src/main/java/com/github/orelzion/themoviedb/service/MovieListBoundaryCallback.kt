package com.github.orelzion.themoviedb.service

import androidx.paging.PagedList
import com.github.orelzion.themoviedb.db.entity.MovieDetailsEntity
import com.github.orelzion.themoviedb.repository.MoviesRepository
import com.github.orelzion.themoviedb.toEntity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class MovieListBoundaryCallback(private val moviesRepository: MoviesRepository) :
    PagedList.BoundaryCallback<MovieDetailsEntity>() {

    private val executor = Executors.newSingleThreadExecutor()
    private val helper = PagingRequestHelper(executor)

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) { callback ->
            moviesRepository.fetchMovieListFromService(1)
                .flatMapCompletable { response ->
                    Completable.concat(
                        listOf(
                            moviesRepository.insertMovieList(response.toEntity()),
                            moviesRepository.insertAllMovieDetails(response.results.map { it.toEntity() })
                        )
                    )
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { callback.recordSuccess() },
                    { throwable -> callback.recordFailure(throwable) }
                )
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: MovieDetailsEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {callback ->
            moviesRepository.fetchMovieListFromLocal()
                .flatMap { moviesRepository.fetchMovieListFromService(it.last().page + 1) }
                .flatMapCompletable { response ->
                    Completable.concat(
                        listOf(
                            moviesRepository.insertMovieList(response.toEntity()),
                            moviesRepository.insertAllMovieDetails(response.results.map { it.toEntity() })
                        )
                    )
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { callback.recordSuccess() },
                    { throwable -> callback.recordFailure(throwable) }
                )
        }
    }
}