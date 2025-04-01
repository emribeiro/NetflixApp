package br.com.emribeiro.netflixapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.emribeiro.netflixapp.model.Category


class CategoryAdapter(private val categories: List<Category>):
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_movie_list, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(category: Category){
            val title: TextView = itemView.findViewById(R.id.category_name)
            title.text = category.name
            val moviesRecyclerView: RecyclerView = itemView.findViewById(R.id.movies_rv)
            moviesRecyclerView.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
            moviesRecyclerView.adapter = MovieRecyclerViewAdapter(category.movies)
        }
    }


}