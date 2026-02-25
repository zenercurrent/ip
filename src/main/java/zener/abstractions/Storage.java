package zener.abstractions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import zener.Zenerbot;
import zener.tasks.Task;
import zener.tasks.TaskList;

/**
 * Manages the storing/saving from a file saved on hard disk.
 */
public class Storage {
    private final String saveLocation;

    public Storage(String saveLocation) {
        this.saveLocation = saveLocation;
    }

    /**
     * Loads data from a location in the hard disk to a specified TaskList.
     *
     * @param tasks the TaskList to be loaded
     * @throws IOException if file cannot be created
     */
    public void load(TaskList tasks) throws IOException {
        File filePtr = new File(this.saveLocation);
        Scanner file;
        try {
            file = new Scanner(filePtr);
        } catch (FileNotFoundException e) {
            // ask user for permission to create
            System.out.println("Save file '" + this.saveLocation + "' does not exist!");
            System.out.println("Could not load tasks...");
            System.out.println("Create file in save location? (yes/no)");
            Scanner scan = new Scanner(System.in);
            if (scan.nextLine().equalsIgnoreCase("yes")) {
                if (filePtr.getParentFile() != null) {
                    filePtr.getParentFile().mkdirs();
                }
                if (filePtr.createNewFile()) {
                    System.out.println("Save file created at: '" + filePtr.getAbsolutePath() + "'.");
                } else {
                    System.out.println("File actually already exists?? This shouldn't happen...");
                }
            }
            file = new Scanner(filePtr);
        }

        int failures = 0;
        while (file.hasNextLine()) {
            String cmd = file.nextLine();
            boolean success = Zenerbot.getInstance().exec(cmd);
            if (!success) {
                System.out.println("Failed at: '" + cmd + "'");
                failures++;
            }
        }

        System.out.println("Load completed!");
        if (failures > 0) {
            System.out.println("Failed to import " + failures + " line(s).");
        }
        System.out.println("Loaded " + tasks.size() + " task(s) successfully.");
    }

    /**
     * Saves data from a specified TaskList to a location in a hard disk.
     * Overwrites existing data!
     *
     * @param tasks the TaskList to save from
     */
    public void save(TaskList tasks) {
        try (FileWriter file = new FileWriter(this.saveLocation);) {
            int i = 0;
            for (Task t : tasks) {
                i++;
                file.write(t.toCommandString() + "\n");
                if (t.isDone()) {
                    file.write("mark " + i + "\n");
                }
            }

        } catch (IOException e) {
            System.out.println("Save file could not be opened/created due to an unknown error.");
            System.out.println("Save failed!");
        }
    }
}
