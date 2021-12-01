package action;

import java.util.ArrayList;
import java.util.List;

public abstract class Action {
    /**
     * Action id
     */
    private final int actionId;
    /**
     * Type of action
     */
    private final String actionType;
    /**
     * Used only for commands
     */
    private final String type;
    /**
     * Username of user making the action
     */
    private final String username;
    /**
     * The type of object on which the actions will be performed (for queries)
     */
    private final String objectType;
    /**
     * Sorting type: ascending or descending (for queries and recommendations)
     */
    private final String sortType;
    /**
     * The criteria according to which the sorting will be performed
     */
    private final String criteria;
    /**
     * Video title
     */
    private final String title;
    /**
     * Video genre
     */
    private final String genre;
    /**
     * Query limit
     */
    private final int number;
    /**
     * Grade for rating - i.e. value of the rating
     */
    private final double grade;
    /**
     * Season number
     */
    private final int seasonNumber;
    /**
     * Filters used for selecting videos
     */
    private final List<List<String>> filters = new ArrayList<>();

    protected Action(final int actionId, final String actionType, final String type,
                     final String username, final String objectType, final String sortType,
                     final String criteria, final String title, final String genre,
                     final int number, final double grade, final int seasonNumber) {
        this.actionId = actionId;
        this.actionType = actionType;
        this.type = type;
        this.username = username;
        this.objectType = objectType;
        this.sortType = sortType;
        this.criteria = criteria;
        this.title = title;
        this.genre = genre;
        this.number = number;
        this.grade = grade;
        this.seasonNumber = seasonNumber;
    }

    public final int getActionId() {
        return actionId;
    }

    public final String getActionType() {
        return actionType;
    }

    public final String getType() {
        return type;
    }

    public final String getUsername() {
        return username;
    }

    public final String getObjectType() {
        return objectType;
    }

    public final String getSortType() {
        return sortType;
    }

    public final String getCriteria() {
        return criteria;
    }

    public final String getTitle() {
        return title;
    }

    public final String getGenre() {
        return genre;
    }

    public final int getNumber() {
        return number;
    }

    public final double getGrade() {
        return grade;
    }

    public final int getSeasonNumber() {
        return seasonNumber;
    }

    public final List<List<String>> getFilters() {
        return filters;
    }
}
