package com.msit.tmdb.core.util

public class Constants {
    companion object {
        const val DATABASE_NAME = "movie_database"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "a219ff7743bdce7f93da60cf587a8097"
        const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMjE5ZmY3NzQzYmRjZTdmOTNkYTYwY2Y1ODdhODA5NyIsInN1YiI6IjVmYzE2YjgwYWM4ZTZiMDA0MDM2OTM4YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.eF-uZgfmTzSapy-aOv3Ms0Yx0HvK_uwS85R-KEExbxE"

        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
        const val YOUTUBE_WEB_URL = "https://www.youtube.com/watch?v="
        const val IMAGE_SIZE_W185 = "w185"
        const val PROFILE_SIZE_W185 = "w185"
        const val BACKDROP_SIZE = "w780"
        const val IMAGE_URL = IMAGE_BASE_URL + IMAGE_SIZE_W185
        const val BACKDROP_URL = IMAGE_BASE_URL + BACKDROP_SIZE
    }
}