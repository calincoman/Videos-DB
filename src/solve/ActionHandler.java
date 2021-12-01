package solve;

import action.Action;
import action.Command;
import action.Query;
import action.Recommendation;
import common.Constants;
import fileio.ActionInputData;

public class ActionHandler {

    public String executeAction(ActionInputData action) {
        final String actionType = action.getActionType();

        return switch(actionType) {
            case Constants.COMMAND -> executeCommand(action);
            case Constants.QUERY -> executeQuery(action);
            case Constants.RECOMMENDATION -> executeRecommendation(action);
            default -> Constants.WRONG_ACTION;
        };
    }

    private String executeCommand(final ActionInputData command) {
        final String commandType = command.getType();

        return switch(commandType) {
            case Constants.FAVORITE_COMMAND -> Command.favoriteCommand(command);
            case Constants.VIEW_COMMAND -> Command.viewCommand(command);
            case Constants.RATING_COMMAND -> Command.ratingCommand(command);
            default -> Constants.INVALID_COMMAND;
        };
    }

    private String executeQuery(final ActionInputData query) {
        final String queryCriteria = query.getCriteria();

        return switch (query.getObjectType()) {
            case Constants.ACTORS -> switch (queryCriteria) {
                case Constants.AVERAGE -> Query.actorAverageQuery(query);
                case Constants.AWARDS -> Query.actorAwardQuery(query);
                case Constants.FILTER_DESCRIPTIONS -> Query.actorFilterQuery(query);
                default -> Constants.INVALID_QUERY;
            };
            case Constants.MOVIES, Constants.SHOWS -> switch (queryCriteria) {
                case Constants.RATING_QUERY -> Query.videoRatingQuery(query);
                case Constants.FAVORITE_QUERY -> Query.videoFavoriteQuery(query);
                case Constants.LONGEST_QUERY -> Query.videoLongestQuery(query);
                case Constants.MOST_VIEWED_QUERY -> Query.videoMostViewedQuery(query);
                default -> Constants.INVALID_QUERY;
            };
            case Constants.USERS -> switch (queryCriteria) {
                case Constants.NUM_RATINGS -> Query.userMostRatingsQuery(query);
                default -> Constants.INVALID_QUERY;
            };
            default -> Constants.INVALID_QUERY;
        };
    }

    private String executeRecommendation(final ActionInputData recommendation) {

        return switch(recommendation.getType()) {
            case Constants.STANDARD_RECOMMENDATION -> Recommendation.standardRecommendation(recommendation);
            case Constants.BEST_UNSEEN_RECOMMENDATION -> Recommendation.bestUnseenRecommendation(recommendation);
            case Constants.POPULAR_RECOMMENDATION -> Recommendation.popularRecommendation(recommendation);
            case Constants.FAVORITE_RECOMMENDATION -> Recommendation.favoriteRecommendation(recommendation);
            case Constants.SEARCH_RECOMMENDATION -> Recommendation.searchRecommendation(recommendation);
            default -> Constants.INVALID_RECOMMENDATION;
        };
    }

}
