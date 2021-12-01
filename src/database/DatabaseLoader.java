package database;

import actor.Actor;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import user.User;

import java.util.ArrayList;

/**
 * Class used to load data in the database
 */
public class DatabaseLoader {

    /**
     * Loads data in the database by calling the load functions
     * @param input input data to be loaded
     */
    public static void loadData(final Input input) {
        DatabaseLoader databaseLoader = new DatabaseLoader();

        databaseLoader.loadUsers(input);
        databaseLoader.loadActors(input);
        databaseLoader.loadMovies(input);
        databaseLoader.loadShows(input);
        databaseLoader.loadVideos(input);
    }

    /**
     * Removes data from the database
     */
    public static void unLoadData() {
        Database.getDatabaseInstance().getActors().clear();
        Database.getDatabaseInstance().getUsers().clear();
        Database.getDatabaseInstance().getVideos().clear();
        Database.getDatabaseInstance().getMovies().clear();
        Database.getDatabaseInstance().getShows().clear();
    }

    /**
     * Makes a deep copy of the users from the input and adds the copies in the database
     * @param input input data
     */
    public final void loadUsers(final Input input) {
        ArrayList<User> users = new ArrayList<User>();
        for (UserInputData userInput : input.getUsers()) {
            users.add(new User(userInput));
        }
        Database.getDatabaseInstance().addUsers(users);
    }

    /**
     * Makes a deep copy of the actors from the input and adds the copies in the database
     * @param input input data
     */
    public final void loadActors(final Input input) {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        for (ActorInputData actorInput : input.getActors()) {
            actors.add(new Actor(actorInput));
        }
        Database.getDatabaseInstance().addActors(actors);
    }

    /**
     * Makes a deep copy of the movies from the input and adds the copies in the database
     * @param input input data
     */
    public final void loadMovies(final Input input) {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        for (MovieInputData movieInput : input.getMovies()) {
            movies.add(new Movie(movieInput));
        }
        Database.getDatabaseInstance().addMovies(movies);
    }

    /**
     * Makes a deep copy of the shows from the input and adds the copies in the database
     * @param input input data
     */
    public final void loadShows(final Input input) {
        ArrayList<Show> shows = new ArrayList<Show>();
        for (SerialInputData showInput : input.getSerials()) {
            shows.add(new Show(showInput));
        }
        Database.getDatabaseInstance().addShows(shows);
    }

    /**
     * Adds the deep copies of movies and shows from the input data to a video list
     * The video list will have same references as the objects from the movie and show list
     * @param input input data
     */
    public final void loadVideos(final Input input) {

        ArrayList<Movie> movies = Database.getDatabaseInstance().getMovies();
        ArrayList<Show> shows = Database.getDatabaseInstance().getShows();

        ArrayList<Video> videos = new ArrayList<Video>();
        videos.addAll(movies);
        videos.addAll(shows);
        Database.getDatabaseInstance().addVideos(videos);
    }
}
