package database;

import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import user.User;

/**
 * Class used to search videos and users in the database
 */
public final class DatabaseSearch {
    private DatabaseSearch() {
    }

    /**
     * Search a user by username
     * @param username username of the user
     * @return the user object if it is found, null otherwise
     */
    public static User searchUser(final String username) {
        return Database.getDatabaseInstance().getUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    /**
     * Search a video by its title
     * @param videoTitle video's title
     * @return the video object if it is found, null otherwise
     */
    public static Video searchVideo(final String videoTitle) {
        return Database.getDatabaseInstance().getVideos().stream()
                .filter(video -> video.getTitle().equals(videoTitle))
                .findAny()
                .orElse(null);
    }

    /**
     * Search a movie by its title
     * @param movieTitle movie's title
     * @return the movie object if it is found, null otherwise
     */
    public static Movie searchMovie(final String movieTitle) {
        return Database.getDatabaseInstance().getMovies().stream()
                .filter(movie -> movie.getTitle().equals(movieTitle))
                .findAny()
                .orElse(null);
    }

    /**
     * Search a show by its title
     * @param showTitle show's title
     * @return the show object if it is found, null otherwise
     */
    public static Show searchShow(final String showTitle) {
        return Database.getDatabaseInstance().getShows().stream()
                .filter(show -> show.getTitle().equals(showTitle))
                .findAny()
                .orElse(null);
    }
}
