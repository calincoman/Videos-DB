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

    public static void printData() {
        ArrayList<Actor> actors = Database.getDatabaseInstance().getActors();
        ArrayList<User> users = Database.getDatabaseInstance().getUsers();
        ArrayList<Video> videos = Database.getDatabaseInstance().getVideos();
        ArrayList<Movie> movies = Database.getDatabaseInstance().getMovies();
        ArrayList<Show> shows = Database.getDatabaseInstance().getShows();
    }

}
