package org.pop.moviedb;

import org.pop.moviedb.callbacks.PopCBGeneric;
import org.pop.moviedb.client.APIMovieDb;
import org.pop.moviedb.entities.*;

import java.util.List;

/**
 * Wrapper for The movie database API https://www.themoviedb.org
 * Use it to fetch info about movies and tv shows.
 *
 */
public class PopMovieDB {
    private APIMovieDb apiMovieDb;

    public PopMovieDB(String apiKeyTheMovieDb) {
        apiMovieDb = new APIMovieDb(apiKeyTheMovieDb);
    }

    /**
     * Returns the first page of results for given query term
     *
     * @param term Query to use in API call
     * @param callback If API call is successful, returns the found movies
     */
    public void search(String term, final PopCBGeneric<List<MoviePartial>> callback) {
        apiMovieDb.search(term, new PopCBGeneric<MovieSearch>() {
            @Override
            public void onResponse(boolean success, MovieSearch movieSearch) {
                if (success) {
                    callback.onResponse(true, movieSearch.getResults());
                } else {
                    callback.onResponse(false, null);
                }
            }
        });
    }

    public void details(MoviePartial moviePartial, PopCBGeneric<Movie> callback) {
        details(moviePartial.getId(), callback);
    }

    public void details(int movieId, PopCBGeneric<Movie> callback) {
        apiMovieDb.movie(movieId, callback);
    }

    public void credits(int movieId, PopCBGeneric<Credits> callback) {
        apiMovieDb.credits(movieId, callback);
    }

    public void images(int movieId, PopCBGeneric<Images> callback) {
        apiMovieDb.images(movieId, callback);
    }

    /**
     * Gets url to best rated poster image for given movie
     * @param movieId Movie's id
     * @param callback This callback will receive the url to poster
     */
    public void poster(int movieId, final PopCBGeneric<String> callback) {
        images(movieId, new PopCBGeneric<Images>() {
            @Override
            public void onResponse(boolean success, Images images) {
                if (success) {
                    if (images.getPosters().size() > 0) {
                        Image image = bestImage(images.getPosters());
                        callback.onResponse(true, imageUrl(image));
                    }
                } else {
                    callback.onResponse(false, null);
                }
            }
        });
    }

    /**
     * Gets url to best rated backdrop image for given movie
     * @param movieId Movie's id
     * @param callback This callback will receive the url to backdrop
     */
    public void backdrop(int movieId, final PopCBGeneric<String> callback) {
        images(movieId, new PopCBGeneric<Images>() {
            @Override
            public void onResponse(boolean success, Images images) {
                if (success) {
                    if (images.getBackdrops().size() > 0) {
                        Image image = bestImage(images.getBackdrops());
                        callback.onResponse(true, imageUrl(image));
                    }
                } else {
                    callback.onResponse(false, null);
                }
            }
        });
    }

    public String imageUrl(MoviePartial moviePartial) {
        return Const.BASE_PATH_IMAGES_ORIGINAL + moviePartial.getPosterPath();
    }

    private String imageUrl(Image image) {
        return Const.BASE_PATH_IMAGES_ORIGINAL + image.getFilePath();
    }

    private Image bestImage(List<Image> images) {
        Image bestImage = images.get(0);
        for (Image image : images) {
            if (image.getVoteAverage() > bestImage.getVoteAverage()) {
                bestImage = image;
            }
        }

        return bestImage;
    }
}
