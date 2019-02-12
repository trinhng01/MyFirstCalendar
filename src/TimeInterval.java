import sun.jvm.hotspot.memory.LoaderConstraintEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * HashMap to store calendar
 */
public class TimeInterval {
    private LocalDate startTime;
    private LocalDate endTime;

    /** Constructors */
    public TimeInterval(){}
    public TimeInterval(LocalDate startTime, LocalDate endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /** Accessors & Mutators */
    public LocalDate getStartTime() { return startTime; }

    public void setStartTime(LocalDate startTime) { this.startTime = startTime; }

    public LocalDate getEndTime() { return endTime; }

    public void setEndTime(LocalDate endTime) { this.endTime = endTime; }

    //Check time overlap


}
