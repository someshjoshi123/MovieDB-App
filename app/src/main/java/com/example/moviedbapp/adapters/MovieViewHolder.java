package com.example.moviedbapp.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedbapp.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    AppCompatImageView poster;
    AppCompatTextView title, release_date;
    AppCompatRatingBar vote_avg;

    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;

        poster = itemView.findViewById(R.id.poster);
        title = itemView.findViewById(R.id.title);
        release_date = itemView.findViewById(R.id.release_date);
        vote_avg = itemView.findViewById(R.id.vote_avg);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
