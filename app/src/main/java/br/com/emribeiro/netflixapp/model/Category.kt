package br.com.emribeiro.netflixapp.model

data class Category( val id: Int
                   , val title: String
                   , val movies: List<Movie>)
