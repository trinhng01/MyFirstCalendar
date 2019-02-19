/**
 * TimeInterval has starting time and ending of an Event
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * HashMap to store calendar
 */
public class TimeInterval implements Serializable {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /** Constructors */
    public TimeInterval(){}

    /** Accessors & Mutators */
    public LocalDateTime getStartTime() { return startTime; }

    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }

    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    /** Print Functions */
    public void print(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(" " + formatter.format(startTime) + "-" + formatter.format(endTime));
    }
    public void printDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("\nE, MM/dd/yyyy, HH:mm");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(" " + formatter.format(startTime) + "-" + formatter2.format(endTime));
    }
    /** toString function to write object to text file */
    @Override
    public String toString() {
        return startTime.toString()
                + " - " + endTime.toString();
    }
}
