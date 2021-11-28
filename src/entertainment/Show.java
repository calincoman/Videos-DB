package entertainment;

import database.Database;
import fileio.SerialInputData;
import fileio.ShowInput;

import java.util.ArrayList;

public class Show extends Video {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;

    private Double showRating;

    public Show(String title, int year, ArrayList<String> cast, ArrayList<String> genres,
                int numberOfSeasons, ArrayList<Season> seasons) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public Show(final SerialInputData serialInputData) {
        super(serialInputData);
        this.numberOfSeasons = serialInputData.getNumberSeason();
        this.seasons = new ArrayList<Season>(serialInputData.getSeasons());
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public int getDuration() {
        int showDuration = 0;
        for (Season season : this.seasons) {
            showDuration += season.getDuration();
        }
        return showDuration;
    }

    @Override
    public Double getRating() {
        double showRating = 0.0;
        for (Season season : this.seasons) {
            double seasonRating = 0.0;
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
}
