package database;

import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import user.User;
import actor.Actor;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

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

    public static String getVideoType(final String videoTitle) {
        for (Movie movie : getDatabaseInstance().getMovies()) {
            if (movie.getTitle().equals(videoTitle)) {
                return Constants.MOVIES;
            }
        }
        return Constants.SHOWS;
    }

    public static ArrayList<Video> filterVideos(final ActionInputData action) {
        List<String> yearFilter = action.getFilters().get(Constants.YEAR_FILTER_INDEX);
        List<String> genreFilter = action.getFilters().get(Constants.GENRE_FILTER_INDEX);

        String year = (yearFilter.isEmpty()) ? null : yearFilter.get(0);

        ArrayList<Video> videoList = Database.getDatabaseInstance().getVideos();
        ArrayList<Video> filteredVideoList = new ArrayList<Video>();

        for (Video video : videoList) {
            boolean toBeAdded = true;
            if (year != null && !year.equals(String.valueOf(video.getYear()))) {
                toBeAdded = false;
            }
            for (String genre : genreFilter) {
                if (genre != null && !video.getGenres().contains(genre)) {
                    toBeAdded = false;
                    break;
                }
            }
            if (toBeAdded) {
                filteredVideoList.add(video);
            }
        }
        return filteredVideoList;
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
