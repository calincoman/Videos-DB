package entertainment;

import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.Map;

public class Movie extends Video {
    /**
     * Duration in minutes of a movie
     */
    private final int duration;

    private final ArrayList<Double> ratings;

    public Movie(String title, int year, ArrayList<String> cast, ArrayList<String> genres,
                 int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
        this.ratings = new ArrayList<Double>();
    }

    public Movie(final MovieInputData movieInput) {
        super(movieInput);
        this.duration = movieInput.getDuration();
        this.ratings = new ArrayList<Double>();
    }

    @Override
    public Double getRating() {
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

    public int getDuration() {
        return duration;
    }

    public final ArrayList<Double> getRatings() {
        return ratings;
    }
}
