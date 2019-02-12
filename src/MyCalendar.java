/**
 * HashMap to store calendar
 */
 import java.time.LocalDate;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Scanner;
 import java.io.*;

public class MyCalendar {

    //HashMap to hold all events
    private ArrayList<Event> events = new ArrayList<Event>();
    private HashMap<LocalDate, ArrayList<Event>> map = new HashMap<LocalDate, ArrayList<Event>>();

    /** Constructors */
    public MyCalendar(){}

    /** Accessors & Mutators */

    /**
     * Print calendar
     * @param cal - which month to print calendar
     */
    public void printCalendar(LocalDate cal){
        //Should I check for leap year?
        System.out.println("\t\t" + cal.getMonth()+ " " + cal.getYear());
        System.out.println("\nSun  Mon  Tue  Wed  Thu  Fri  Sat ");

        int [] numDays = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int month = cal.getMonthValue();

        LocalDate firstDay = LocalDate.of(cal.getYear(), cal.getMonth(), 1);
        //Get the integer value of dayofweek for the first day of the month
        int d = firstDay.getDayOfWeek().getValue();

        //Print the calendar
        for (int i = 0; i < d; i++) System.out.print("     ");
        for (int i = 1; i <= numDays[month]; i++) {
            //Highlight days with event
            //if (has_event)
            if (i==cal.getDayOfMonth())  System.out.printf("[%2d] ", i);
            else System.out.printf(" %2d  ", i);
            if (((i + d) % 7 == 0) || (i == numDays[month])) System.out.println();
        }
    }

    public void loadEvents() throws Exception{
        //Read from text file
        File file = new File("/Users/TrinhNg/Desktop/IntelliJ Workspace/MyFirstCalendar/src/events.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String str;
        int counter = 1;

        while ((str = br.readLine()) != null) {
            Event newEvent = new Event();
            System.out.println(str);
            //Modify the string
//            LocalDate date = LocalDate.of();

            if (counter%2!=0){
                newEvent.setEventName(str);
                //System.out.println("-------Event Name: "+newEvent.getEventName());
            } else if (counter%2==0){
                //2 TYPE OF EVENTS
                //One-Time - Date in the beginning
                //Regular - Letter in the beginning
                events.add(newEvent);
            }


        }
    }

    /**
     * Main Menu
     */
    public void MainMenu(Scanner s){
        System.out.println("Enter your choice: ");
        String choice = s.nextLine();
        char option = choice.trim().toUpperCase().charAt(0);


        if(option =='V'){           //View
            ViewMenu(s);

        }else if (option =='C'){    //Create

        }else if (option =='G'){    //Go to
            //Enter a date
            //Display day view and all events on that day
            //Display all events on that day IN ORDER of starting time
        }else if (option =='E'){    //Event list
            //Display all events schedule in the calendar
            //ORDER: Starting date, Starting time --> Sorting Algorithm
            //
        }else if (option =='D'){    //Delete
            //selected
            //all
        }else if (option =='Q'){    //Quit

        }else System.out.println("Invalid Option\n");
    }

    /**
     * View Menu: View by Day or Month
     */
    public void ViewMenu(Scanner s){
        //View by Day or Month
        System.out.println("Enter your choice: ");
        String choice = s.nextLine();
        char option = choice.trim().toUpperCase().charAt(0);
        if(option =='D'){   //Day View
            //Display today and its events

            //How do I go back to Main Menu
        }else if (option =='M'){    //Month View
            //Display Current Month -- Print Calendar
            //Highlight day {} on days have events
            //P,N,G
        }else if (option =='G'){

        }

    }
    public void SubViewMenu(char option, Scanner s){
        System.out.println("Enter your choice: ");
        String choice = s.nextLine();
        char option2 = choice.trim().toUpperCase().charAt(0);
        if(option2 =='P'){          //Previous
            if(option == 'D'){

            }else if (option == 'M'){

            }

        }else if (option2 =='N'){   //Next

        }else if (option2 =='G'){   //Go back to Main Menu
            return;
        }
    }



}

