package co.cdmunoz.kotlineafitmoviedb.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import co.cdmunoz.kotlineafitmoviedb.data.MovieItem
import io.reactivex.Single

@Dao
interface MoviesDbDao {

  @Query("SELECT * FROM movies ORDER BY vote_average DESC")
  fun queryMovies(): Single<List<MovieItem>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertMovie(movieItem: MovieItem)

}