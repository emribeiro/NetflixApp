package br.com.emribeiro.netflixapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.emribeiro.netflixapp.data.RemoteDatasource
import br.com.emribeiro.netflixapp.model.Category
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

        val toolbar: Toolbar = findViewById(R.id.app_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val datasource = RemoteDatasource()
        datasource.execute("https://atway.tiagoaguiar.co/fenix/netflixapp/home?apiKey=ec0c1b1f-23d4-436f-b91c-0242f2e770ef")

        val categoriesList = mutableListOf<Category>()

        for(k in 1..4){
            val movieList = mutableListOf<Movie>()
            for(i in 1..4){
                movieList.add(Movie(i , "Movie $i"))
            }
            categoriesList.add(Category(k, "Category $k", movieList))
        }


        val recyclerView: RecyclerView = findViewById(R.id.main_rv)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = CategoryAdapter(categoriesList)
    }

}