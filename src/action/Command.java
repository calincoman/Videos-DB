package action;

import common.Constants;
import database.Database;
import entertainment.Movie;
import entertainment.Show;
import fileio.ActionInputData;
import user.User;
import database.DatabaseSearch;

import java.util.ArrayList;

public class Command {
    public Command() {
    }

    public static String favoriteCommand(final ActionInputData command) {
        User user = DatabaseSearch.searchUser(command.getUsername());
        if (user == null) {
            return Constants.INVALID_USER;
        }
        String videoTitle = command.getTitle();
        if (user.getHistory().containsKey(videoTitle)) {
            if (user.getFavoriteVideos().contains(videoTitle)) {
                return "error -> " + videoTitle + " is already in favourite list";
            }
            user.getFavoriteVideos().add(videoTitle);
            return "success -> " + videoTitle + " was added as favourite";
        }
        return "error -> " + videoTitle + " is not seen";
    }

    public static String viewCommand(final ActionInputData command) {
        User user = DatabaseSearch.searchUser(command.getUsername());
        if (user == null) {
            return Constants.INVALID_USER;
        }
        String videoTitle = command.getTitle();
        user.getHistory().put(videoTitle, user.getHistory().getOrDefault(videoTitle, 0) + 1);

        return "success -> " + videoTitle + " was viewed with total views of "
                + user.getHistory().get(videoTitle);
    }

    public static String ratingCommand(final ActionInputData command) {
        User user = DatabaseSearch.searchUser(command.getUsername());
        if (user == null) {
            return Constants.INVALID_USER;
        }

        String videoTitle = command.getTitle();
        int seasonNumber = command.getSeasonNumber();
        if (!user.videoWasSeen(videoTitle)) {
            return "error -> " + videoTitle + " is not seen";
        }
        if (user.getRatedVideos().containsKey(videoTitle)
                && user.getRatedVideos().get(videoTitle).contains(seasonNumber)) {
            return "error -> " + videoTitle + " has been already rated";
        }
        if (!user.getRatedVideos().containsKey(videoTitle)) {
            user.getRatedVideos().put(videoTitle, new ArrayList<Integer>());
        }
        ArrayList<Integer> ratedSeasons = user.getRatedVideos().get(videoTitle);

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
            ratedMovie.getRatings().add(command.getGrade());
        } else {

            final Show ratedShow = DatabaseSearch.searchShow(videoTitle);
            if (ratedShow == null) {
                return "error -> " + videoTitle + " is not seen";
            }
            ratedShow.getSeasons().get(seasonNumber - 1).getRatings().add(command.getGrade());
        }
        return "success -> " + videoTitle + " was rated with " + command.getGrade()
                + " by " + command.getUsername();
    }
}
