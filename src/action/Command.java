package action;

import common.Constants;
import database.Database;
import entertainment.Movie;
import entertainment.Show;
import fileio.ActionInputData;
import user.User;
import database.DatabaseSearch;

import java.util.ArrayList;

/**
 * Utility class used for the command operations
 */
public final class Command {
    private Command() {
    }

    /**
     * Adds a video to the user's favorite list
     * @param command information about the command to be executed
     * @return string containing the command's result message
     */
    public static String favoriteCommand(final ActionInputData command) {
        // Get the user from the database
        User user = DatabaseSearch.searchUser(command.getUsername());
        if (user == null) {
            return Constants.INVALID_USER;
        }
        String videoTitle = command.getTitle();
        if (user.getHistory().containsKey(videoTitle)) {
            // Check if video is not already in the favorite list
            if (user.getFavoriteVideos().contains(videoTitle)) {
                return "error -> " + videoTitle + " is already in favourite list";
            }
            user.getFavoriteVideos().add(videoTitle);
            return "success -> " + videoTitle + " was added as favourite";
        }
        return "error -> " + videoTitle + " is not seen";
    }

    /**
     * Adds a video to the user's view history
     * @param command information about the command to be executed
     * @return string containing the command's result message
     */
    public static String viewCommand(final ActionInputData command) {
        // Get the user from the database
        User user = DatabaseSearch.searchUser(command.getUsername());
        if (user == null) {
            return Constants.INVALID_USER;
        }
        String videoTitle = command.getTitle();
        user.getHistory().put(videoTitle, user.getHistory().getOrDefault(videoTitle, 0) + 1);

        return "success -> " + videoTitle + " was viewed with total views of "
                + user.getHistory().get(videoTitle);
    }

    /**
     * Adds a rating to a video (movie / season of a show)
     * @param command information about the command to be executed
     * @return string containing the command's result message
     */
    public static String ratingCommand(final ActionInputData command) {
        // Get the user from the database
        User user = DatabaseSearch.searchUser(command.getUsername());
        if (user == null) {
            return Constants.INVALID_USER;
        }

        String videoTitle = command.getTitle();
        int seasonNumber = command.getSeasonNumber();
        // Check if the video was not seen or was already rated
        if (!user.videoWasSeen(videoTitle)) {
            return "error -> " + videoTitle + " is not seen";
        }
        if (user.getRatedVideos().containsKey(videoTitle)
                && user.getRatedVideos().get(videoTitle).contains(seasonNumber)) {
            return "error -> " + videoTitle + " has been already rated";
        }
        // Add an entry to the user's rated videos map
        if (!user.getRatedVideos().containsKey(videoTitle)) {
            user.getRatedVideos().put(videoTitle, new ArrayList<Integer>());
        }
        ArrayList<Integer> ratedSeasons = user.getRatedVideos().get(videoTitle);

        // For a movie the default season number is 0
        if (ratedSeasons.isEmpty()) {
            ratedSeasons.add(seasonNumber);
        } else if (seasonNumber != 0) {
            ratedSeasons.add(seasonNumber);
        }
        String videoType = Database.getVideoType(videoTitle);
        if (videoType.equals(Constants.MOVIES)) {
            final Movie ratedMovie = DatabaseSearch.searchMovie(videoTitle);
            if (ratedMovie == null) {
                return "error -> " + videoTitle + " is not seen";
            }
            // Add rating to the movie's ratings list
            ratedMovie.getRatings().add(command.getGrade());
        } else {

            final Show ratedShow = DatabaseSearch.searchShow(videoTitle);
            if (ratedShow == null) {
                return "error -> " + videoTitle + " is not seen";
            }
            // Add rating to the season's rating list
            ratedShow.getSeasons().get(seasonNumber - 1).getRatings().add(command.getGrade());
        }
        return "success -> " + videoTitle + " was rated with " + command.getGrade()
                + " by " + command.getUsername();
    }
}
