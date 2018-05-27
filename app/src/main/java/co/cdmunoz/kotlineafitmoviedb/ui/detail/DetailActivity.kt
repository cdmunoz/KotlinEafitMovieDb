package co.cdmunoz.kotlineafitmoviedb.ui.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import co.cdmunoz.kotlineafitmoviedb.BuildConfig
import co.cdmunoz.kotlineafitmoviedb.R
import co.cdmunoz.kotlineafitmoviedb.data.MovieItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.detail_overview
import kotlinx.android.synthetic.main.activity_detail.detail_poster
import kotlinx.android.synthetic.main.activity_detail.detail_release_date
import kotlinx.android.synthetic.main.activity_detail.detail_title
import kotlinx.android.synthetic.main.activity_detail.detail_vote_average

class DetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)

    val movieItem = intent?.getSerializableExtra("THE_MOVIE") as MovieItem
    updateUI(movieItem)

  }

  private fun updateUI(movieItem: MovieItem) {
    Picasso.with(this)
        .load(BuildConfig.IMG_BASE_URL + movieItem.backdropPath)
        .into(detail_poster)

    detail_title.text = movieItem.title
    detail_overview.text = movieItem.overview
    detail_release_date.text = Html.fromHtml(
        resources.getString(R.string.detail_release_date, movieItem.releaseDate))
    detail_vote_average.text = Html.fromHtml(
        resources.getString(R.string.detail_vote_average, movieItem.voteAverage.toString()))
  }
}
