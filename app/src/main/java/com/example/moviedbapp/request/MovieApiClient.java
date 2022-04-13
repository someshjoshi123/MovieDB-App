package com.example.moviedbapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedbapp.AppExecutors;
import com.example.moviedbapp.models.MovieModel;
import com.example.moviedbapp.response.MovieSearchResponse;
import com.example.moviedbapp.utilis.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    private MutableLiveData<List<MovieModel>> vMovies;

    private static MovieApiClient instance;

    //making runnable request
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static MovieApiClient getInstance() {
        if(instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){
        vMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return vMovies;
    }

    public void searchMoviesApi(String query, int pageNumber) {

        if(retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().getNetworkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //cancelling the retrofit call
                myHandler.cancel(true);
            }
        },5000, TimeUnit.MILLISECONDS);
    }

    //retrieving data from API by runnable class
    private class RetrieveMoviesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            //getting response objects
            try{
                Response response = getMovies(query, pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1) {
                        //sending data to livedata
                        //postValue->background thread
                        vMovies.postValue(list);
                    }
                    else {
                        List<MovieModel> currentMovies = vMovies.getValue();
                        currentMovies.addAll(list);
                        vMovies.postValue(currentMovies);
                    }
                }
                else {
                    String error = response.errorBody().string();
                    Log.d("tag", "Error " + error);
                    vMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                vMovies.postValue(null);
            }
            if (cancelRequest) {
                return;
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return Service.getMoviesAPI().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }

        private void setCancelRequest() {
            Log.d("tag", "cancelling search request");
            cancelRequest = true;
        }
    }
}
