import java.util.ArrayList;
import java.util.Arrays;

/**
 * A possible command that can be executed by Zenerbot.
 * Each command can expect parameters, but the base command is not case-sensitive.
 */
public enum Command {

    /** bye: exits program */
    BYE {
        @Override
        void execute(Zenerbot bot, String[] params) {
            System.out.println("Goodbye! It was a nice chat! :)");
            bot.terminate();
        }
    },

    /** list: display all tasks */
    LIST {
        @Override
        void execute(Zenerbot bot, String[] params) {
            ArrayList<Task> tasks = bot.getTasks();
            if (tasks.size() == 0) {
                System.out.println("No tasks today. Take a break!");
                return;
            }

            System.out.println("These are your current tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + 1 + ". " + tasks.get(i));
            }
        }
    },

    /** mark: mark a task as done */
    MARK {
        @Override
        void execute(Zenerbot bot, String[] params) {
            ArrayList<Task> tasks = bot.getTasks();
            int i = Integer.parseInt(params[0]);
            Task task = tasks.get(i - 1);
            task.setDone(true);

            if (!bot.isInit()) {
                System.out.println("Good work. This task is done:");
                System.out.println("\t" + task);

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

            System.out.println("I will mark this as undone for now:");
            System.out.println("\t" + task);

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
                System.out.println("I have created a new task for you:");
                System.out.println("\t" + todo);
                System.out.println("There are now " + tasks.size() + " task(s) to be done.");

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
            if (b == -1 || b == params.length - 1) {
                throw new InvalidTaskException();
            }

            ArrayList<Task> tasks = bot.getTasks();
            String name = String.join(" ", Arrays.copyOfRange(params, 0, b));
            String by = String.join(" ", Arrays.copyOfRange(params, b + 1, params.length));
            Deadline deadline = new Deadline(name, by);
            tasks.add(deadline);

            if (!bot.isInit()) {
                System.out.println("I have created a new task for you:");
                System.out.println("\t" + deadline);
                System.out.println("There are now " + tasks.size() + " task(s) to be done.");

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
            if (f == -1 || f == params.length - 1 || t == -1 || t == params.length - 1 || t - f <= 1) {
                throw new InvalidTaskException();
            }

            ArrayList<Task> tasks = bot.getTasks();
            String name = String.join(" ", Arrays.copyOfRange(params, 0, f));
            String from = String.join(" ", Arrays.copyOfRange(params, f + 1, t));
            String to = String.join(" ", Arrays.copyOfRange(params, t + 1, params.length));
            Event event = new Event(name, from, to);
            tasks.add(event);

            if (!bot.isInit()) {
                System.out.println("I have created a new task for you:");
                System.out.println("\t" + event);
                System.out.println("There are now " + tasks.size() + " task(s) to be done.");

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
            System.out.println("Noted with thanks. I have removed the task:");
            System.out.println("\t" + task);
            System.out.println("Now you are left with " + tasks.size() + " tasks.");
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
