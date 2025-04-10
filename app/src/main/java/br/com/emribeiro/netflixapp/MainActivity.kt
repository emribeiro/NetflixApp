package br.com.emribeiro.netflixapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.emribeiro.netflixapp.data.RemoteDatasource
import br.com.emribeiro.netflixapp.model.Category

class MainActivity : AppCompatActivity(), RemoteDatasource.Callback {

    val categoriesList = mutableListOf<Category>()
    lateinit var categoryAdapter: CategoryAdapter

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
        val datasource = RemoteDatasource(this)
        datasource.execute("https://atway.tiagoaguiar.co/fenix/netflixapp/home?apiKey=ec0c1b1f-23d4-436f-b91c-0242f2e770ef")

        val recyclerView: RecyclerView = findViewById(R.id.main_rv)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        categoryAdapter = CategoryAdapter(categoriesList)
        recyclerView.adapter = categoryAdapter
    }

    override fun onResult(categories: List<Category>) {
        Log.i("MainDados", categories.toString())
        categoriesList.clear()
        categoriesList.addAll(categories)
        categoryAdapter.notifyDataSetChanged()
    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

}