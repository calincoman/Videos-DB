package action;

import common.Constants;
import database.Database;
import entertainment.Video;
import fileio.ActionInputData;
import solve.SortingHandler;
import user.User;
import database.DatabaseSearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class Recommendation {
    private Recommendation() {
    }

    /**
     * Finds first video not seen by the user from the database
     * @param recommendation information about the recommendation to be executed
     * @return string containing the recommendation's result message
     */
    public static String standardRecommendation(final ActionInputData recommendation) {
        User user = DatabaseSearch.searchUser(recommendation.getUsername());

        for (Video video : Database.getDatabaseInstance().getVideos()) {
            if (!user.videoWasSeen(video.getTitle())) {
                return "StandardRecommendation result: " + video.getTitle();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    /**
     * Sorts all videos descending by their ratings and finds the first video (with the
     * biggest rating) not seen by the user
     * @param recommendation information about the recommendation to be executed
     * @return string containing the recommendation's result message
     */
    public static String bestUnseenRecommendation(final ActionInputData recommendation) {
        User user = DatabaseSearch.searchUser(recommendation.getUsername());

        ArrayList<Video> bestUnseenVideos = new ArrayList<Video>();

        Database.getDatabaseInstance().getVideos().forEach(video -> {
            // Filter videos
            if (!user.videoWasSeen(video.getTitle())) {
                bestUnseenVideos.add(video);
            }
        });
        // Sort unseen videos by their rating
        bestUnseenVideos.sort(Comparator.comparing(Video::getRatingNotNullable).reversed());

        for (Video video : bestUnseenVideos) {
            if (!user.videoWasSeen(video.getTitle())) {
                return "BestRatedUnseenRecommendation result: " + video.getTitle();
            }
        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }

    /**
     * Finds the first video from the most popular genre not seen by the user. If all videos from
     * the current genre are seen, it continues to the next most popular genre
     * Genre popularity is the total view number of the videos with the specified genre
     * @param recommendation information about the recommendation to be executed
     * @return string containing the recommendation's result message
     */
    public static String popularRecommendation(final ActionInputData recommendation) {
        User user = DatabaseSearch.searchUser(recommendation.getUsername());
        if (!user.getSubscriptionType().equals(Constants.PREMIUM_SUBSCRIPTION)) {
            return "PopularRecommendation cannot be applied!";
        }

        // Sort the map with entry (genre name, genre popularity) descending by popularity
        Map<String, Double> genrePopularity = Video.getGenrePopularity();
        ArrayList<Map.Entry<String, Double>> sortedGenres = SortingHandler.customSortEntries(
                genrePopularity, Constants.DESCENDING);

        // Get only unseen videos
        List<Video> unseenVideos = Database.getDatabaseInstance().getVideos().stream()
                .filter(video -> !user.videoWasSeen(video.getTitle()))
                .collect(Collectors.toList());

        // Iterate through the genres and find first unseen video with that genre
        for (Map.Entry<String, Double> genre : sortedGenres) {
            for (Video video : unseenVideos) {
                if (video.getGenres().contains(genre.getKey())) {
                    return "PopularRecommendation result: " + video.getTitle();
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    /**
     * Finds first unseen video with most appearances in the favorite list of the users
     * @param recommendation information about the recommendation to be executed
     * @return string containing the recommendation's result message
     */
    public static String favoriteRecommendation(final ActionInputData recommendation) {
        User user = DatabaseSearch.searchUser(recommendation.getUsername());
        if (!user.getSubscriptionType().equals(Constants.PREMIUM_SUBSCRIPTION)) {
            return "FavoriteRecommendation cannot be applied!";
        }

        // Get unseen videos and sort them by number of appearances in favorite lists
        List<Video> unseenFavoriteVideos = Database.getDatabaseInstance().getVideos().stream()
                .filter(video -> !user.videoWasSeen(video.getTitle()))
                .sorted(Comparator.comparing(Video::getMarkedAsFavoriteNumber).reversed())
                .collect(Collectors.toList());

        if (!unseenFavoriteVideos.isEmpty()) {
            return "FavoriteRecommendation result: " + unseenFavoriteVideos.get(0).getTitle();
        }
        return "FavoriteRecommendation cannot be applied!";
    }

    /**
     * Finds all unseen videos which have the genre specified in the input and sort them
     * ascendingly by their rating
     * @param recommendation information about the recommendation to be executed
     * @return string containing the recommendation's result message
     */
    public static String searchRecommendation(final ActionInputData recommendation) {
        User user = DatabaseSearch.searchUser(recommendation.getUsername());
        if (!user.getSubscriptionType().equals(Constants.PREMIUM_SUBSCRIPTION)) {
            return "SearchRecommendation cannot be applied!";
        }

        String genre = recommendation.getGenre();

        // Get unseen videos that have the specified genre and sort them by their rating and title
        ArrayList<String> result = Database.getDatabaseInstance().getVideos().stream()
                .filter(video -> !user.videoWasSeen(video.getTitle()) && video.getGenres()
                        .contains(genre)).sorted(Comparator.comparing(Video::getRatingNotNullable)
                        .thenComparing(Video::getTitle)).map(Video::getTitle)
                        .collect(Collectors.toCollection(ArrayList::new));

        if (result.isEmpty()) {
            return "SearchRecommendation cannot be applied!";
        }
        return "SearchRecommendation result: " + result;
    }
}
