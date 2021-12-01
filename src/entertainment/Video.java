package entertainment;

import database.Database;
import fileio.ShowInput;
import user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class that defines a video
 */
public abstract class Video {
    /**
     * Video's title
     */
    private final String title;
    /**
     * The year the video was released
     */
    private final int year;
    /**
     * Video casting
     */
    private final ArrayList<String> cast;
    /**
     * Video genres
     */
    private final ArrayList<String> genres;

    public Video(final String title, final int year,
                     final ArrayList<String> cast, final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
    }

    public Video(final ShowInput showInput) {
        this.title = showInput.getTitle();
        this.year = showInput.getYear();
        this.cast = new ArrayList<String>(showInput.getCast());
        this.genres = new ArrayList<String>(showInput.getGenres());
    }

    public abstract int getDuration();

    public abstract Double getRating();

    public abstract Double getRatingNotNullable();

    /**
     * Calculate number of the video's appearances in the favorite lists of the users
     * @return number of appearances in the favorite lists of the users
     */
    public int getMarkedAsFavoriteNumber() {
        int favoriteCount = 0;
        for (User user : Database.getDatabaseInstance().getUsers()) {
            if (user.getFavoriteVideos().contains(this.title)) {
                ++favoriteCount;
            }
        }
        return favoriteCount;
    }

    /**
     * Calculates the number of times the video was viewed
     * @return number of times the video was viewed
     */
    public int getViewNumber() {
        int viewNumber = 0;
        for (User user : Database.getDatabaseInstance().getUsers()) {
            if (user.getHistory().containsKey(this.title)) {
                viewNumber += user.getHistory().get(this.title);
            }
        }
        return viewNumber;
    }

    /**
     * Creates a map with entry (genre title, genre popularity)
     * Popularity of a genre is the number of views of the videos which have that genre
     * @return map containing (String, Double) entries
     */
    public static Map<String, Double> getGenrePopularity() {
        Map<String, Double> genrePopularity = new HashMap<String, Double>();

        for (Video video : Database.getDatabaseInstance().getVideos()) {
            int viewNumber = video.getViewNumber();
            for (String genre : video.getGenres()) {
                genrePopularity.put(genre, genrePopularity.getOrDefault(genre, 0.0) + viewNumber);
            }
        }
        return genrePopularity;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }
}
