package com.example.moviedbapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.example.moviedbapp.adapters.MovieAdapter;
import com.example.moviedbapp.adapters.OnMovieListener;
import com.example.moviedbapp.models.MovieModel;
import com.example.moviedbapp.viewmodels.MovieListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMovieListener {

    private MovieListViewModel movieListViewModel;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SetupSearchView();

        recyclerView = findViewById(R.id.recyclerView);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        ObserveChange();
        ConfigureRecyclerView();

    }

    private void SetupSearchView() {
        final SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMoviesApi(
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void ObserveChange() {

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if(movieModels != null) {
                    for (MovieModel movieModel: movieModels) {
                        //get data
                        Log.d("tag", "onChanged: " + movieModel.getTitle());

                        movieAdapter.setMovieModelList(movieModels);
                    }
                }
            }
        });
    }



    private void ConfigureRecyclerView() {

        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {

        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieAdapter.getSelectedMovie(position));
        startActivity(intent);

    }

    /*private void GetRetrofitResponse() {
        MoviesAPI moviesAPI = Service.getMoviesAPI();

        Call<MovieSearchResponse> responseCall = moviesAPI
                .searchMovie(
                        Credentials.API_KEY,
                        "Jack Reacher",
                        1
                );

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {

                if(response.code() == 200) {
                    Log.d("tag", "response " + response.body().toString());

                    List<MovieModel> movieModels = new ArrayList<>(response.body().getMovies());

                    for(MovieModel movie: movieModels) {
                        Log.d("tag", "Name: " + movie.getTitle());
                    }
                }

                else {
                    try {
                        Log.d("tag", "error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });
    }*/
}