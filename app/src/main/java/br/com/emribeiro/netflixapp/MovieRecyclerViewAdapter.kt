package br.com.emribeiro.netflixapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.emribeiro.netflixapp.model.Movie
import com.bumptech.glide.Glide

class MovieRecyclerViewAdapter( private val movies: List<Movie>
                              , private val onClickItemListener: ((Int) -> Unit)?
                              ): RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>(){

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
            val image: ImageView = itemView.findViewById(R.id.movie_image)
            image.contentDescription = movie.coverUrl
//            image.setImageResource(R.drawable.placeholder_bg)
            Glide.with(itemView)
                .load(movie.coverUrl)
                .placeholder(R.drawable.placeholder_bg)
                .into(image)
            image.setOnClickListener{
                onClickItemListener?.invoke(movie.id)
            }
        }
    }
}