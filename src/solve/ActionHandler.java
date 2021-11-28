package solve;

import action.Action;
import action.Command;
import common.Constants;
import fileio.ActionInputData;

public class ActionHandler {

    public String executeAction(ActionInputData action) {
        final String actionType = action.getActionType();
        return switch(actionType) {
            case Constants.COMMAND -> executeCommand(action);
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

}
