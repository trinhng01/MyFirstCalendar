
/**
 * 2 types of events
 *      regular event and one-time event
 */
import java.time.LocalDate;
public class Event {
    private String eventName;
    private TimeInterval timeInterval;

    /** Constructors */
    public Event(){}

    public Event(String eventName,TimeInterval timeInterval) {
        this.eventName = eventName;
        this.timeInterval = timeInterval;
    }

    /** Accessors & Mutators */
    public String getEventName() { return eventName; }

    public void setEventName(String eventName) { this.eventName = eventName; }

    public TimeInterval getTimeInterval() { return timeInterval; }

    public void setTimeInterval(TimeInterval timeInterval) { this.timeInterval = timeInterval; }

}
