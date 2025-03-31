package br.com.emribeiro.netflixapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.emribeiro.netflixapp.model.Movie

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val movieList = mutableListOf<Movie>()

        for(i in 1..4){
            movieList.add(Movie("Movie $i", R.drawable.movie_4))
        }

        val recyclerView: RecyclerView = findViewById(R.id.main_rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(movieList)
    }



}