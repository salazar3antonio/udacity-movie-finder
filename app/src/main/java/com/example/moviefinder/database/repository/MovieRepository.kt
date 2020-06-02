package com.example.moviefinder.database.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviefinder.api.TheMovieDbAPI
import com.example.moviefinder.api.TheaterSearchAPI
import com.example.moviefinder.database.dao.MovieDao
import com.example.moviefinder.database.model.Movie
import com.example.moviefinder.database.model.Theater
import com.example.moviefinder.utils.Constants
import com.example.moviefinder.utils.parseNowPlayingResults
import com.example.moviefinder.utils.parseTheaterRadiusResults
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.util.ArrayList

class MovieRepository(private val mMovieDao: MovieDao) {

    private val _theaterSearchResults: MutableLiveData<List<Theater>> by lazy {
        MutableLiveData<List<Theater>>()
    }

    val mAllNowPlayingMovies: LiveData<List<Movie>> = mMovieDao.getAllNowPlayingMovies()
    val mAllWantToWatchMovies: LiveData<List<Movie>> = mMovieDao.getAllWantToWatchMovies()
    val mATheaterSearchResults: LiveData<List<Theater>>
        get() = _theaterSearchResults

    @WorkerThread
    fun updateMovie(movie: Movie) {
        Timber.i("REPOSITORY: %s", movie.toString())
        mMovieDao.updateMovie(movie)
    }

    @WorkerThread
    suspend fun fetchNowPlayingMovies() {

        try {

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.TMDB_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val nowPlayingApi = retrofit.create(TheMovieDbAPI::class.java)
            val rootJSONObject = nowPlayingApi.getNowPlaying(
                Constants.TMDB_API_KEY,
                Constants.LANGUAGE,
                Constants.PAGE_NUM,
                Constants.REGION_CODE
            )

            Timber.i("JSON RESPONSE: %s", rootJSONObject)

            val listOfMovies = parseNowPlayingResults(JSONObject(rootJSONObject))

            Timber.i("LIST OF MOVIES: %s", listOfMovies.toString())

            for (movie in listOfMovies) {
                mMovieDao.insertMovie(movie)
            }

        } catch (cause: Throwable) {
            Timber.i(cause, "ERROR: ")
        }

    }

    @MainThread
    suspend fun fetchTheaterRadiusSearch(origin: String) {

        val theaters: ArrayList<Theater>

        try {

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.MP_SEARCH_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
            val theaterSearchAPI = retrofit.create(TheaterSearchAPI::class.java)

            val rootJSONObject = theaterSearchAPI.getTheaterRadiusSearch(
                origin,
                Constants.RADIUS,
                Constants.MAX_MATCHES,
                Constants.AMBIGUITIES,
                Constants.HOST_DATA,
                Constants.OUT_FORMAT,
                Constants.MP_API_KEY
            )

            theaters = parseTheaterRadiusResults(JSONObject(rootJSONObject))

            Timber.i("THEATERS LIST: %s", theaters.toString())

            _theaterSearchResults.value = theaters

        } catch (cause: Throwable) {
            Timber.i(cause, "ERROR: ")
        }

    }


}