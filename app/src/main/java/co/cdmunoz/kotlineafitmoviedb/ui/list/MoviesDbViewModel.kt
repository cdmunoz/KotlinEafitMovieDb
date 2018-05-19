package co.cdmunoz.kotlineafitmoviedb.ui.list

import android.arch.lifecycle.ViewModel
import android.util.Log
import co.cdmunoz.kotlineafitmoviedb.data.MoviesResponse
import co.cdmunoz.kotlineafitmoviedb.data.source.MovieDbRepository
import co.cdmunoz.kotlineafitmoviedb.data.source.remote.MovieDbApiService
import co.cdmunoz.kotlineafitmoviedb.data.source.remote.MovieDbApiService.Factory
import co.cdmunoz.kotlineafitmoviedb.ui.list.ListActivity.Companion
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class MoviesDbViewModel: ViewModel() {

  private val movieDbRepository: MovieDbRepository = MovieDbRepository(MovieDbApiService.create())

  lateinit var disposableObserver: DisposableObserver<MoviesResponse>

  fun loadMovies(){
    disposableObserver = object : DisposableObserver<MoviesResponse>() {
      override fun onComplete() {
      }

      override fun onNext(moviesResponse: MoviesResponse) {
        val numberOfItems = moviesResponse.results?.size.toString()
        Log.i(ListActivity.TAG, "+++++++++++++++++ Size of results: $numberOfItems")
      }

      override fun onError(error: Throwable) {
        Log.e(ListActivity.TAG, "Error getting results: ${error.message}")
      }
    }

    movieDbRepository.getMovies("2018", "df1b9abfde892d0d5407d6b602b349f2")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(disposableObserver)
  }

  fun disposeElements(){
    if(!disposableObserver.isDisposed) disposableObserver.dispose()
  }
}