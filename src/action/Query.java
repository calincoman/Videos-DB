package action;

import actor.Actor;
import common.Constants;
import database.Database;
import entertainment.Video;
import fileio.ActionInputData;
import solve.SortingHandler;
import utils.Utils;

import java.util.*;

public class Query {

    public static String actorAverageQuery(final ActionInputData query) {
        Map<String, Double> actorRatingMap = new HashMap<String, Double>();

        for (Actor actor : Database.getDatabaseInstance().getActors()) {
            Double actorRating = actor.getRating();

            if (actorRating != null) {
                actorRatingMap.put(actor.getName(), actorRating);
            }
        }

        return getQueryMessage(query, actorRatingMap);
    }

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
            if (hasAllAwards) {
                actorFilteredMap.put(actor.getName(), (double) actor.getAwardsNumber());
            }
        }
        return getQueryMessage(query, actorFilteredMap);
    }

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
            if (hasAllKeyWords) {
                actorFilteredList.add(actor.getName());
            }
        }
        if (query.getSortType().equals(Constants.ASCENDING)) {
            Collections.sort(actorFilteredList);
        } else {
            actorFilteredList.sort(Collections.reverseOrder());
        }
        return "Query result: " + actorFilteredList;
    }

    public static String videoRatingQuery(final ActionInputData query) {
        Map<String, Double> videoRatingMap = new HashMap<String, Double>();
        ArrayList<Video> filteredVideos = Database.filterVideos(query);

        filteredVideos.forEach(video -> {
            Double videoRating = video.getRating();
            String videoType = Database.getVideoType(video.getTitle());

            if (videoRating != null && videoType.equals(query.getObjectType())) {
                videoRatingMap.put(video.getTitle(), videoRating);
            }
        });
        return getQueryMessage(query, videoRatingMap);
    }

    public static String videoFavoriteQuery(final ActionInputData query) {
        Map<String, Double> videoFavoriteMap = new HashMap<String, Double>();
        ArrayList<Video> filteredVideos = Database.filterVideos(query);

        filteredVideos.forEach(video -> {
            int markedAsFavoriteNumber = video.getMarkedAsFavoriteNumber();
            String videoType = Database.getVideoType(video.getTitle());

            if (markedAsFavoriteNumber != 0 && videoType.equals(query.getObjectType())) {
                videoFavoriteMap.put(video.getTitle(), (double) markedAsFavoriteNumber);
            }
        });
        return getQueryMessage(query, videoFavoriteMap);
    }

    public static String videoLongestQuery(final ActionInputData query) {
        Map<String, Double> videoDurationMap = new HashMap<String, Double>();
        ArrayList<Video> filteredVideos = Database.filterVideos(query);

        filteredVideos.forEach(video -> {
            String videoType = Database.getVideoType(video.getTitle());

            if (videoType.equals(query.getObjectType())) {
                videoDurationMap.put(video.getTitle(), (double) video.getDuration());
            }
        });
        return getQueryMessage(query, videoDurationMap);
    }

    public static String videoMostViewedQuery(final ActionInputData query) {
        Map<String, Double> videoViewMap = new HashMap<String, Double>();
        ArrayList<Video> filteredVideos = Database.filterVideos(query);

        filteredVideos.forEach(video -> {
            String videoType = Database.getVideoType(video.getTitle());
            int viewNumber = video.getViewNumber();

            if (viewNumber != 0 && videoType.equals(query.getObjectType())) {
                videoViewMap.put(video.getTitle(), (double) viewNumber);
            }
        });
        return getQueryMessage(query, videoViewMap);
    }

    public static String userMostRatingsQuery(final ActionInputData query) {
        Map<String, Double> userRatingMap = new HashMap<String, Double>();

        Database.getDatabaseInstance().getUsers().forEach(user -> {
            int numberOfRatings = user.getNumberOfRatings();
            if (numberOfRatings != 0) {
                userRatingMap.put(user.getUsername(), (double) numberOfRatings);
            }
        });
        return getQueryMessage(query, userRatingMap);
    }

    private static String getQueryMessage(final ActionInputData action,
                                         final Map<String, Double> map) {
        ArrayList<Map.Entry<String, Double>> sortedItems;
        sortedItems = SortingHandler.customSortEntries(map, action.getSortType());

        ArrayList<String> queryMessage = new ArrayList<String>();

        int itemCount = action.getNumber();

        int currentCount = 1;
        for (Map.Entry<String, Double> entry : sortedItems) {
            if (currentCount > itemCount) {
                break;
            }
            queryMessage.add(entry.getKey());
            ++currentCount;
        }

        return "Query result: " + queryMessage;
    }
}
