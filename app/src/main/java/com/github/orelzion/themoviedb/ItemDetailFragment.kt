package com.github.orelzion.themoviedb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.orelzion.themoviedb.db.entity.MovieDetailsEntity
import com.github.orelzion.themoviedb.view_model.MovieViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    private val movieViewModel: MovieViewModel by viewModel()

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: MovieDetailsEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.

                movieViewModel.repository.fetchMovieListItems(listOf(it.getInt(ARG_ITEM_ID)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { movieDetails, throwable ->
                        if (!movieDetails.isNullOrEmpty()) {
                            item = movieDetails.first()
                            loadMovieDetails()
                        }
                        if (throwable != null) {
                            Log.e(ItemDetailFragment::class.java.simpleName, "Failed to load item", throwable)
                        }
                    }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_detail, container, false)
    }

    private fun loadMovieDetails() {
        item?.apply {
            activity?.toolbar_layout?.title = title
            movieTitle.text = title
            movieDetails.text = overview
            Glide.with(this@ItemDetailFragment)
                .load(DatasourceProperties.IMAGE_BASE_URL + poster_path)
                .into(movieImage)
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
