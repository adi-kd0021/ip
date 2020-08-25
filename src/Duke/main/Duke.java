package Duke.main;

import Duke.exception.DukeException;
import Duke.task.Deadline;
import Duke.task.Event;
import Duke.task.Task;
import Duke.task.Todo;
import Duke.tasklist.TaskList;
import Duke.ui.Ui;
import Duke.parser.Parser;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Duke {

    private TaskList tasks;
    private Ui ui;

    public Duke() {
        ui = new Ui();
        tasks = new TaskList();
    }


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

                        } else {
                            ui.showToUser("No tasks found in the list!");
                        }
                        ui.printGoodBye();
                        break;
                    case "help":
                        ui.helpMessage();
                        break;
                    case "todo":
                        tasks.addTask(Parser.createTodo(fullCommand));
                        ui.showToUser(tasks.getTaskCount());
                        break;
                    case "deadline":
                        tasks.addTask(Parser.createDeadLine(fullCommand));
                        ui.showToUser(tasks.getTaskCount());
                        break;
                    case "print":
                        tasks.printTasks();
                        break;
                    case "done":
                        tasks.markAsDone(fullCommand);
                        ui.showToUser(tasks.getTaskCount());
                        break;
                    case "undone":
                        tasks.markAsNotDone(fullCommand);
                        ui.showToUser(tasks.getTaskCount());
                    case "save":
//                        storage.save(tasks.getTasks());
                        break;
//                    case "remove":
//                        tasks.removeTask(fullCommand);
//                        ui.showToUser(tasks.getTaskCount());
//                        break;
//                    case "backup":
//                        storage.createBackup(tasks.getTasks());
//                        break;
                    default:
                        ui.printError("Unknown command! please try again. Please type \"help\" to view a list of commands.");
                        break;
                }
            } catch (DukeException e) {
                ui.printError(e.getMessage());
            }
        }
        exit();
    }

    public static void main(String[] args) {
        new Duke().run();
    }

    public static void exit() {
//        System.out.println(0);
    }
}
    