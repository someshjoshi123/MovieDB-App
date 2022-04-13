package com.example.moviedbapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedbapp.R;
import com.example.moviedbapp.models.MovieModel;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private List<MovieModel> movieModelList;

    public MovieAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    private OnMovieListener onMovieListener;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        holder.title.setText(movieModelList.get(position).getTitle());
        holder.release_date.setText(movieModelList.get(position).getRelease_date());
        holder.vote_avg.setRating((movieModelList.get(position).getVote_average())/2);

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + movieModelList.get(position).getPoster_path())
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        if(movieModelList != null) {
            return movieModelList.size();
        }
        return 0;
    }

    public void setMovieModelList(List<MovieModel> movieModelList) {
        this.movieModelList = movieModelList;
        notifyDataSetChanged();
    }

    public MovieModel getSelectedMovie(int position) {
        if (movieModelList != null) {
            if(movieModelList.size() > 0) {
                return movieModelList.get(position);
            }
        }
        return null;
    }
}