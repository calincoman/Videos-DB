# Videos DB - Coman Calin-Alexandru 321CA

## Package Structure and Class Description

* action :
  * Classes in this package are final utilitary classes, they are not instantiated. 
  * **Command** - class used for command action operations, has static
                  methods for each of the favorite, view and rating commands.
  * **Query** - class used for query action operations, contains static methods
                which execute the queries.
  * **Recommendation** - class used for recommendation operations, contains
                   static methods which do the recommendations.
* actor
    * **Actor** - class that defines an actor, contains specific fields and
                  methods which are called on an actor.
    * **ActorsAwards** - enum containing actors awards.
* checker
    * checker files
* common
    * **Constants** - class containing constant values used in the program.
* database
    * **Database** - *singleton* class which stores the data used in the program.
    * **DatabaseLoader** - class that loads and unloads the database.
    * **DatabaseSearch** - class used for searching data from the database.
* entertainment
    * **Video** - *abstract* class that defines a video (a video can be a movie
                  or a show). Has common methods for movie and shows and abstract
                  methods which will be implemented in the movie and show classes.
    * **Movie** - class that extends the Video class and defines a movie.
    * **Show** - class that extends the Video class and defines a show. A show
                 has more seasons.
    * **Season** - class that defines a season.
    * **Genre** - enum containing video genres.
* fileio
    * contains file input / output logic (JSON parsing, data read and write etc).
    * **ActionInputData**, **ActorInputData**, **MovieInputData** etc. - classes
       containing the input information about an action, an actor, a movie etc.
* main
    * contains classes which run the program (the main method).
* solve
    * **InputHandler** - class that iterates through the input actions, calls
                         the action handler for every one of them and builds the
                         output.
    * **ActionHandler** - class that manages the action execution. Determines the
                          type of the action and according to its parameters calls
                          the corresponding methods from the action package.
* user
    * **User** - class that defines a user. Has different methods that apply on
                 a user.
* utils
    * **Utils** - utilitary class containing static methods used in the program.

## Implementation Details

* The Database is using the *singleton* pattern with lazy instantiation, only one instance
  is created throughout the execution of the program.

* The operations are mostly executed on the videos list of the database (which contains
  references of type Video to objects of type Movie and Show). Basically, the videos list
  has all the objects of the movie and show lists, merged, in the order they appeared in
  the input.

* This is possible because the **Movie** and **Show** classes extend the **Video** abstract
  class and thus, instead of operating on two separate lists (movies and shows), the program
  works only on the videos list.
  
* When sorting by a certain criteria, I used a **Map<String, Double>** where the value
  is the criteria and the key is usually a name or title (actor name, video title etc).
  This way, when wanting to sort items by a criteria, the Map is populated and its entries
  sorted, first by value, then by key.

* Streams are used in some parts of the code to reduce and simplify the implementation.

## Flow of the Program

Entry point:
* Main class - runs the program, uses the file input/output classes from fileio
               package for reading and writing, loads the Database with the input
               data, executes the actions and writes their result messages in
               the output.

How it works:
* First the input info (actors, movies, shows, users) is loaded into the database
  by the **DatabaseLoader**.
* Then, the InputHandler goes through all actions and calls the **ActionHandler** in
  order to execute the action.
* The ActionHandler determines what type of action it is (command, query, recommendation)
  and then the subtype (actor average guery, search recommendation etc.).
* After determining the action type, the ActionHandler calls the corresponding methods
  from the action classes (actorAverageQuery, searchRecommendation etc.) to execute the action.
* The methods return the result message, that gets passed to the ActionHandler and then
  to the InputHandler, which creates the output in arrayResult.
* After all the actions are executed, the Database is unloaded by the **DatabaseLoader**.

