package co.cdmunoz.kotlineafitmoviedb.data.source

import android.util.Log
import co.cdmunoz.kotlineafitmoviedb.data.MoviesResponse
import co.cdmunoz.kotlineafitmoviedb.data.source.remote.MovieDbApiService
import io.reactivex.Observable


class MovieDbRepository constructor(val apiService:MovieDbApiService) {

  fun getMovies(year: String, apiKey: String):Observable<MoviesResponse>{
    return getMoviesFromApi(year, apiKey)
  }

  private fun getMoviesFromApi(year: String, apiKey: String): Observable<MoviesResponse> {
    return apiService.getMovies(year, apiKey)
        .doOnNext {
          Log.e("REPOSITORY API * ", it.results?.size.toString())
        }
  }
}