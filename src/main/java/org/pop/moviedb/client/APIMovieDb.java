package org.pop.moviedb.client;

import org.pop.moviedb.Const;
import org.pop.moviedb.callbacks.PopCBGeneric;
import org.pop.moviedb.entities.Credits;
import org.pop.moviedb.entities.Images;
import org.pop.moviedb.entities.Movie;
import org.pop.moviedb.entities.MovieSearch;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIMovieDb {

    private TheMovieDbService movieDbService;
    private final String apiKey;

    public APIMovieDb(final String apiKey) {
        this.apiKey = apiKey;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieDbService = retrofit.create(TheMovieDbService.class);
    }

    public void search(String term, PopCBGeneric<MovieSearch> callback) {
        Call<MovieSearch> call = movieDbService.searchMovie(apiKey, term);
        call.enqueue(new RetrofitCBGeneric<>("Movie search", callback));
    }

    public void movie(int movieId, PopCBGeneric<Movie> callback) {
        Call<Movie> call = movieDbService.movie(movieId, apiKey);
        call.enqueue(new RetrofitCBGeneric<>("Movie details", callback));
    }

    public void credits(int movieId, PopCBGeneric<Credits> callback) {
        Call<Credits> call = movieDbService.credits(movieId, apiKey);
        call.enqueue(new RetrofitCBGeneric<>("Movie credits", callback));
    }

    public void images(int movieId, PopCBGeneric<Images> callback) {
        Call<Images> call = movieDbService.images(movieId, apiKey);
        call.enqueue(new RetrofitCBGeneric<>("Movie images", callback));
    }
}
