import java.time.LocalDate;
import java.util.Scanner;

public class MyCalendarTester {

    public static void main(String[] args) throws Exception{
	// write your code here
        MyCalendar cal = new MyCalendar();
        Scanner s = new Scanner(System.in);
        LocalDate c = LocalDate.now();
        cal.printCalendar(c);
        cal.loadEvents();
        //cal.MainMenu(s);
        s.close();

    }

}


/**
 * Show the current month
 * Load Event from events.txt
 * Populate the calendar - Load events into calendar
 *
 */