package action;

import actor.Actor;
import common.Constants;
import database.Database;
import entertainment.Video;
import fileio.ActionInputData;
import solve.SortingHandler;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class used for the query operations
 */
public final class Query {
    private Query() {
    }

    /**
     * Sorts in a given order all actors by the average rating of the videos in which they played
     * in and return first N of them, N specified in query
     * @param query information about the query to be executed
     * @return string containing the query's result message
     */
    public static String actorAverageQuery(final ActionInputData query) {
        Map<String, Double> actorRatingMap = new HashMap<String, Double>();

        for (Actor actor : Database.getDatabaseInstance().getActors()) {
            Double actorRating = actor.getRating();
            // Create a map with (actor name, actor rating) as entry
            if (actorRating != null) {
                actorRatingMap.put(actor.getName(), actorRating);
            }
        }
        // Get the query's result message (sort the map and get the first N actors' names)
        return getQueryMessage(query, actorRatingMap);
    }

    /**
     * Sorts in a given order all actors with the specified awards by the award number they
     * obtained and returns first N of them, N specified in query
     * @param query information about the query to be executed
     * @return string containing the query's result message
     */
    public static String actorAwardQuery(final ActionInputData query) {
        Map<String, Double> actorFilteredMap = new HashMap<String, Double>();

        for (Actor actor : Database.getDatabaseInstance().getActors()) {
            boolean hasAllAwards = true;
            for (String award : query.getFilters().get(Constants.AWARDS_FILTER_INDEX)) {
                if (!actor.getAwards().containsKey(Utils.stringToAwards(award))) {
                    hasAllAwards = false;
                    break;
                }
            }
            // If an actor has all specified awards, add it to a map with the entry
            // (actor name, actor's award number)
            if (hasAllAwards) {
                actorFilteredMap.put(actor.getName(), (double) actor.getAwardsNumber());
            }
        }
        // Get the query's result message (sort the map and get the first N actors' names)
        return getQueryMessage(query, actorFilteredMap);
    }

    /**
     * Sorts alphabetically in a given order all actors whose description contains all keywords
     * specified in the query and returns first N of them, N specified in query
     * @param query information about the query to be executed
     * @return string containing the query's result message
     */
    public static String actorFilterQuery(final ActionInputData query) {
        List<String> keyWords = query.getFilters().get(Constants.WORDS_FILTER_INDEX);
        ArrayList<String> actorFilteredList = new ArrayList<String>();

        for (Actor actor : Database.getDatabaseInstance().getActors()) {
            boolean hasAllKeyWords = true;
            for (String keyWord : keyWords) {
                if (!actor.hasKeyWord(keyWord)) {
                    hasAllKeyWords = false;
                    break;
                }
            }
            // If an actor's description contains all keywords, add the actor to the list
            if (hasAllKeyWords) {
                actorFilteredList.add(actor.getName());
            }
        }
        // Sort the actor list by the actors' names
        if (query.getSortType().equals(Constants.ASCENDING)) {
            Collections.sort(actorFilteredList);
        } else {
            actorFilteredList.sort(Collections.reverseOrder());
        }
        return "Query result: " + actorFilteredList;
    }

    /**
     * Sorts in a given order all videos of a certain type (movie/show) by their rating and
     * returns first N of them, N specified in query
     * @param query information about the query to be executed
     * @return string containing the query's result message
     */
    public static String videoRatingQuery(final ActionInputData query) {
        Map<String, Double> videoRatingMap = new HashMap<String, Double>();
        // Filter videos by year and genre specified in query
        ArrayList<Video> filteredVideos = Database.filterVideos(query);

        filteredVideos.forEach(video -> {
            Double videoRating = video.getRating();
            String videoType = Database.getVideoType(video.getTitle());

            // If the video has a rating and is of the type specified in query, add it to the
            // map with entry (video title, video rating)
            if (videoRating != null && videoType.equals(query.getObjectType())) {
                videoRatingMap.put(video.getTitle(), videoRating);
            }
        });
        // Get the query's result message (sort the map and get the first N videos' names)
        return getQueryMessage(query, videoRatingMap);
    }

    /**
     * Sorts in a given order all videos of a certain type by the number of appearances in the
     * users' favorite list and returns first N of them, N specified in query
     * @param query information about the query to be executed
     * @return string containing the query's result message
     */
    public static String videoFavoriteQuery(final ActionInputData query) {
        Map<String, Double> videoFavoriteMap = new HashMap<String, Double>();
        // Filter videos by year and genre specified in query
        ArrayList<Video> filteredVideos = Database.filterVideos(query);

        filteredVideos.forEach(video -> {
            int markedAsFavoriteNumber = video.getMarkedAsFavoriteNumber();
            String videoType = Database.getVideoType(video.getTitle());

            // If the video was marked as favorite at least once and is of the type specified in
            // the query, add it to the map with entry (video title, marked as favorite number)
            if (markedAsFavoriteNumber != 0 && videoType.equals(query.getObjectType())) {
                videoFavoriteMap.put(video.getTitle(), (double) markedAsFavoriteNumber);
            }
        });
        // Get the query's result message (sort the map and get the first N videos' names)
        return getQueryMessage(query, videoFavoriteMap);
    }

    /**
     * Sorts in a given order all videos of a certain type by their duration and returns first
     * N of them, N specified in query
     * @param query information about the query to be executed
     * @return string containing the query's result message
     */
    public static String videoLongestQuery(final ActionInputData query) {
        Map<String, Double> videoDurationMap = new HashMap<String, Double>();
        // Filter videos by year and genre specified in query
        ArrayList<Video> filteredVideos = Database.filterVideos(query);

        filteredVideos.forEach(video -> {
            String videoType = Database.getVideoType(video.getTitle());

            // If the video is of the type specified in query, add it to the map
            // with entry (video title, video duration)
            if (videoType.equals(query.getObjectType())) {
                videoDurationMap.put(video.getTitle(), (double) video.getDuration());
            }
        });
        // Get the query's result message (sort the map and get the first N videos' names)
        return getQueryMessage(query, videoDurationMap);
    }

    /**
     * Sorts in a given order all videos of a certain type by their number of views and returns
     * first N of them, N specified in query
     * @param query information about the query to be executed
     * @return string containing the query's result message
     */
    public static String videoMostViewedQuery(final ActionInputData query) {
        Map<String, Double> videoViewMap = new HashMap<String, Double>();
        // Filter videos by year and genre specified in query
        ArrayList<Video> filteredVideos = Database.filterVideos(query);

        filteredVideos.forEach(video -> {
            String videoType = Database.getVideoType(video.getTitle());
            int viewNumber = video.getViewNumber();

            // If the video is of the type specified in query, add it to the map
            // with entry (video title, video's view number)
            if (viewNumber != 0 && videoType.equals(query.getObjectType())) {
                videoViewMap.put(video.getTitle(), (double) viewNumber);
            }
        });
        // Get the query's result message (sort the map and get the first N videos' names)
        return getQueryMessage(query, videoViewMap);
    }

    /**
     * Sorts in a given order all users by their number of given ratings and returns first N
     * of them, N specified in input
     * @param query information about the query to be executed
     * @return string containing the query's result message
     */
    public static String userMostRatingsQuery(final ActionInputData query) {
        Map<String, Double> userRatingMap = new HashMap<String, Double>();

        Database.getDatabaseInstance().getUsers().forEach(user -> {
            int numberOfRatings = user.getNumberOfRatings();
            // If the user has given at least a rating, add it to the map with
            // entry (username, number of ratings)
            if (numberOfRatings != 0) {
                userRatingMap.put(user.getUsername(), (double) numberOfRatings);
            }
        });
        // Get the query's result message (sort the map and get the first N videos' names)
        return getQueryMessage(query, userRatingMap);
    }

    /**
     * Sorts a (String, Double) map first by value, then by key and creates the result message
     * @param action information about the action
     * @param map the map to be sorted
     * @return string containing a query's result message (a list)
     */
    private static String getQueryMessage(final ActionInputData action,
                                         final Map<String, Double> map) {
        // Sort the entries of the map in the specified order
        ArrayList<Map.Entry<String, Double>> sortedItems;
        sortedItems = SortingHandler.customSortEntries(map, action.getSortType());

        ArrayList<String> queryMessage = new ArrayList<String>();

        int itemCount = action.getNumber();

        int currentCount = 1;
        for (Map.Entry<String, Double> entry : sortedItems) {
            if (currentCount > itemCount) {
                break;
            }
            // Add the entry key to the result message list
            queryMessage.add(entry.getKey());
            ++currentCount;
        }

        return "Query result: " + queryMessage;
    }
}
