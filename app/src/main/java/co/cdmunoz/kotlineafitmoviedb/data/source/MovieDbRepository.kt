package co.cdmunoz.kotlineafitmoviedb.data.source

import android.util.Log
import co.cdmunoz.kotlineafitmoviedb.data.MovieItem
import co.cdmunoz.kotlineafitmoviedb.data.source.local.MoviesDbDao
import co.cdmunoz.kotlineafitmoviedb.data.source.remote.MovieDbApiService
import io.reactivex.Observable


class MovieDbRepository constructor(val apiService:MovieDbApiService, val moviesDbDao: MoviesDbDao) {

  fun getMovies(year: String, apiKey: String):Observable<List<MovieItem>>{
    val observableFromApi = getMoviesFromApi(year, apiKey)
    val observableFromDb = getMoviesFromDb()
    return Observable.concatArrayEager(observableFromApi, observableFromDb)
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

  fun getMoviesFromDb(): Observable<List<MovieItem>>{
    return moviesDbDao.queryMovies()
        .toObservable()
        .doOnNext {
          Log.e("REPOSITORY DB *** ", it.size.toString())
        }
  }
}