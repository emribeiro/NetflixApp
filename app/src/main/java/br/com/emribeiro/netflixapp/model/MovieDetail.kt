package br.com.emribeiro.netflixapp.model

data class MovieDetail(val movie: Movie, val similars: List<Movie>)