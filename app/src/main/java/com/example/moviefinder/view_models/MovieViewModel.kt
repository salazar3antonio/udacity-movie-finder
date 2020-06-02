package com.example.moviefinder.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.moviefinder.database.MoviesDatabase
import com.example.moviefinder.database.model.Movie
import com.example.moviefinder.database.model.Theater
import com.example.moviefinder.database.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    private val mMovieRepository: MovieRepository
    val mAllNowPlayingMovies: LiveData<List<Movie>>
    val mAllWantToWatchMovies: LiveData<List<Movie>>
    val mTheaterSearchResults: LiveData<List<Theater>>


    init {
        val movieDao = MoviesDatabase.getDatabase(application).movieDao()
        mMovieRepository = MovieRepository(movieDao)
        mAllNowPlayingMovies = mMovieRepository.mAllNowPlayingMovies
        mAllWantToWatchMovies = mMovieRepository.mAllWantToWatchMovies
        mTheaterSearchResults = mMovieRepository.mATheaterSearchResults
    }

    fun update(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        mMovieRepository.updateMovie(movie)
    }

    fun callNowPlayingAPI() = viewModelScope.launch(Dispatchers.IO) {
        mMovieRepository.fetchNowPlayingMovies()
    }

    fun callTheaterSearchAPI(zipCode: String) = viewModelScope.launch(Dispatchers.Main) {
        mMovieRepository.fetchTheaterRadiusSearch(zipCode)
    }
}