package org.pop;

import org.pop.moviedb.PopMovieDB;
import org.pop.moviedb.callbacks.PopCBGeneric;
import org.pop.moviedb.entities.*;

import java.util.List;
import java.util.Scanner;

public class PopConsoleClient {
    private Scanner scanner = new Scanner(System.in);

    private final String apiKey = "b05e87f356ef223c5aeacf0bcae54d04";
    private PopMovieDB popMovieDB = new PopMovieDB(apiKey);

    public static void main(String[] args) {
        PopConsoleClient popConsoleClient = new PopConsoleClient();
        popConsoleClient.askMenu();
    }

    private void anyKey() {
        System.out.println("* Press any key to continue");
        scanner.next();
        System.out.println("");

        askMenu();
    }

    private void askMenu() {
        System.out.println("\n--- --- --- --- --- --- --- --- --- ---");
        System.out.println("Pop is the 'pop' in 'Popcorn moments'");
        System.out.println("0. Exit");
        System.out.println("1. Search movie");
        System.out.println("2. Movie details");
        System.out.println("3. Movie credits");
        System.out.println("4. Movie poster");
        System.out.println("5. Movie backdrop");
        System.out.print("ENTER YOUR OPTION: ");

        handleOptions(scanner.nextInt());
    }

    private void handleOptions(int option) {
        switch (option) {
            case 0:
                System.exit(0);
                break;
            case 1:
                searchMovie();
                break;
            case 2:
                movieDetails();
                break;
            case 3:
                movieCredits();
                break;
            case 4:
                moviePoster();
                break;
            case 5:
                movieBackdrop();
                break;
        }
    }

    private void searchMovie() {
        System.out.println("");
        System.out.print("Enter your query: ");
        String query = scanner.next();
        System.out.println("");

        popMovieDB.search(query, new PopCBGeneric<List<MoviePartial>>() {
            @Override
            public void onResponse(boolean success, List<MoviePartial> movieSearch) {
                if (success) {
                    System.out.println("\nResults");
                    for (MoviePartial moviePartial : movieSearch) {
                        System.out.println("[" + moviePartial.getId() + "]\t" + moviePartial.getTitle());
                    }
                }

                anyKey();
            }
        });
    }

    private void movieDetails() {
        System.out.println("");
        System.out.print("Enter movie id: ");
        int movieId = scanner.nextInt();
        System.out.println("");

        popMovieDB.details(movieId, new PopCBGeneric<Movie>() {
            @Override
            public void onResponse(boolean success, Movie movie) {
                if (success) {
                    System.out.println("Title: " + movie.getTitle());
                    System.out.println("Original title: " + movie.getOriginalTitle());
                    System.out.println("Runtime: " + movie.getRuntime());
                }

                anyKey();
            }
        });
    }

    private void movieCredits() {
        System.out.println("");
        System.out.print("Enter movie id: ");
        int movieId = scanner.nextInt();
        System.out.println("");

        popMovieDB.credits(movieId, new PopCBGeneric<Credits>() {
            @Override
            public void onResponse(boolean success, Credits credits) {
                if (success) {
                    System.out.println("CAST:");
                    for (Cast cast : credits.getCast()) {
                        System.out.println("* " + cast.getName() + " ==> " + cast.getCharacter());
                    }

                    System.out.println("CREW:");
                    for (Crew crew : credits.getCrew()) {
                        System.out.println("* " + crew.getName() + " ==> " + crew.getJob());
                    }
                }

                anyKey();
            }
        });
    }

    private void moviePoster() {
        System.out.println("");
        System.out.print("Enter movie id: ");
        int movieId = scanner.nextInt();
        System.out.println("");

        popMovieDB.poster(movieId, new PopCBGeneric<String>() {
            @Override
            public void onResponse(boolean success, String url) {
                if (success) {
                    System.out.println("* " + url);
                }

                anyKey();
            }
        });
    }

    private void movieBackdrop() {
        System.out.println("");
        System.out.print("Enter movie id: ");
        int movieId = scanner.nextInt();
        System.out.println("");

        popMovieDB.backdrop(movieId, new PopCBGeneric<String>() {
            @Override
            public void onResponse(boolean success, String url) {
                if (success) {
                    System.out.println("* " + url);
                }

                anyKey();
            }
        });
    }
}
