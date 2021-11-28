package database;

import fileio.Input;
import user.User;
import actor.Actor;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public final class Database {
    private static Database databaseInstance = null;

    private final ArrayList<User> users = new ArrayList<User>();
    private final ArrayList<Actor> actors = new ArrayList<Actor>();
    private final ArrayList<Video> videos = new ArrayList<Video>();
    private final ArrayList<Movie> movies = new ArrayList<Movie>();
    private final ArrayList<Show> shows = new ArrayList<Show>();

    private Database() {
    }

    public static Database getDatabaseInstance() {
        if (databaseInstance == null) {
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    public void loadData(final Input input) {
        DatabaseLoader.loadData(input);
    }

    public void addUsers(ArrayList<User> users) {
        this.users.addAll(users);
    }

    public void addActors(ArrayList<Actor> actors) {
        this.actors.addAll(actors);
    }

    public void addVideos(ArrayList<Video> videos) {
        this.videos.addAll(videos);
    }

    public void addMovies(ArrayList<Movie> movies) {
        this.movies.addAll(movies);
    }

    public void addShows(ArrayList<Show> shows) {
        this.shows.addAll(shows);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Show> getShows() {
        return shows;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }
}
