package co.cdmunoz.kotlineafitmoviedb.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.util.Calendar
import java.util.concurrent.TimeUnit


class Utilities {

  companion object {
    fun isConnectionAvailable(context: Context): Boolean {
      val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
      return activeNetwork?.isConnectedOrConnecting == true
    }

    fun putLongSharedPreferences(context: Context, longPrefName: String, longValue: Long) {
      val preferences: SharedPreferences = context.getSharedPreferences("MoviesDbPreferences", 0)
      val editor = preferences.edit()

      editor.putLong(longPrefName, longValue)
      editor.apply()
    }

    fun getLongSharedPreferences(context: Context, longPrefName: String): Long {
      val preferences: SharedPreferences = context.getSharedPreferences("MoviesDbPreferences",
                                                                              Context.MODE_PRIVATE)
      //first time is current time minus 5 minutes
      return preferences.getLong(longPrefName, Calendar.getInstance().timeInMillis - 5000 * 60)
    }

    fun convertMillisecondsToMinutes(milliseconds: Long): Long {
      return TimeUnit.MILLISECONDS.toMinutes(milliseconds)
    }

    //return true if it's been more than 5 minutes from the last
    fun shouldGetDataFromApi(context: Context): Boolean {
      val timeLastQueryFromApi: Long = Utilities.getLongSharedPreferences(context,
          "pref_time_net_query")
      val minutesFromLastApiQuery = Utilities.convertMillisecondsToMinutes(timeLastQueryFromApi)
      val currentTime = Utilities.convertMillisecondsToMinutes(Calendar.getInstance().timeInMillis)
      return currentTime - minutesFromLastApiQuery >= 5
    }

  }
}