package co.cdmunoz.kotlineafitmoviedb.data.source.remote

import co.cdmunoz.kotlineafitmoviedb.BuildConfig
import co.cdmunoz.kotlineafitmoviedb.data.MoviesResponse
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieDbApiService {

  @GET("discover/movie")
  fun getMovies(@Query("year") movieYear: String, @Query("api_key") api_key: String): Observable<MoviesResponse>

  companion object Factory {
    fun create(): MovieDbApiService {
      val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
      val retrofit = Retrofit.Builder()
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(MoshiConverterFactory.create(moshi))
          .baseUrl(BuildConfig.BASE_URL)
          .build()
      return retrofit.create(MovieDbApiService::class.java)
    }
  }
}