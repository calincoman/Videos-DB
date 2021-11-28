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
import user.User;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {
    public InputHandler() {
    }

    public static void executeActions(final Input input, final Writer fileWriter) {
        List<ActionInputData> actions = input.getCommands();
        ActionHandler actionHandler = new ActionHandler();

        for (ActionInputData actionInputData : actions) {
            String resultMessage = actionHandler.executeAction(actionInputData);
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
