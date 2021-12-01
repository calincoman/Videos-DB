package database;

import common.Constants;
import fileio.ActionInputData;
import fileio.Input;
import user.User;
import actor.Actor;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class used to store the program's data
 */
public final class Database {
    private static Database databaseInstance = null;

    /**
     * Lists with all data required
     */
    private final ArrayList<User> users = new ArrayList<User>();
    private final ArrayList<Actor> actors = new ArrayList<Actor>();
    private final ArrayList<Video> videos = new ArrayList<Video>();
    private final ArrayList<Movie> movies = new ArrayList<Movie>();
    private final ArrayList<Show> shows = new ArrayList<Show>();

    private Database() {
    }

    /**
     * Gets the database instance. If it doesn't exist, it creates and returns it.
     * @return the database instance
     */
    public static Database getDatabaseInstance() {
        if (databaseInstance == null) {
            databaseInstance = new Database();
        }
        return databaseInstance;
    }

    /**
     * Finds the type of video
     * @param videoTitle the video's title
     * @return "movies" if the video is a movie, "shows" if it is a show
     */
    public static String getVideoType(final String videoTitle) {
        for (Movie movie : getDatabaseInstance().getMovies()) {
            if (movie.getTitle().equals(videoTitle)) {
                return Constants.MOVIES;
            }
        }
        return Constants.SHOWS;
    }

    /**
     * Filters the videos from the database by year and genre
     * @param action contains filter input data
     * @return a list with the videos filtered by year and genre
     */
    public static ArrayList<Video> filterVideos(final ActionInputData action) {
        // Get filters
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

    /**
     * Loads input data in the database
     * @param input input data
     */
    public void loadData(final Input input) {
        DatabaseLoader.loadData(input);
    }

    /**
     * Adds a users list to the database
     * @param otherUsers users list to be added in the database
     */
    public void addUsers(final ArrayList<User> otherUsers) {
        this.users.addAll(otherUsers);
    }

    /**
     * Adds an actors list to the database
     * @param otherActors actors list to be added in the database
     */
    public void addActors(final ArrayList<Actor> otherActors) {
        this.actors.addAll(otherActors);
    }

    /**
     * Adds a videos list to the database
     * @param otherVideos videos list to be added in the database
     */
    public void addVideos(final ArrayList<Video> otherVideos) {
        this.videos.addAll(otherVideos);
    }

    /**
     * Adds a movies list to the database
     * @param otherMovies movies list to be added in the database
     */
    public void addMovies(final ArrayList<Movie> otherMovies) {
        this.movies.addAll(otherMovies);
    }

    /**
     * Adds a shows list to the database
     * @param otherShows shows list to be added in the database
     */
    public void addShows(final ArrayList<Show> otherShows) {
        this.shows.addAll(otherShows);
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
