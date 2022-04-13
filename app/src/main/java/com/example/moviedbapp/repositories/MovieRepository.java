package com.example.moviedbapp.repositories;

import androidx.lifecycle.LiveData;

import com.example.moviedbapp.models.MovieModel;
import com.example.moviedbapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;
    private MovieApiClient movieApiClient;
    private String mQuery;
    private int mPageNumber;

    public static MovieRepository getInstance() {
        if(instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }

    public void searchMoviesApi(String query, int pageNumber) {
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApi(query, pageNumber);
    }

    public void searchNextPage() {
        searchMoviesApi(mQuery, mPageNumber+1);
    }
}
