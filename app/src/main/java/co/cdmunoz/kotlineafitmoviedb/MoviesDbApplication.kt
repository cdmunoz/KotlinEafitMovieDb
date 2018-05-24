package co.cdmunoz.kotlineafitmoviedb

import android.app.Application
import android.arch.persistence.room.Room
import co.cdmunoz.kotlineafitmoviedb.data.source.local.MoviesDatabase
import co.cdmunoz.kotlineafitmoviedb.data.source.local.MoviesDbDao


class MoviesDbApplication: Application() {

  companion object {
    lateinit var moviesDbDao: MoviesDbDao
  }

  override fun onCreate() {
    super.onCreate()
    moviesDbDao = createDatabase().moviesDbDao()
  }

  private fun createDatabase(): MoviesDatabase =
    Room.databaseBuilder(this, MoviesDatabase::class.java, "movies_db").build()

}