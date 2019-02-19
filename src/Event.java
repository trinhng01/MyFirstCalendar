
/**
 * Event has event name and time interval
 */
import java.io.Serializable;
import java.time.LocalDateTime;
public class Event implements Serializable {
    private String eventName;
    private TimeInterval timeInterval;

    /** Constructors */
    public Event(){
        timeInterval = new TimeInterval();
    }

    /**
     * Construct Event with specific event name, LocalDate starting time and ending time
     */
    public Event(String eventName, LocalDateTime start, LocalDateTime end) {
        this.eventName = eventName;
        timeInterval = new TimeInterval();
        this.timeInterval.setStartTime(start);
        this.timeInterval.setEndTime(end);
    }

    /**
     * Construct Event with specific event name and TimeInterval object
     */
    public Event(String eventName,TimeInterval timeInterval) {
        this.eventName = eventName;
        this.timeInterval = timeInterval;
    }

    /** Accessors & Mutators */

    public String getEventName() { return eventName; }

    public void setEventName(String eventName) { this.eventName = eventName; }

    public TimeInterval getTimeInterval() { return timeInterval; }

    public void setTimeInterval(TimeInterval timeInterval) { this.timeInterval = timeInterval; }

    public LocalDateTime getStartTime() { return this.timeInterval.getStartTime();  }

    public void setStartTime(LocalDateTime startTime) { this.timeInterval.setStartTime(startTime);  }

    public void setEndTime(LocalDateTime endTime) { this.timeInterval.setEndTime(endTime);  }

    public LocalDateTime getEndTime() { return this.timeInterval.getEndTime();  }

    /** Print Functions */
    public void print(){
        System.out.print(eventName + ": ");
        timeInterval.print();
    }
    public void printDate(){
        System.out.print(eventName + ": ");
        timeInterval.printDate();
    }

    /** toString function to write object to text file */
    @Override
    public String toString() {
        return "Event name: " + eventName + "\n" + timeInterval.toString();
    }
}
