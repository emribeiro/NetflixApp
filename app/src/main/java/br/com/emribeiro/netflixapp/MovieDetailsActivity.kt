package br.com.emribeiro.netflixapp

import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.emribeiro.netflixapp.data.RemoteMovieDataSource
import br.com.emribeiro.netflixapp.model.Category
import br.com.emribeiro.netflixapp.model.Movie
import br.com.emribeiro.netflixapp.model.MovieDetail

class MovieDetailsActivity : AppCompatActivity(), RemoteMovieDataSource.Callback {

    lateinit var movieTitle: TextView
    lateinit var movieDescription: TextView
    lateinit var movieCast: TextView
    lateinit var adapter: MovieRecyclerViewAdapter
    val movieList =  mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = findViewById(R.id.app_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent?.getIntExtra("id", 0) ?: throw IllegalArgumentException("ID não encontrado")

        val url = "https://atway.tiagoaguiar.co/fenix/netflixapp/movie/$id?apiKey=ec0c1b1f-23d4-436f-b91c-0242f2e770ef"
        val datasource = RemoteMovieDataSource(this)
        datasource.execute(url)

        val layerDrawable : LayerDrawable = ContextCompat.getDrawable(this, R.drawable.shadows) as LayerDrawable
        val movieCover = ContextCompat.getDrawable(this, R.drawable.movie_4)
        layerDrawable.setDrawableByLayerId(R.id.image_cover, movieCover)
        val coverImg: ImageView = findViewById(R.id.movie_cover)
        coverImg.setImageDrawable(layerDrawable)

        movieTitle= findViewById(R.id.movie_title)
        movieDescription = findViewById(R.id.movie_description)
        movieCast = findViewById(R.id.movie_cast)

        adapter = MovieRecyclerViewAdapter(movieList){ id ->
            val intent = Intent(this@MovieDetailsActivity
                , MovieDetailsActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }

        val similarOptionsRv: RecyclerView = findViewById(R.id.other_options_rv)
        similarOptionsRv.layoutManager = GridLayoutManager(this, 4)
        similarOptionsRv.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResult(movieDetail: MovieDetail) {
        movieTitle.text = movieDetail.movie.title
        movieDescription.text = movieDetail.movie.description
        movieCast.text = movieDetail.movie.cast

        movieList.clear()
        movieList.addAll(movieDetail.similars)
        adapter.notifyDataSetChanged()
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}