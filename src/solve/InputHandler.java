package solve;

import action.Command;
import actor.Actor;
import common.Constants;
import database.Database;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputHandler {
    public InputHandler() {
    }

    @SuppressWarnings("unchecked")
    public static void executeActions(final Input input, final Writer fileWriter,
                                      JSONArray arrayResult) throws IOException {
        List<ActionInputData> actions = input.getCommands();
        ActionHandler actionHandler = new ActionHandler();

        for (ActionInputData actionInputData : actions) {
            String resultMessage = actionHandler.executeAction(actionInputData);
            arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "", resultMessage));
        }
    }
}
