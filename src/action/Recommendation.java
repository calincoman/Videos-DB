package action;

import common.Constants;
import database.Database;
import entertainment.Video;
import fileio.ActionInputData;
import solve.SortingHandler;
import user.User;
import database.DatabaseSearch;

import java.util.*;
import java.util.stream.Collectors;

public class Recommendation {

    public static String standardRecommendation(final ActionInputData recommendation) {
        User user = DatabaseSearch.searchUser(recommendation.getUsername());

        for (Video video : Database.getDatabaseInstance().getVideos()) {
            if (!user.videoWasSeen(video.getTitle())) {
                return "StandardRecommendation result: " + video.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    public static String bestUnseenRecommendation(final ActionInputData recommendation) {
        User user = DatabaseSearch.searchUser(recommendation.getUsername());

        ArrayList<Video> bestUnseenVideos = new ArrayList<Video>();

        Database.getDatabaseInstance().getVideos().forEach(video -> {
            if (!user.videoWasSeen(video.getTitle())) {
                bestUnseenVideos.add(video);
            }
        });
        bestUnseenVideos.sort(Comparator.comparing(Video::getRatingNotNullable).reversed());

        for (Video video : bestUnseenVideos) {
            if (!user.videoWasSeen(video.getTitle())) {
                return "BestRatedUnseenRecommendation result: " + video.getTitle();
            }
        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    public static String popularRecommendation(final ActionInputData recommendation) {
        User user = DatabaseSearch.searchUser(recommendation.getUsername());
        if (!user.getSubscriptionType().equals(Constants.PREMIUM_SUBSCRIPTION)) {
            return "PopularRecommendation cannot be applied!";
        }

        Map<String, Double> genrePopularity = Video.getGenrePopularity();
        ArrayList<Map.Entry<String, Double>> sortedGenres = SortingHandler.customSortEntries(
                genrePopularity, Constants.DESCENDING);

        List<Video> unseenVideos = Database.getDatabaseInstance().getVideos().stream()
                .filter(video -> !user.videoWasSeen(video.getTitle()))
                .collect(Collectors.toList());

        for (Map.Entry<String, Double> genre : sortedGenres) {
            for (Video video : unseenVideos) {
                if (video.getGenres().contains(genre.getKey())) {
                    return "PopularRecommendation result: " + video.getTitle();
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    public static String favoriteRecommendation(final ActionInputData recommendation) {
        User user = DatabaseSearch.searchUser(recommendation.getUsername());
        if (!user.getSubscriptionType().equals(Constants.PREMIUM_SUBSCRIPTION)) {
            return "FavoriteRecommendation cannot be applied!";
        }

        List<Video> unseenFavoriteVideos = Database.getDatabaseInstance().getVideos().stream()
                .filter(video -> !user.videoWasSeen(video.getTitle()))
                .sorted(Comparator.comparing(Video::getMarkedAsFavoriteNumber).reversed())
                .collect(Collectors.toList());

        if (!unseenFavoriteVideos.isEmpty()) {
            return "FavoriteRecommendation result: " + unseenFavoriteVideos.get(0).getTitle();
        }
        return "FavoriteRecommendation cannot be applied!";
    }

    public static String searchRecommendation(final ActionInputData recommendation) {
        User user = DatabaseSearch.searchUser(recommendation.getUsername());
        if (!user.getSubscriptionType().equals(Constants.PREMIUM_SUBSCRIPTION)) {
            return "SearchRecommendation cannot be applied!";
        }

        String genre = recommendation.getGenre();

        ArrayList<String> result = Database.getDatabaseInstance().getVideos().stream()
                .filter(video -> !user.videoWasSeen(video.getTitle()) && video.getGenres().contains(genre))
                .sorted(Comparator.comparing(Video::getRatingNotNullable).thenComparing(Video::getTitle))
                .map(Video::getTitle)
                .collect(Collectors.toCollection(ArrayList::new));

        if (result.isEmpty()) {
            return "SearchRecommendation cannot be applied!";
        }
        return "SearchRecommendation result: " + result;
    }
}
