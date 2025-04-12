package br.com.emribeiro.netflixapp.model

data class Movie( val id: Int
                , val coverUrl: String
                , val title: String = ""
                , val description: String = ""
                , val cast: String = ""
                )
