package com.example.moviefinder.api

import com.example.moviefinder.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbAPI {

    @GET(Constants.NOW_PLAYING)
    suspend fun getNowPlaying(
        @Query(Constants.API_KEY_PARAM) apiKey: String,
        @Query(Constants.LANGUAGE_PARAM) language: String,
        @Query(Constants.PAGE_PARAM) pageNum: Int,
        @Query(Constants.REGION_PARAM) regionCode: String
    ): String

}