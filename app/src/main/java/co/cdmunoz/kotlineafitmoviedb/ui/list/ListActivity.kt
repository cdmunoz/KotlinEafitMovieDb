package co.cdmunoz.kotlineafitmoviedb.ui.list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.cdmunoz.kotlineafitmoviedb.R.layout
import co.cdmunoz.kotlineafitmoviedb.data.ResultsItem
import kotlinx.android.synthetic.main.activity_list.hello_text_view

class ListActivity : AppCompatActivity() {

  companion object {
    val TAG = ListActivity::class.java.name
  }

  lateinit var moviesDbViewModel: MoviesDbViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_list)

    moviesDbViewModel = ViewModelProviders.of(this).get(MoviesDbViewModel::class.java)
    moviesDbViewModel.loadMovies()

    initObservers()

  }

  private fun initObservers() {
    moviesDbViewModel.moviesResult().observe(this, Observer<List<ResultsItem>> {
      if (it != null){
        hello_text_view.text = "Items ::::: ${it.toString()}"
      }
    })
  }

  override fun onDestroy() {
    moviesDbViewModel.disposeElements()
    super.onDestroy()
  }
}
