package co.cdmunoz.kotlineafitmoviedb.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import co.cdmunoz.kotlineafitmoviedb.R.layout
import co.cdmunoz.kotlineafitmoviedb.data.MovieItem
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {

    companion object {
        val TAG = ListActivity::class.java.name
    }

    private lateinit var moviesDbViewModel: MoviesDbViewModel
    private var moviesDbAdapter = MoviesDbAdapter(ArrayList())
    private lateinit var recyclerViewState: Parcelable
    //private var currentVisiblePositionInList = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_list)

        moviesDbViewModel = ViewModelProviders.of(this).get(MoviesDbViewModel::class.java)

        initRecycler()
        initObservers()
        loadData()
    }

    private fun initRecycler() {
        val gridLayoutManager = GridLayoutManager(this, 1)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        movies_list.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
        }
    }

    private fun initObservers() {
        moviesDbViewModel.moviesResult().observe(this, Observer<List<MovieItem>> {
            if (it != null) {
                recyclerViewState = movies_list.layoutManager.onSaveInstanceState()
                moviesDbAdapter = MoviesDbAdapter(it)
                movies_list.adapter = moviesDbAdapter
                movies_list.layoutManager.onRestoreInstanceState(recyclerViewState)
            }
        })

        moviesDbViewModel.moviesLoader().observe(this, Observer<Boolean> {
            if (it == false) {
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun loadData() {
        moviesDbViewModel.loadMovies()
    }

    override fun onDestroy() {
        moviesDbViewModel.disposeElements()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        recyclerViewState = movies_list.layoutManager.onSaveInstanceState()
        outState?.putParcelable("LIST_STATE", recyclerViewState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState!=null) recyclerViewState = savedInstanceState.getParcelable("LIST_STATE")
        movies_list.layoutManager.onRestoreInstanceState(recyclerViewState)
    }
}
