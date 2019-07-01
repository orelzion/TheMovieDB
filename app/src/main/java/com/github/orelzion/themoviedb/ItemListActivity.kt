package com.github.orelzion.themoviedb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.orelzion.themoviedb.db.entity.MovieDetailsEntity
import com.github.orelzion.themoviedb.service.MovieListBoundaryCallback
import com.github.orelzion.themoviedb.view_model.MovieViewModel
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private val movieViewModel: MovieViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        item_list.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        item_list.adapter = SimpleItemRecyclerViewAdapter(this, twoPane)

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(10)
            .build()

        val liveData = initializedPagedListBuilder(pagedListConfig).build()

        liveData.observe(this, Observer<PagedList<MovieDetailsEntity>> { pagedList ->
            (item_list.adapter as SimpleItemRecyclerViewAdapter).submitList(pagedList)
        })
    }

    private fun initializedPagedListBuilder(pagedListConfig: PagedList.Config): LivePagedListBuilder<Int, MovieDetailsEntity> {
        val livePagedListBuilder = LivePagedListBuilder(movieViewModel.repository.getMovieListFromLocal(), pagedListConfig)
        livePagedListBuilder.setBoundaryCallback(MovieListBoundaryCallback(movieViewModel.repository))
        return livePagedListBuilder
    }


    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: ItemListActivity,
        private val twoPane: Boolean
    ) :
        PagedListAdapter<MovieDetailsEntity, SimpleItemRecyclerViewAdapter.ViewHolder>(MovieDetailsDiffUtil()) {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as MovieDetailsEntity
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putInt(ItemDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = getItem(position)
            holder.movieTitleView.text = item?.title

            item?.poster_path?.let {
                Glide.with(holder.movieImage.context)
                    .load(DatasourceProperties.IMAGE_BASE_URL + it)
                    .into(holder.movieImage)
            }

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val movieTitleView: TextView = view.movieTitle
            val movieImage: ImageView = view.movieImage
        }
    }
}
