package com.example.moviedbapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.moviedbapp.adapters.MovieViewHolder;
import com.example.moviedbapp.models.MovieModel;

public class MovieDetails extends AppCompatActivity {

    AppCompatImageView poster;
    AppCompatTextView title, synopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        poster = findViewById(R.id.poster);
        title = findViewById(R.id.title);
        synopsis = findViewById(R.id.synopsis);

        if(getIntent().hasExtra("movie")) {
            MovieModel movieModel = getIntent().getParcelableExtra("movie");

            title.setText(movieModel.getTitle());
            synopsis.setText(movieModel.getMovie_overview());

            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + movieModel.getPoster_path())
                    .into(poster);
        }
    }
}