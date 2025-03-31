package br.com.emribeiro.netflixapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 8
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    }

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}