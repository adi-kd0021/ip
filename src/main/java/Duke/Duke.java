package Duke;

import ui.Constants;
import ui.Parser;
import ui.Ui;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (FileNotFoundException | DukeException e) {

            if (e.toString().contains("FileNotFoundException")) {

            } else if (e.toString().contains("DukeException")) {
                ui.showToUser(Constants.FileNotReadableException);
            }
            tasks = new TaskList(new ArrayList<>());
        }

    }

    /**
     * Begin running main processing loop
     */
    public void run() {
        ui.printWelcome();

        boolean isExit = false;
        while (!isExit) {

            try {

                String fullCommand = ui.readUserCommand();
                String commandWord = Parser.getCommandWord(fullCommand);

                switch (commandWord) {
                    case "":
                        ui.printError("No command input! Please enter a command or type \"help\" to view a list of commands.");
                        break;

                    case "exit":
                        isExit = true;
                        if (tasks.getNumberOfTasks() > 0) {
                            storage.save(tasks.getTasks());
                        }
                        ui.endInputFeed();
                        ui.printGoodBye();
                        break;

                    case "help":
                        ui.helpMessage();
                        break;

                    case "todo":
                        tasks.addTask(Parser.createTodo(fullCommand));
                        ui.printTaskAddAck(tasks.getTasks().get(TaskList.getNumberOfTasks() - 1), TaskList.getNumberOfTasks());
                        storage.save(tasks.getTasks());
                        break;

                    case "deadline":
                        tasks.addTask(Parser.createDeadLine(fullCommand));
                        ui.printTaskAddAck(tasks.getTasks().get(TaskList.getNumberOfTasks() - 1), TaskList.getNumberOfTasks());
                        storage.save(tasks.getTasks());
                        break;

                    case "event":
                        tasks.addTask(Parser.createEvent(fullCommand));
                        ui.printTaskAddAck(tasks.getTasks().get(TaskList.getNumberOfTasks() - 1), TaskList.getNumberOfTasks());
                        storage.save(tasks.getTasks());
                        break;

                    case "print":
                        ui.printTasks(tasks.getTasks());
                        break;

                    case "done":
                        tasks.markAsDone(Parser.parseTaskNum(fullCommand));
                        ui.printTaskMarkedAsDone(tasks.getTasks().get(Parser.parseTaskNum(fullCommand) - 1));
                        storage.save(tasks.getTasks());
                        break;

                    case "remove":
                        tasks.removeTask(fullCommand);
                        ui.printNumberOfTasks(TaskList.getNumberOfTasks());
                        storage.save(tasks.getTasks());
                        break;

                    default:
                        ui.printError("Unknown command! please try again. Please type \"help\" to view a list of commands.");
                        break;
                }
            } catch (Exception e) {
                ui.printError(e.getMessage());
            }
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        new Duke("data/tasks.txt").run();
    }

}
    