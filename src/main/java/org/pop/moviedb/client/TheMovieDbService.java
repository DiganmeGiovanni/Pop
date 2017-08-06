package org.pop.moviedb.client;

import org.pop.moviedb.entities.Credits;
import org.pop.moviedb.entities.Images;
import org.pop.moviedb.entities.Movie;
import org.pop.moviedb.entities.MovieSearch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDbService {

    @GET("search/movie")
    Call<MovieSearch> searchMovie(@Query("api_key") String apiKey, @Query("query") String term);

    @GET("movie/{movieId}")
    Call<Movie> movie(@Path("movieId") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movieId}/credits")
    Call<Credits> credits(@Path("movieId") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movieId}/images")
    Call<Images> images(@Path("movieId") int movieId, @Query("api_key") String apiKey);
}
