package zener;

import zener.exceptions.InvalidTaskException;
import zener.exceptions.UnknownCommandException;
import zener.tasks.Deadline;
import zener.tasks.Event;
import zener.tasks.Task;
import zener.tasks.Todo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A possible command that can be executed by zener.Zenerbot.
 * Each command can expect parameters, but the base command is not case-sensitive.
 */
public enum Command {

    /** bye: exits program */
    BYE {
        @Override
        void execute(Zenerbot bot, String[] params) {
            bot.terminate();
        }
    },

    /** list: display all tasks */
    LIST {
        @Override
        void execute(Zenerbot bot, String[] params) {
            ArrayList<Task> tasks = bot.getTasks();
            if (tasks.size() == 0) {
                bot.print("No tasks today. Take a break!");
                return;
            }

            bot.print("These are your current tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                bot.print(i + 1 + ". " + tasks.get(i));
            }
        }
    },

    /** mark: mark a task as done */
    MARK {
        @Override
        void execute(Zenerbot bot, String[] params) {

            ArrayList<Task> tasks = bot.getTasks();
            int i = Integer.parseInt(params[0]);
            // check out of bounds
            if (i > tasks.size()) {
                bot.print("zener.tasks.Task does not exist!");
                bot.print("There are only " + tasks.size() + " tasks in the list currently.");
                return;
            }

            Task task = tasks.get(i - 1);
            task.setDone(true);

            if (!bot.isInit()) {
                bot.print("Good work. This task is done:");
                bot.print("\t" + task);

                bot.save();
            }
        }

        @Override
        public boolean isScriptable() {
            // to allow persistence of completed tasks
            return true;
        }
    },

    /** unmark: mark a task as not done */
    UNMARK {
        @Override
        void execute(Zenerbot bot, String[] params) {
            ArrayList<Task> tasks = bot.getTasks();
            int i = Integer.parseInt(params[0]);
            Task task = tasks.get(i - 1);
            task.setDone(false);

            bot.print("I will mark this as undone for now:");
            bot.print("\t" + task);

            bot.save();
        }
    },

    /** todo: create a new todo task */
    TODO {
        @Override
        void execute(Zenerbot bot, String[] params) {
            if (params.length == 0) {
                throw new InvalidTaskException();
            }

            ArrayList<Task> tasks = bot.getTasks();
            Todo todo = new Todo(String.join(" ", params));
            tasks.add(todo);

            if (!bot.isInit()) {
                bot.print("I have created a new task for you:");
                bot.print("\t" + todo);
                bot.print("There are now " + tasks.size() + " task(s) to be done.");

                bot.save();
            }
        }

        @Override
        public boolean isScriptable() {
            return true;
        }
    },

    /** deadline: create a new deadline task */
    DEADLINE {
        @Override
        void execute(Zenerbot bot, String[] params) {
            int b = Arrays.asList(params).indexOf("/by");
            if (b == 1 || b == -1 || b == params.length - 1) {
                throw new InvalidTaskException();
            }

            ArrayList<Task> tasks = bot.getTasks();
            String name = String.join(" ", Arrays.copyOfRange(params, 0, b));
            String by = String.join(" ", Arrays.copyOfRange(params, b + 1, params.length));
            Deadline deadline = new Deadline(name, by);
            tasks.add(deadline);

            if (!bot.isInit()) {
                bot.print("I have created a new task for you:");
                bot.print("\t" + deadline);
                bot.print("There are now " + tasks.size() + " task(s) to be done.");

                bot.save();
            }
        }

        @Override
        public boolean isScriptable() {
            return true;
        }
    },

    /** event: create a new event task */
    EVENT {
        @Override
        void execute(Zenerbot bot, String[] params) {
            int f = Arrays.asList(params).indexOf("/from");
            int t = Arrays.asList(params).indexOf("/to");
            if (f == 1 || f == -1 || f == params.length - 1 || t == -1 || t == params.length - 1 || t - f <= 1) {
                throw new InvalidTaskException();
            }

            ArrayList<Task> tasks = bot.getTasks();
            String name = String.join(" ", Arrays.copyOfRange(params, 0, f));
            String from = String.join(" ", Arrays.copyOfRange(params, f + 1, t));
            String to = String.join(" ", Arrays.copyOfRange(params, t + 1, params.length));
            Event event = new Event(name, from, to);
            tasks.add(event);

            if (!bot.isInit()) {
                bot.print("I have created a new task for you:");
                bot.print("\t" + event);
                bot.print("There are now " + tasks.size() + " task(s) to be done.");

                bot.save();
            }
        }

        @Override
        public boolean isScriptable() {
            return true;
        }
    },

    /** delete: remove a task from the list */
    DELETE {
        @Override
        void execute(Zenerbot bot, String[] params) {
            ArrayList<Task> tasks = bot.getTasks();
            int i = Integer.parseInt(params[0]);   // todo: handle possible exception here
            Task task = tasks.get(i - 1);
            tasks.remove(i - 1);
            bot.print("Noted with thanks. I have removed the task:");
            bot.print("\t" + task);
            bot.print("Now you are left with " + tasks.size() + " tasks.");
        }
    };


    /**
     * Gets a command based on a string (non-case-sensitive).
     *
     * @param cmd the string
     * @return the command enum
     */
    public static Command fromString(String cmd) {
        try {
            return Command.valueOf(cmd.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnknownCommandException();
        }
    }


    /**
     * Executes the defined behaviour of the command.
     * Can take in parameters.
     *
     * @param params the command parameters
     */
    abstract void execute(Zenerbot bot, String[] params);

//    /**
//     * Gets the instructions and syntax of the command.
//     *
//     * @return the command instructions
//     */
//    abstract String getManual();

    /**
     * <p>A command is considered <b>scriptable</b>
     * if it is allowed to be executed through parsing from a text file.</p>
     * <p>It is set to <i>false</i> by default.</p>
     *
     * @return whether the command is scriptable
     */
    public boolean isScriptable() {
        return false;
    }
}
