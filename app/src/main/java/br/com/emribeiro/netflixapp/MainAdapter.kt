package br.com.emribeiro.netflixapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.emribeiro.netflixapp.model.Movie

class RecyclerViewAdapter(private val movies: List<Movie>): RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(movie: Movie){
            val text: TextView = itemView.findViewById(R.id.list_item_text)
            text.text = movie.name
        }
    }
}