package user;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class defining a user
 */
public class User {
    /**
     * Username
     */
    private final String username;
    /**
     * Subscription Type
     */
    private final String subscriptionType;
    /**
     * The history of the watched movies
     */
    private final Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private final ArrayList<String> favoriteVideos;

    private final Map<String, ArrayList<Integer>> ratedVideos;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteVideos = favoriteMovies;
        this.history = history;
        ratedVideos = new HashMap<String, ArrayList<Integer>>();
    }

    public User(final UserInputData userInputData) {
        this.username = userInputData.getUsername();
        this.subscriptionType = userInputData.getSubscriptionType();
        this.history = new HashMap<String, Integer>(userInputData.getHistory());
        this.favoriteVideos = new ArrayList<String>(userInputData.getFavoriteMovies());
        this.ratedVideos = new HashMap<String, ArrayList<Integer>>();
    }

    /**
     * Calculates number of ratings given by the user
     * @return number of ratings given by user
     */
    public int getNumberOfRatings() {
        int numberOfRatings = 0;
        for (Map.Entry<String, ArrayList<Integer>> rating : ratedVideos.entrySet()) {
            numberOfRatings += rating.getValue().size();
        }
        return numberOfRatings;
    }

    public String getUsername() {
        return username;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public Map<String, Integer> getHistory() {
        return history;
    }

    public ArrayList<String> getFavoriteVideos() {
        return favoriteVideos;
    }

    public Map<String, ArrayList<Integer>> getRatedVideos() {
        return ratedVideos;
    }

    /**
     * Checks if a video was seen by the user
     * @param videoName video's title
     * @return true if it was seen, false otherwise
     */
    public boolean videoWasSeen(String videoName) {
        return history.containsKey(videoName);
    }
}
