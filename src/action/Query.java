package action;

import actor.Actor;
import common.Constants;
import database.Database;
import entertainment.Video;
import fileio.ActionInputData;
import solve.SortingHandler;
import utils.DatabaseSearch;
import utils.Utils;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.*;

public class Query {

    public static String actorAverageQuery(final ActionInputData query) {
        Map<String, Double> actorRatingMap = new HashMap<String, Double>();
        for (Actor actor : Database.getDatabaseInstance().getActors()) {
            actorRatingMap.put(actor.getName(), actor.getRating());
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
            for (String keyWord : keyWords) {
                if (actor.getCareerDescription().toLowerCase().contains(keyWord.toLowerCase())) {
                    actorFilteredList.add(actor.getName());
                } else {
                    break;
                }
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
        for (Video video : Database.getDatabaseInstance().getVideos()) {
            Double videoRating = video.getRating();
            if (videoRating != null) {
                videoRatingMap.put(video.getTitle(), videoRating);
            }
        }
        return getQueryMessage(query, videoRatingMap);
    }

    public static String videoFavoriteQuery(final ActionInputData query) {
        Map<String, Double> videoFavoriteMap = new HashMap<String, Double>();
        Database.getDatabaseInstance().getVideos().forEach(video -> {
            videoFavoriteMap.put(video.getTitle(), (double) video.getMarkedAsFavoriteNumber());
        });
        return getQueryMessage(query, videoFavoriteMap);
    }

    public static String videoLongestQuery(final ActionInputData query) {
        Map<String, Double> videoDurationMap = new HashMap<String, Double>();
        Database.getDatabaseInstance().getVideos().forEach(video -> {
            videoDurationMap.put(video.getTitle(), (double) video.getDuration());
        });
        return getQueryMessage(query, videoDurationMap);
    }

    public static String videoMostViewQuery(final ActionInputData query) {
        Map<String, Double> videoViewMap = new HashMap<String, Double>();
        Database.getDatabaseInstance().getVideos().forEach(video -> {
            videoViewMap.put(video.getTitle(), (double) video.getViewNumber());
        });
        return getQueryMessage(query, videoViewMap);
    }

    private static String getQueryMessage(final ActionInputData action,
                                         final Map<String, Double> map) {
        Map<String, Double> sortedItems;
        sortedItems = SortingHandler.sortMap(map, action.getSortType());

        ArrayList<String> queryMessage = new ArrayList<String>();

        int itemCount = action.getNumber();


        int currentCount = 1;
        for (Map.Entry<String, Double> entry : sortedItems.entrySet()) {
            if (currentCount > itemCount) {
                break;
            }
            queryMessage.add(entry.getKey());
            ++currentCount;
        }

        return "Query result: " + queryMessage;
    }
}
