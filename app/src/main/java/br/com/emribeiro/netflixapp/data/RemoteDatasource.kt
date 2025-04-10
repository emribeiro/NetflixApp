package br.com.emribeiro.netflixapp.data

import android.os.Handler
import android.os.Looper
import android.util.Log
import br.com.emribeiro.netflixapp.model.Category
import br.com.emribeiro.netflixapp.model.Movie
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class RemoteDatasource(private val callback: Callback) {

    val handler = Handler(Looper.getMainLooper())

    interface Callback{
        fun onResult(categories: List<Category>)
        fun onError(message: String)
    }


    fun execute(url: String){
        val executor = Executors.newSingleThreadExecutor()

        executor.execute{
            try {
                val request = URL(url)
                val urlConnection = request.openConnection() as HttpsURLConnection
                urlConnection.readTimeout = 2000
                urlConnection.connectTimeout = 3000

                val statusCode = urlConnection.responseCode
                if(statusCode > 400){
                    throw IOException("Erro ao recuperar dados do servidor. StatusCode: $statusCode")
                }

                val stream = urlConnection.inputStream
                val jsonData = stream.bufferedReader().use {
                    it.readText()
                }
                Log.i("Dados", toCategories(jsonData).toString())

                handler.post {
                    callback.onResult(toCategories(jsonData))
                }

            }catch (e: Exception){
                Log.e("Error", e.message, e)
                handler.post{
                    callback.onError("Erro de Execução")
                }

            }

        }
    }

    private fun toCategories(jsonString: String): List<Category>{
        val categories = mutableListOf<Category>()
        val jsonCategories = JSONObject(jsonString).getJSONArray("category")

        for(i in 0 until jsonCategories.length()){
            val jsonCategory = jsonCategories.getJSONObject(i)
            val id = jsonCategory.getInt("id")
            val title = jsonCategory.getString("title")
            val jsonMovies = jsonCategory.getJSONArray("movie")
            val movies = mutableListOf<Movie>()
            for(j in 0 until jsonMovies.length()){
                val jsonMovie = jsonMovies.getJSONObject(j)
                val movieId = jsonMovie.getInt("id")
                val coverUrl = jsonMovie.getString("cover_url")
                movies.add(Movie(movieId, coverUrl))
            }

            categories.add(Category(id, title, movies))

        }

        return categories
    }
}