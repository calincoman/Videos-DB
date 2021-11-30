package actor;

import database.Database;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import fileio.ActorInputData;
import utils.DatabaseSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Actor {
    /**
     * actor name
     */
    private final String name;
    /**
     * description of the actor's career
     */
    private final String careerDescription;
    /**
     * videos starring actor
     */
    private final ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;

    public Actor(final ActorInputData actorInput) {
        this.name = actorInput.getName();
        this.careerDescription = actorInput.getCareerDescription();
        this.filmography = new ArrayList<String>(actorInput.getFilmography());
        this.awards = new HashMap<ActorsAwards, Integer>(actorInput.getAwards());
    }

    public Double getRating() {
        double actorRating = 0.0;
        double ratingsNumber = 0.0;

        for (String videoTitle : filmography) {
            Movie movie = DatabaseSearch.searchMovie(videoTitle);
            Show show = DatabaseSearch.searchShow(videoTitle);

            if (movie != null && movie.getRating() != null) {
                ++ratingsNumber;
                actorRating += movie.getRating();
            }
            if (show != null && show.getRating() != null) {
                ++ratingsNumber;
                actorRating += show.getRating();
            }
        }

        if (ratingsNumber != 0) {
            return actorRating / ratingsNumber;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getCareerDescription() {
        return careerDescription;
    }

    public ArrayList<String> getFilmography() {
        return filmography;
    }

    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public int getAwardsNumber() {
        int awardsNumber = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : awards.entrySet()) {
            awardsNumber += entry.getValue();
        }
        return awardsNumber;
    }

    public boolean hasKeyWord(String keyWord) {
        Pattern pattern = Pattern.compile("[ ,!.'(-]" + keyWord + "[ ,!.')-]",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(this.careerDescription);
        return matcher.find();
    }
}
