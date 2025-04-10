package br.com.emribeiro.netflixapp

import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.emribeiro.netflixapp.model.Category
import br.com.emribeiro.netflixapp.model.Movie

class MovieDetailsActivity : AppCompatActivity() {
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

        val layerDrawable : LayerDrawable = ContextCompat.getDrawable(this, R.drawable.shadows) as LayerDrawable
        val movieCover = ContextCompat.getDrawable(this, R.drawable.movie_4)
        layerDrawable.setDrawableByLayerId(R.id.image_cover, movieCover)
        val coverImg: ImageView = findViewById(R.id.movie_cover)
        coverImg.setImageDrawable(layerDrawable)

        val movieTitle: TextView = findViewById(R.id.movie_title)
        val movieDescription: TextView = findViewById(R.id.movie_description)
        val movieCast: TextView = findViewById(R.id.movie_cast)

        movieTitle.text = "Batman: O Cavaleiro das Trevas Ressurge"
        movieDescription.text = "Passaram-se oito anos desde a morte de Harvey Dent e o Batman, ao assumir a culpa pela morte do promotor, desapareceu. Agora, ele terá de lidar com a chegada da Mulher Gato e de Bane em Gotham City."
        movieCast.text = "Christian Bale, Tom Hardy, Anne Hathaway e outros"
        val movieList = mutableListOf<Movie>()
        for(k in 1..12){
            movieList.add(Movie(k, "Movie $k"))
        }

        val similarOptionsRv: RecyclerView = findViewById(R.id.other_options_rv)
        similarOptionsRv.layoutManager = GridLayoutManager(this, 4)
        similarOptionsRv.adapter = MovieRecyclerViewAdapter(movieList)

    }
}