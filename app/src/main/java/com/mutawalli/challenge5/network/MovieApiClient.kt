package com.mutawalli.challenge5.network

import com.mutawalli.challenge5.data.api.MovieResponse
import com.mutawalli.challenge5.data.api.Movies
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

object MovieApiClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance: MovieApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(EndPoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        retrofit.create(MovieApiService::class.java)
    }
}

interface MovieApiService {
    companion object {
        private const val TOKEN = "0fbaf8c27d542bc99bfc67fb877e3906"
    }

    @GET(EndPoint.Popular.urlPopular)
    fun getPopular(
        @Query("api_key") api: String = TOKEN,
        @Query("page") page: Int = 1
    ): Call<MovieResponse>

    @GET(EndPoint.Detail.detail)
    fun getDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") api: String = TOKEN
    ): Call<Movies>

}

object EndPoint {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    object Popular {
        const val urlPopular = "movie/popular"
    }

    object Detail {
        const val detail = "movie/{movieId}"
    }
}