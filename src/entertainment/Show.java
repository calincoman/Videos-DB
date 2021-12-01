package entertainment;

import fileio.SerialInputData;

import java.util.ArrayList;

/**
 * Class that defines a show
 * Inherits Video abstract class
 */
public class Show extends Video {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    public Show(final String title, final int year, final ArrayList<String> cast,
                final ArrayList<String> genres, final int numberOfSeasons,
                final ArrayList<Season> seasons) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public Show(final SerialInputData serialInputData) {
        super(serialInputData);
        this.numberOfSeasons = serialInputData.getNumberSeason();
        this.seasons = new ArrayList<Season>(serialInputData.getSeasons());
    }

    public final int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public final ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public final int getDuration() {
        int showDuration = 0;
        for (Season season : this.seasons) {
            showDuration += season.getDuration();
        }
        return showDuration;
    }

    /**
     * Calculates rating of a show (is nullable)
     * @return the rating if it was rated at least once, null otherwise
     */
    @Override
    public final Double getRating() {
        double showRating = 0.0;
        for (Season season : this.seasons) {
            double seasonRating = 0.0;
            if (season.getRatings().size() == 0) {
                continue;
            }
            for (Double rating : season.getRatings()) {
                seasonRating += rating;
            }
            showRating += seasonRating / season.getRatings().size();
        }
        showRating /= numberOfSeasons;
        if (showRating != 0) {
            return showRating;
        }
        return null;
    }

    /**
     * Calculates rating of a show (is not nullable)
     * @return the rating if it was rated at least once, 0 otherwise
     */
    @Override
    public final Double getRatingNotNullable() {
        double showRating = 0.0;
        for (Season season : this.seasons) {
            double seasonRating = 0.0;
            if (season.getRatings().size() == 0) {
                continue;
            }
            for (Double rating : season.getRatings()) {
                seasonRating += rating;
            }
            showRating += seasonRating / season.getRatings().size();
        }
        showRating /= numberOfSeasons;
        return showRating;
    }
}
