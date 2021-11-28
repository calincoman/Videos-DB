package user;

import fileio.UserInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public boolean videoWasSeen(String videoName) {
        return history.containsKey(videoName);
    }
}
