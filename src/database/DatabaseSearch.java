package database;

import database.Database;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import user.User;

public class DatabaseSearch {

    public static User searchUser(String username) {
        return Database.getDatabaseInstance().getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    public static Video searchVideo(String videoTitle) {
        return Database.getDatabaseInstance().getVideos().stream()
                .filter(video -> video.getTitle().equals(videoTitle))
                .findAny()
                .orElse(null);
    }

    public static Movie searchMovie(String movieTitle) {
        return Database.getDatabaseInstance().getMovies().stream()
                .filter(movie -> movie.getTitle().equals(movieTitle))
                .findAny()
                .orElse(null);
    }

    public static Show searchShow(String showTitle) {
        return Database.getDatabaseInstance().getShows().stream()
                .filter(show -> show.getTitle().equals(showTitle))
                .findAny()
                .orElse(null);
    }
}
