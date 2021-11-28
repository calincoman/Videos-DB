package database;

import actor.Actor;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import fileio.*;
import user.User;

import java.util.ArrayList;

public class DatabaseLoader {

    public static void loadData(final Input input) {
        DatabaseLoader databaseLoader = new DatabaseLoader();

        databaseLoader.loadUsers(input);
        databaseLoader.loadActors(input);
        databaseLoader.loadMovies(input);
        databaseLoader.loadShows(input);
        databaseLoader.loadVideos(input);
    }

    public void loadUsers(final Input input) {
        ArrayList<User> users = new ArrayList<User>();
        for (UserInputData userInput : input.getUsers()) {
            users.add(new User(userInput));
        }
        Database.getDatabaseInstance().addUsers(users);
    }

    public void loadActors(final Input input) {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        for (ActorInputData actorInput : input.getActors()) {
            actors.add(new Actor(actorInput));
        }
        Database.getDatabaseInstance().addActors(actors);
    }

    public void loadMovies(final Input input) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for (MovieInputData movieInput : input.getMovies()) {
            movies.add(new Movie(movieInput));
        }
        Database.getDatabaseInstance().addMovies(movies);
    }
    public void loadShows(final Input input) {
        ArrayList<Show> shows = new ArrayList<Show>();
        for (SerialInputData showInput : input.getSerials()) {
            shows.add(new Show(showInput));
        }
        Database.getDatabaseInstance().addShows(shows);
    }

    public void loadVideos(final Input input) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        ArrayList<Show> shows = new ArrayList<Show>();

        for (MovieInputData movieInput : input.getMovies()) {
            movies.add(new Movie(movieInput));
        }
        for (SerialInputData showInput : input.getSerials()) {
            shows.add(new Show(showInput));
        }

        ArrayList<Video> videos = new ArrayList<Video>();
        videos.addAll(movies);
        videos.addAll(shows);
        Database.getDatabaseInstance().addVideos(videos);
    }
}
