package entertainment;

import database.Database;
import fileio.ShowInput;
import user.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;

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

    public int getMarkedAsFavoriteNumber() {
        int favoriteCount = 0;
        for (User user : Database.getDatabaseInstance().getUsers()) {
            if (user.getFavoriteVideos().contains(this.title)) {
                ++favoriteCount;
            }
        }
        return favoriteCount;
    }

    public int getViewNumber() {
        int viewNumber = 0;
        for (User user : Database.getDatabaseInstance().getUsers()) {
            if (user.getHistory().containsKey(this.title)) {
                viewNumber += user.getHistory().get(this.title);
            }
        }
        return viewNumber;
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
