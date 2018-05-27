package co.cdmunoz.kotlineafitmoviedb.data.source

import android.util.Log
import co.cdmunoz.kotlineafitmoviedb.MoviesDbApplication
import co.cdmunoz.kotlineafitmoviedb.data.MovieItem
import co.cdmunoz.kotlineafitmoviedb.data.source.local.MoviesDbDao
import co.cdmunoz.kotlineafitmoviedb.data.source.remote.MovieDbApiService
import co.cdmunoz.kotlineafitmoviedb.utils.Utilities
import io.reactivex.Observable
import java.util.Calendar


class MovieDbRepository constructor(val apiService: MovieDbApiService,
    val moviesDbDao: MoviesDbDao) {

  private val context = MoviesDbApplication.instance

  fun getMovies(year: String, apiKey: String): Observable<List<MovieItem>> {
    val isConnected = Utilities.isConnectionAvailable(context)
    var observableFromApi: Observable<List<MovieItem>>? = null
    var hasApiData = Utilities.shouldGetDataFromApi(context)
    if (isConnected && hasApiData) {
      observableFromApi = getMoviesFromApi(year, apiKey)
      Utilities.putLongSharedPreferences(context, "pref_time_net_query",
          Calendar.getInstance().timeInMillis)
      hasApiData = true
    }
    val observableFromDb = getMoviesFromDb()
    return if (hasApiData) Observable.concatArrayEager(observableFromApi,
        observableFromDb) else observableFromDb
  }

  private fun getMoviesFromApi(year: String, apiKey: String): Observable<List<MovieItem>> {
    return apiService.getMovies(year, apiKey)
        .flatMap { Observable.just(it.results) }
        .doOnNext {
          Log.e("REPOSITORY API * ", it.size.toString())
          for (item in it) {
            Log.e("REPOSITORY Insert * ", item.title)
            moviesDbDao.insertMovie(item)
          }
        }
        .doOnError { Log.e("API Insert", it.message) }
  }

  fun getMoviesFromDb(): Observable<List<MovieItem>> {
    return moviesDbDao.queryMovies()
        .toObservable()
        .doOnNext {
          Log.e("REPOSITORY DB *** ", it.size.toString())
        }
  }
}