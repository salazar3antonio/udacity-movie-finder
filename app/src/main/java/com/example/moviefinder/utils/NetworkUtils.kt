package com.example.moviefinder.utils

import android.net.Uri
import com.example.moviefinder.database.model.Movie
import com.example.moviefinder.database.model.Theater
import org.json.JSONObject

fun buildMoviePosterUri(imagePath: String?): Uri {
    val editedImagePath = imagePath?.substring(1)
    return Uri.parse(Constants.TMDB_IMAGE_BASE_URL).buildUpon()
        .appendPath(Constants.POSTER_IMAGE_SIZE)
        .appendPath(editedImagePath)
        .build()
}

fun buildMovieBackDropUri(imagePath: String?): Uri {
    val editedImagePath = imagePath?.substring(1)
    return Uri.parse(Constants.TMDB_IMAGE_BASE_URL).buildUpon()
        .appendPath(Constants.BACKDROP_IMAGE_SIZE)
        .appendPath(editedImagePath)
        .build()
}

fun parseNowPlayingResults(rootJSONObject: JSONObject): ArrayList<Movie> {

    val resultsJSONArray = rootJSONObject.getJSONArray("results")
    val movieList = ArrayList<Movie>()

    for (i in 0 until resultsJSONArray.length()) {
        val movieResult = resultsJSONArray.getJSONObject(i)
        val id = movieResult.getInt("id")
        val poster = movieResult.getString("poster_path")
        val backdrop = movieResult.getString("backdrop_path")
        val title = movieResult.getString("title")
        val overview = movieResult.getString("overview")
        val releaseDate = movieResult.getString("release_date")

        val movie = Movie(id, poster, backdrop, title, overview, releaseDate)
        movieList.add(movie)

    }

    return movieList

}

fun parseTheaterRadiusResults(rootJSONObject: JSONObject): ArrayList<Theater> {

    val resultsJSONArray = rootJSONObject.getJSONArray("searchResults")
    val theaterList = ArrayList<Theater>()

    for (i in 0 until resultsJSONArray.length()) {
        val theaterResult = resultsJSONArray.getJSONObject(i)
        val fieldsResult = theaterResult.getJSONObject("fields")
        val id = fieldsResult.getString("id")
        val name = fieldsResult.getString("name")
        val address = fieldsResult.getString("address")
        val city = fieldsResult.getString("city")
        val lat = fieldsResult.getDouble("lat")
        val lng = fieldsResult.getDouble("lng")
        val postalCode = fieldsResult.getInt("postal_code")
        val state = fieldsResult.getString("state")
        val phone = fieldsResult.getString("phone")

        val fullAddress = "$address, $city, $state $postalCode"

        val theater = Theater(
            id, name, fullAddress, lat, lng, phone
        )

        theaterList.add(theater)

    }

    return theaterList
}
