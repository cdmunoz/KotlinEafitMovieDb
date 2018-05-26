package co.cdmunoz.kotlineafitmoviedb.ui.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import co.cdmunoz.kotlineafitmoviedb.MoviesDbApplication
import co.cdmunoz.kotlineafitmoviedb.data.MovieItem
import co.cdmunoz.kotlineafitmoviedb.data.source.MovieDbRepository
import co.cdmunoz.kotlineafitmoviedb.data.source.local.MoviesDatabase
import co.cdmunoz.kotlineafitmoviedb.data.source.remote.MovieDbApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class MoviesDbViewModel : ViewModel() {

  private val database = MoviesDatabase.getMoviesDbDatabase(MoviesDbApplication.instance)
  private val movieDbRepository: MovieDbRepository = MovieDbRepository(MovieDbApiService.create(),
      database.moviesDbDao())

  private var moviesResult: MutableLiveData<List<MovieItem>> = MutableLiveData()
  fun moviesResult(): LiveData<List<MovieItem>> = moviesResult

  private var moviesLoader: MutableLiveData<Boolean> = MutableLiveData()
  fun moviesLoader(): LiveData<Boolean> = moviesLoader

  lateinit var disposableObserver: DisposableObserver<List<MovieItem>>

  fun loadMovies() {
    disposableObserver = object : DisposableObserver<List<MovieItem>>() {
      override fun onComplete() {
      }

      override fun onNext(movies: List<MovieItem>) {
        val numberOfItems = movies.size.toString()
        Log.i(ListActivity.TAG, "+++++++++++++++++ Size of results: $numberOfItems")
        moviesResult.postValue(movies)
        moviesLoader.postValue(false)
      }

      override fun onError(error: Throwable) {
        Log.e(ListActivity.TAG, "Error getting results: ${error.message}")
        moviesLoader.postValue(false)
      }
    }

    movieDbRepository.getMovies("2018", "df1b9abfde892d0d5407d6b602b349f2")
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(disposableObserver)
  }

  fun disposeElements() {
    if (!disposableObserver.isDisposed) disposableObserver.dispose()
  }
}