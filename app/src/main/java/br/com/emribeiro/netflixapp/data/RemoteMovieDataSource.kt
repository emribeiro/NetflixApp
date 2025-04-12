package br.com.emribeiro.netflixapp.data

import android.content.res.Resources.NotFoundException
import android.os.Handler
import android.os.Looper
import android.util.Log
import br.com.emribeiro.netflixapp.model.Movie
import br.com.emribeiro.netflixapp.model.MovieDetail
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class RemoteMovieDataSource(private val callback: Callback) {

    val handler = Handler(Looper.getMainLooper())

    interface Callback{
        fun onResult(movieDetail: MovieDetail)
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

                if(statusCode == 404){
                    throw NotFoundException("Recurso não encontrado")
                }

                if(statusCode > 400){
                    throw IOException("Erro ao recuperar dados do servidor. StatusCode: $statusCode")
                }

                val stream = urlConnection.inputStream
                val jsonData = stream.bufferedReader().use {
                    it.readText()
                }

                handler.post {
                    callback.onResult(toMovieDetail(jsonData))
                }

            }catch (e: Exception){
                Log.e("Error", e.message, e)
                handler.post{
                    callback.onError("Erro de Execução")
                }

            }

        }
    }

    private fun toMovieDetail(jsonString: String): MovieDetail{
        val movieDetailJson = JSONObject(jsonString)
        val id = movieDetailJson.getInt("id")
        val cover = movieDetailJson.getString("cover_url")
        val title = movieDetailJson.getString("title")
        val description = movieDetailJson.getString("desc")
        val cast = movieDetailJson.getString("cast")
        val moviesJson = movieDetailJson.getJSONArray("movie")
        val similars = mutableListOf<Movie>()
        for (i in 0 until moviesJson.length()){
          val movie = moviesJson.getJSONObject(i)
          val itemId = movie.getInt("id")
          val coverUrl = movie.getString("cover_url")
          similars.add(Movie(itemId, coverUrl))
        }

        val mubee = Movie(id, cover, title, description, cast)

        return MovieDetail(mubee, similars)

    }
}