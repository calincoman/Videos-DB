package solve;

import action.Command;
import action.Query;
import action.Recommendation;
import common.Constants;
import fileio.ActionInputData;

/**
 * Class used to process an action's type and call the corresponding functions in order
 * to execute it
 */
public class ActionHandler {

    /**
     * Determines the type of the action and calls the corresponding execute function
     * @param action action to be executed
     * @return result message of the action
     */
    public String executeAction(final ActionInputData action) {
        final String actionType = action.getActionType();

        return switch (actionType) {

            case Constants.COMMAND -> executeCommand(action);
            case Constants.QUERY -> executeQuery(action);
            case Constants.RECOMMENDATION -> executeRecommendation(action);
            default -> Constants.WRONG_ACTION;
        };
    }

    /**
     * Determines the type of the command action and calls the corresponding function
     * @param command command to be executed
     * @return result message of the command
     */
    private String executeCommand(final ActionInputData command) {
        final String commandType = command.getType();

        return switch (commandType) {

            case Constants.FAVORITE_COMMAND -> Command.favoriteCommand(command);
            case Constants.VIEW_COMMAND -> Command.viewCommand(command);
            case Constants.RATING_COMMAND -> Command.ratingCommand(command);
            default -> Constants.INVALID_COMMAND;
        };
    }

    /**
     * Determines the type of the query action and calls the corresponding function
     * @param query query to be executed
     * @return result message of the query
     */
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

    /**
     * Determines the type of the recommendation action and calls the corresponding function
     * @param recommendation recommendation to be executed
     * @return result message of the recommendation
     */
    private String executeRecommendation(final ActionInputData recommendation) {

        return switch (recommendation.getType()) {

            case Constants.STANDARD_RECOMMENDATION ->
                    Recommendation.standardRecommendation(recommendation);
            case Constants.BEST_UNSEEN_RECOMMENDATION ->
                    Recommendation.bestUnseenRecommendation(recommendation);
            case Constants.POPULAR_RECOMMENDATION ->
                    Recommendation.popularRecommendation(recommendation);
            case Constants.FAVORITE_RECOMMENDATION ->
                    Recommendation.favoriteRecommendation(recommendation);
            case Constants.SEARCH_RECOMMENDATION ->
                    Recommendation.searchRecommendation(recommendation);
            default -> Constants.INVALID_RECOMMENDATION;
        };
    }

}
