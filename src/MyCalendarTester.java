/**
 * MyFirsCalendar
 * @author Trinh Nguyen
 * @version 1.0 02/16/19
 * */

/**
 * main class to print calendar + load events + view/modify calendar
 * */
import java.time.LocalDate;
import java.util.Scanner;

public class MyCalendarTester {
    public static void main(String[] args) throws Exception{
        MyCalendar cal = new MyCalendar();
        Scanner s = new Scanner(System.in);
        LocalDate c = LocalDate.now();
        //cal.printCalendar(c);
        cal.loadEvents();
        cal.MainMenu(s);
        s.close();
    }
}
