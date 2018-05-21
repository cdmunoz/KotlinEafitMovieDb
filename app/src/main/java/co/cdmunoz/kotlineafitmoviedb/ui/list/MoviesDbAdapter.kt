package co.cdmunoz.kotlineafitmoviedb.ui.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.cdmunoz.kotlineafitmoviedb.BuildConfig
import co.cdmunoz.kotlineafitmoviedb.R
import co.cdmunoz.kotlineafitmoviedb.data.ResultsItem
import com.squareup.picasso.Picasso


class MoviesDbAdapter(
    movies: List<ResultsItem>) : RecyclerView.Adapter<MoviesDbAdapter.MoviesDbViewHolder>() {

  private var moviesDbList = ArrayList<ResultsItem>()

  init {
    this.moviesDbList = movies as ArrayList<ResultsItem>
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesDbViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent,
        false)
    return MoviesDbViewHolder(itemView)
  }

  override fun getItemCount(): Int = moviesDbList.size

  override fun onBindViewHolder(holder: MoviesDbViewHolder, position: Int) {
    val movieDbItem = moviesDbList[position]
    holder.movieDbItem(movieDbItem)
  }


  class MoviesDbViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var movieImagePoster = itemView.findViewById<ImageView>(R.id.movie_image_poster)
    var movieTitle = itemView.findViewById<TextView>(R.id.movie_title)
    var movieReleaseDate = itemView.findViewById<TextView>(R.id.movie_release_date)
    var movieVoteAvg = itemView.findViewById<TextView>(R.id.movie_vote_avg)

    fun movieDbItem(resultsItem: ResultsItem) {
      Picasso.with(itemView.context)
          .load(BuildConfig.IMG_BASE_URL + resultsItem.posterPath)
          .into(movieImagePoster)

      movieTitle.text = resultsItem.title
      movieReleaseDate.text = resultsItem.releaseDate
      movieVoteAvg.text = resultsItem.voteAverage.toString()
    }
  }
}