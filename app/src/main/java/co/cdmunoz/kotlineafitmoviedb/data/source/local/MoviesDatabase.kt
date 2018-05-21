package co.cdmunoz.kotlineafitmoviedb.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import co.cdmunoz.kotlineafitmoviedb.data.MovieItem
import co.cdmunoz.kotlineafitmoviedb.data.source.DataConverter

@Database(entities = arrayOf(MovieItem::class), version = 1)
@TypeConverters(DataConverter::class)
abstract class MoviesDatabase: RoomDatabase() {
  abstract fun moviesDbDao(): MoviesDbDao
}