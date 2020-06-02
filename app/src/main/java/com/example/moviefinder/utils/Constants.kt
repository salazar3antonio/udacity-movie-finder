package com.example.moviefinder.utils

object Constants {


    //API KEY from https://www.themoviedb.org/documentation/api
    const val TMDB_API_KEY = "PLACE_API_KEY_HERE"
    //API KEY from https://developer.mapquest.com/
    const val MP_API_KEY = "PLACE_API_KEY_HERE"

    //Base URLs
    const val TMDB_BASE_URL: String = "https://api.themoviedb.org/3/"
    const val TMDB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/"
    const val MP_SEARCH_BASE_URL = "https://www.mapquestapi.com/search/v2/"

    //API Libraries
    const val NOW_PLAYING = "movie/now_playing"
    const val THEATER_RADIUS_SEARCH = "radius"

    //URL Parameter Labels
    //TMDB Labels
    const val API_KEY_PARAM: String = "api_key"
    const val LANGUAGE_PARAM = "language"
    const val PAGE_PARAM = "page"
    const val REGION_PARAM = "region"
    //MapQuest Labels
    const val ORIGIN_PARAM = "origin"
    const val RADIUS_PARAM = "radius"
    const val MAX_MATCHES_PARAM = "maxMatches"
    const val AMBIGUITIES_PARAM = "ambiguities"
    const val HOSTED_DATA_PARAM = "hostedData"
    const val OUT_FORMAT_PARAM = "outFormat"
    const val KEY_PARAM = "key"


    //URL Parameter Values
    //TMDB Values
    const val LANGUAGE = "en-US"
    const val PAGE_NUM = 1
    const val REGION_CODE = "US"
    const val POSTER_IMAGE_SIZE = "w185"
    const val BACKDROP_IMAGE_SIZE = "w500"
    //MapQuest Values
    const val RADIUS = 8
    const val MAX_MATCHES = 10
    const val AMBIGUITIES = "ignore"
    const val HOST_DATA = "mqap.ntpois|group_sic_code=?|783201"
    const val OUT_FORMAT = "json"

    //Permission Request Code
    const val GPS_PERMISSION_REQUEST_CODE = 1


}