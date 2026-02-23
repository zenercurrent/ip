import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

/** Event tasks start at a specific datetime and ends at a specific date/time. */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Instantiates a new Event task.
     *
     * @param name The name of the task
     * @param fromStr The datetime when the event starts (as string)
     * @param toStr The datetime when the event ends (as string)
     */
    public Event(String name, String fromStr, String toStr) {
        super(name);

        LocalDateTime dtFrom, dtTo;
        try {
            // can take in either date (as d/M/yyyy) or time (as HHmm) or both
            // defaults to today
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
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();
            TemporalAccessor parsedFrom = formatter.parse(fromStr);
            TemporalAccessor parsedTo = formatter.parse(toStr);

            LocalDate dateFrom, dateTo;
            if (parsedFrom.isSupported(ChronoField.YEAR)) {
                dateFrom = LocalDate.from(parsedFrom);
            } else {
                dateFrom = LocalDate.now();
            }
            if (parsedTo.isSupported(ChronoField.YEAR)) {
                dateTo = LocalDate.from(parsedFrom);
            } else {
                dateTo = LocalDate.now();
            }
            LocalTime timeFrom = LocalTime.from(parsedFrom);
            LocalTime timeTo = LocalTime.from(parsedTo);
            dtFrom = LocalDateTime.of(dateFrom, timeFrom);
            dtTo = LocalDateTime.of(dateTo, timeTo);

        } catch (DateTimeParseException e) {
            System.out.println("Invalid datetime!");
            System.out.println("Defaulting to today.");

            dtFrom = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
            dtTo = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59));
        }

        // if "to" is before "from", auto swaps
        if (dtTo.isBefore(dtFrom)) {
            LocalDateTime tmp = dtTo;
            dtTo = dtFrom;
            dtFrom = tmp;
        }

        this.from = dtFrom;
        this.to = dtTo;
    }

    @Override
    public String toCommandString() {
        return "event " + this.getName() + " /from " + this.from.format(this.formatterCmd) + " /to " + this.to.format(this.formatterCmd);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(this.formatter) + ", to: " + this.to.format(this.formatter) + ")"
                + (this.to.isBefore(LocalDateTime.now()) ? " [expired!]" : "");
    }
}
