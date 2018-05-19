package co.cdmunoz.kotlineafitmoviedb.ui.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.cdmunoz.kotlineafitmoviedb.R.layout

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

  }

  override fun onDestroy() {
    moviesDbViewModel.disposeElements()
    super.onDestroy()
  }
}
