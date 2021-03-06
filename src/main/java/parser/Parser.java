package parser;

import data.Deadline;
import data.Event;
import data.Todo;
import exception.DukeException;
import ui.Ui;

/**
 * Code structure referenced from
 * https://github.com/se-edu/addressbook-level2/blob/master/src/seedu/addressbook/parser/Parser.java
 */
public class Parser {

    /**
     * Split the user input and return the command action to process
     *
     * @param fullCommand
     * @return
     */
    public static String getCommandWord(String fullCommand) {
        String command = fullCommand.trim().split(" ")[0];
        return command;
    }

    public static String getKeyword(String fullCommand) {
        String keyword = fullCommand.trim().split(" ")[1];
        return keyword;
    }

    /**
     * Creates a Todo object and returns it.
     *
     * @param fullCommand
     * @return Todo
     * @throws DukeException
     */
    public static Todo createTodo(String fullCommand) {
        String description = fullCommand.substring("todo".length()).trim();
        return new Todo(description);
    }

    /**
     * Creates a Deadline object and returns it
     *
     * @param fullCommand
     * @return Deadline
     * @throws DukeException
     */
    public static Deadline createDeadLine(String fullCommand) {
        int idxOfBy = fullCommand.indexOf("/by");
        if (idxOfBy < 0) {
            Ui.printError("Deadline does not contain /by");
        }

        String description = fullCommand.substring(0, idxOfBy).substring("deadline".length()).trim();
        if (description.isEmpty() || description.equals("")) {
            Ui.printError("Empty description for DEADLINE");
        }
        String deadline = fullCommand.substring(idxOfBy, fullCommand.length()).substring("/by".length()).trim();
        if (deadline.isEmpty() || deadline.equals("")) {
            Ui.printError("Empty deadline for DEADLINE");
        }
        return new Deadline(description, deadline);
    }

    /**
     * Creates a Event object and returns it
     *
     * @param fullCommand
     * @return Event
     * @throws DukeException
     */
    public static Event createEvent(String fullCommand) {
        int idxOfBy = fullCommand.indexOf("/at");
        if (idxOfBy < 0) {
            Ui.printError("Event does not contain /at");
        }

        String description = fullCommand.substring(0, idxOfBy).substring("event".length()).trim();
        if (description.isEmpty() || description.equals("")) {
            Ui.printError("Empty description for EVENT");
        }
        String schedule = fullCommand.substring(idxOfBy, fullCommand.length()).substring("/at".length()).trim();
        if (schedule.isEmpty() || schedule.equals("")) {
            Ui.printError("Empty schedule for EVENT");
        }
        return new Event(description, schedule);
    }

    /**
     * Parses the user input for the idx of the Task and returns as Int
     *
     * @param fullCommand
     * @return
     */
    public static int parseTaskNum(String fullCommand) {
        int index = fullCommand.contains("undone")
                ? Integer.parseInt(fullCommand.substring("undone".length()).trim())
                : Integer.parseInt(fullCommand.substring("done".length()).trim());
        return index;
    }

    /**
     * Returns the index of the task to be modified.
     * @param fullCommand
     * @return
     */
    public static int parseTagTaskNum(String fullCommand) {
        String idxCommand = fullCommand;

        if (fullCommand.contains("tag") && !fullCommand.contains("untag")) {
            idxCommand = fullCommand.substring(0, fullCommand.indexOf("#") - 1);
        }

        int index = idxCommand.contains("untag")
                ? Integer.parseInt(fullCommand.substring("untag".length()).trim())
                : Integer.parseInt(idxCommand.substring("tag".length()).trim());
        return index;
    }

    /**
     * Returns the description of the tag.
     * @param fullCommand
     * @return
     */
    public static String parseTagTaskDescription(String fullCommand) {
        String description = "";

        description = fullCommand.substring(fullCommand.lastIndexOf("#") + 1);

        return description;
    }

}
