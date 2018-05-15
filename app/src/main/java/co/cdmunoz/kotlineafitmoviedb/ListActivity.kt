package co.cdmunoz.kotlineafitmoviedb

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.cdmunoz.kotlineafitmoviedb.api.MovieDbApiService
import co.cdmunoz.kotlineafitmoviedb.api.MoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.hello_text_view

class ListActivity : AppCompatActivity() {

  companion object {
    val TAG = ListActivity::class.java.name
  }

  lateinit var disposableObserver: DisposableObserver<MoviesResponse>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_list)

    val apiService = MovieDbApiService.create()

    disposableObserver = object : DisposableObserver<MoviesResponse>() {
      override fun onComplete() {
      }

      override fun onNext(moviesResponse: MoviesResponse) {
        val numberOfItems = moviesResponse.results?.size.toString()
        Log.i(TAG, "+++++++++++++++++ Size of results: $numberOfItems")
        hello_text_view.text = "Items: ${moviesResponse.results?.toString()}"
      }

      override fun onError(error: Throwable) {
        Log.e(TAG, "Error getting results: ${error.message}")
      }
    }

    apiService.getMovies("2018", "df1b9abfde892d0d5407d6b602b349f2")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(disposableObserver)
  }

  override fun onDestroy() {
    disposableObserver.dispose()
    super.onDestroy()
  }
}
