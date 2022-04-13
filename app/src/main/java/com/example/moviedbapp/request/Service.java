package com.example.moviedbapp.request;

import com.example.moviedbapp.utilis.Credentials;
import com.example.moviedbapp.utilis.MoviesAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static MoviesAPI moviesAPI = retrofit.create(MoviesAPI.class);

    public static MoviesAPI getMoviesAPI() {
        return moviesAPI;
    }
}
