package solve;

import fileio.ActionInputData;
import fileio.Input;
import fileio.Writer;
import org.json.simple.JSONArray;
import java.io.IOException;

import java.util.List;

/**
 * Class used to handle the input and output
 */
public final class InputHandler {
    private InputHandler() {
    }

    /**
     * Executes the actions from the input by iterating through them and calling the action
     * handler for each one of them and adds the result messages to arrayResult
     * @param input the inout
     * @param fileWriter file writer used for output
     * @param arrayResult result array containing the result messages
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static void executeActions(final Input input, final Writer fileWriter,
                                      final JSONArray arrayResult) throws IOException {
        List<ActionInputData> actions = input.getCommands();
        ActionHandler actionHandler = new ActionHandler();

        /**
         * Iterate through the actions and execute them one by one, then write the result message to
         * the arrayResult
         */
        for (ActionInputData actionInputData : actions) {
            String resultMessage = actionHandler.executeAction(actionInputData);
            arrayResult.add(fileWriter.writeFile(actionInputData.getActionId(), "", resultMessage));
        }
    }
}
