package entertainment;

import fileio.MovieInputData;

import java.util.ArrayList;

/**
 * Class that defines a movie
 * Inherits Video abstract class
 */
public class Movie extends Video {
    /**
     * Duration in minutes of a movie
     */
    private final int duration;

    private final ArrayList<Double> ratings;

    public Movie(final String title, final int year, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new ArrayList<Double>();
    }

    public Movie(final MovieInputData movieInput) {
        super(movieInput);
        this.duration = movieInput.getDuration();
        this.ratings = new ArrayList<Double>();
    }

    /**
     * Calculates rating of a movie (is nullable)
     * @return the rating if it was rated at least once, null otherwise
     */
    @Override
    public final Double getRating() {
        double movieRating = 0.0;
        if (ratings.isEmpty()) {
            return null;
        }
        for (Double rating : ratings) {
            movieRating += rating;
        }
        movieRating /= ratings.size();
        return movieRating;
    }

    /**
     * Calculates rating of a movie (is not nullable)
     * @return the rating if it was rated at least once, 0 otherwise
     */
    @Override
    public final Double getRatingNotNullable() {
        double movieRating = 0.0;
        if (ratings.isEmpty()) {
            return 0.0;
        }
        for (Double rating : ratings) {
            movieRating += rating;
        }
        movieRating /= ratings.size();
        return movieRating;
    }

    public final int getDuration() {
        return duration;
    }

    public final ArrayList<Double> getRatings() {
        return ratings;
    }
}
