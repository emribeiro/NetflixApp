package br.com.emribeiro.netflixapp.data

import android.util.Log
import java.io.IOException
import java.net.URL
import java.util.concurrent.Executors
import javax.net.ssl.HttpsURLConnection

class RemoteDatasource {

    fun execute(url: String){
        val executor = Executors.newSingleThreadExecutor()

        executor.execute{
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

            Log.i("Dados", jsonData)
        }
    }
}