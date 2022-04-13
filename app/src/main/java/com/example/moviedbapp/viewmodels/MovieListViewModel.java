package com.example.moviedbapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviedbapp.models.MovieModel;
import com.example.moviedbapp.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }

    public void searchMoviesApi(String query, int pageNumber) {
        movieRepository.searchMoviesApi(query, pageNumber);
    }

    public void searchNextPage() {
        movieRepository.searchNextPage();
    }
}
