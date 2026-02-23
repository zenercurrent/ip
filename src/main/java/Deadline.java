import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

/** Deadline tasks have a date/time associated to it. */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Instantiates a new Deadline task.
     *
     * @param name The name of the task
     * @param byStr The datetime when the task is due (as string)
     */
    public Deadline(String name, String byStr) {
        super(name);

        LocalDateTime dt;
        try {
            // can take in either date (as d/M/yyyy) or time (as HHmm) or both
            // defaults to 2359/today
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .optionalStart()
                        .appendPattern("d/M/yyyy")
                        .optionalStart()
                            .appendLiteral(' ')
                            .appendPattern("HHmm")
                        .optionalEnd()
                    .optionalEnd()
                    .optionalStart()
                        .appendPattern("HHmm")
                    .optionalEnd()
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 23)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 59)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();
            TemporalAccessor parsed = formatter.parse(byStr);

            LocalDate date;
            if (parsed.isSupported(ChronoField.YEAR)) {
                date = LocalDate.from(parsed);
            } else {
                date = LocalDate.now();
            }
            LocalTime time = LocalTime.from(parsed);
            dt = LocalDateTime.of(date, time);

        } catch (DateTimeParseException e) {
            System.out.println("Invalid datetime!");
            System.out.println("Defaulting to today 2359.");

            dt = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59));
        }

        this.by = dt;
    }

    @Override
    public String toCommandString() {
        return "deadline " + this.getName() + " /by " + this.by.format(this.formatter);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by.format(this.formatter) + ")";
    }
}
