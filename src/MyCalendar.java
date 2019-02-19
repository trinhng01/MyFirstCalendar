/**
 * MyCalendar has information of all events and allows user to view and modify events
 */

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

public class MyCalendar {
    private HashMap<LocalDate, ArrayList<Event>> map = new HashMap<LocalDate, ArrayList<Event>>();

    /**
     * Constructors
     */
    public MyCalendar() {
    }

    /**
     * Main Menu
     * @param s - Scanner for user input
     */
    public void MainMenu(Scanner s) throws IOException {
        String choice;
        char option;
        do {
            System.out.println("\nSelect one of the following options:\n" +
                    "[V]iew by [C]reate [G]o to [E]vent list [D]elete [Q]uit");
            System.out.print("\nEnter your choice (V,C,G,E,D,Q): ");
            choice = s.nextLine();
            option = choice.trim().toUpperCase().charAt(0);
            if (option == 'V') {           //View
                ViewMenu(s);
            } else if (option == 'C') {    //Create
                create(s);
            } else if (option == 'G') {    //Go to
                goTo(s);
            } else if (option == 'E') {    //Event list
                eventList();
            } else if (option == 'D') {    //Delete
                deleteMenu(s);
            } else if (option == 'Q') {    //Quit
                outputFile();
                System.out.println("Good Bye!");
                System.exit(0);
            } else System.out.println("Invalid Option\n");
        }while(option != 'Q');
    }

    /**
     * Print calendar given the date
     * @param cal - display the month of that LocalDate
     */
    public void printCalendar(LocalDate cal) {
        LocalDate today = LocalDate.now();
        int[] numDays = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (cal.isLeapYear()) numDays[2]=29;
        System.out.println("\n\t\t" + cal.getMonth() + " " + cal.getYear());
        System.out.println("\nSun  Mon  Tue  Wed  Thu  Fri  Sat ");

        int month = cal.getMonthValue();
        //Get the integer value of day of week for the first day of the month
        LocalDate day = LocalDate.of(cal.getYear(), cal.getMonth(), 1);
        int d = day.getDayOfWeek().getValue();

        //Print the calendar
        for (int i = 0; i < d; i++) System.out.print("     ");
        for (int i = 1; i <= numDays[month]; i++) {
            //Highlight days with event
            if (i == cal.getDayOfMonth() && month == today.getMonthValue() && map.containsKey(day)) System.out.printf("{[%2d]} ", i);
            else if (i == cal.getDayOfMonth() && month == today.getMonthValue()) System.out.printf("[%2d] ", i);
            else if (map.containsKey(day)) System.out.printf("{%2d} ", i);
            else System.out.printf(" %2d  ", i);
            if (((i + d) % 7 == 0) || (i == numDays[month])) System.out.println();
            day = day.plusDays(1);
        }
    }

    /**
     * Load all event from input file to HashMap
     */
    public void loadEvents() throws Exception {
        File file = new File("/Users/TrinhNg/Desktop/IntelliJ Workspace/MyFirstCalendar/src/events.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String str;
        LocalDateTime start, end;
        String eventName = "";
        int counter = 1;

        while ((str = br.readLine()) != null) {
            if (counter % 2 != 0) {
                eventName = str;

            } else if (counter % 2 == 0) {
                int month, day, year, hour, min;
                String repeat;

                // One-Time Event
                if (StringUtils.isNumeric(str.subSequence(0, 1))) {
                    month = Integer.parseInt(str.substring(0, str.indexOf('/')));
                    day = Integer.parseInt(str.substring(str.indexOf('/') + 1, str.indexOf('/', str.indexOf('/') + 1)));
                    year = 2000+Integer.parseInt(str.substring(str.indexOf('/', str.indexOf('/') + 1) + 1, str.indexOf(' ')));
                    hour = Integer.parseInt(str.substring(str.indexOf(' ') + 1, str.indexOf(':', str.indexOf(' '))));
                    min = Integer.parseInt(str.substring(str.indexOf(':') + 1, str.indexOf(' ', str.indexOf(':'))));

                    start = LocalDateTime.of(year, month, day, hour, min);

                    hour = Integer.parseInt(str.substring(str.indexOf(' ', str.indexOf(':')) + 1, str.lastIndexOf(':')));
                    min = Integer.parseInt(str.substring(str.lastIndexOf(':') + 1));
                    end = LocalDateTime.of(year, month, day, hour, min);

                    addMap(start, new Event(eventName, start, end));


                }
                // Regular Event
                else if (StringUtils.isAlpha(str.subSequence(0, 1))) {
                    repeat = str.substring(0, str.indexOf(' '));

                    hour = Integer.parseInt(str.substring(str.indexOf(' ') + 1, str.indexOf(':')));
                    min = Integer.parseInt(str.substring(str.indexOf(':') + 1, str.indexOf(' ', str.indexOf(':'))));

                    month = Integer.parseInt((str.substring(str.indexOf(' ', str.lastIndexOf(":")), str.indexOf('/'))).trim());
                    day = Integer.parseInt(str.substring(str.indexOf('/') + 1, str.indexOf('/', str.indexOf('/')+1)));
                    year = 2000+Integer.parseInt(str.substring(str.indexOf('/', str.indexOf('/') + 1) + 1, str.lastIndexOf(' ')));

                    start = LocalDateTime.of(year, month, day, hour, min);    //Starting date + Starting time

                    hour = Integer.parseInt((str.substring(str.lastIndexOf(":")-2, str.lastIndexOf(":"))).trim());
                    min = Integer.parseInt((str.substring(str.lastIndexOf(":")+1, str.indexOf(' ', str.lastIndexOf(":"))).trim()));

                    end = LocalDateTime.of(year, month, day, hour, min);      //Starting date + Starting time
                    addMap(start, new Event(eventName, start, end));

                    month = Integer.parseInt((str.substring(str.lastIndexOf(' ')+1, str.indexOf('/', str.lastIndexOf(' ')))).trim());
                    day = Integer.parseInt(str.substring(str.indexOf('/', str.lastIndexOf(' ')) + 1, str.lastIndexOf('/')));
                    year = 2000+Integer.parseInt(str.substring(str.lastIndexOf('/')+1));

                    end = LocalDateTime.of(year, month, day, hour, min);
                    LocalDateTime eDay = end;
                    LocalDateTime sDay = start;

                    for (int i = 0; i < repeat.length(); i++) {

                        if (repeat.toUpperCase().charAt(i) == 'M'){
                            sDay = changeDay(sDay, 1);
                            createRegularEvent(eventName, sDay, eDay);
                        } else if (repeat.toUpperCase().charAt(i) == 'T') {
                            sDay = changeDay(sDay, 2);
                            createRegularEvent(eventName, sDay, eDay);
                        } else if (repeat.toUpperCase().charAt(i) == 'W') {
                            sDay = changeDay(sDay, 3);
                            createRegularEvent(eventName, sDay, eDay);
                        } else if (repeat.toUpperCase().charAt(i) == 'R') {
                            sDay = changeDay(sDay, 4);
                            createRegularEvent(eventName, sDay, eDay);
                        } else if (repeat.toUpperCase().charAt(i) == 'F') {
                            sDay = changeDay(sDay, 5);
                            createRegularEvent(eventName, sDay, eDay);
                        } else if (repeat.toUpperCase().charAt(i) == 'A') {
                            sDay = changeDay(sDay, 6);
                            createRegularEvent(eventName, sDay, eDay);
                        } else if (repeat.toUpperCase().charAt(i) == 'S') {
                            sDay = changeDay(sDay, 7);
                            createRegularEvent(eventName, sDay, eDay);
                        }
                    }
                }
            }
            counter++;
        }
    }

    /**
     * Check for existing key and add new event to HashMap
     * @param newKey - key of new event
     * @param newEvent - new event to be added to HashMap
     */
    public void addMap(LocalDateTime newKey, Event newEvent){
        LocalDate key = newKey.toLocalDate();
        if (map.containsKey(key)){
            if (checkConflict(key,newEvent))
                map.get(key).add(newEvent);
            else {
                System.out.println("Conflicted Event!");
            }
        }else{
            ArrayList<Event> newList = new ArrayList<Event>();
            newList.add(newEvent);
            map.put(key, newList);
        }

    }

    /**
     * Find the correct day of the week to populate Regular Event
     * @param day - current day
     * @param end - day of the week
     */
    public LocalDateTime changeDay(LocalDateTime day, int end){
        while (day.getDayOfWeek().getValue() != end){
            day = day.plusDays(1);
        }
        return day;
    }

    /**
     * Add all Regular Events to HashMap
     * @param eventName - name of new event
     * @param startDate - starting time of new event
     * @param endDate - ending time of new event
     */
    public void createRegularEvent(String eventName, LocalDateTime startDate, LocalDateTime endDate){
        LocalDateTime curDate = startDate;
        while (curDate.toLocalDate().isBefore(endDate.toLocalDate())){
            curDate = curDate.plusDays(7);
            LocalDateTime endingTime = LocalDateTime.of(curDate.getYear(),curDate.getMonth(),curDate.getDayOfMonth(), endDate.getHour(), endDate.getMinute());
            addMap(curDate, new Event(eventName, curDate, endingTime));
        }
   }

    /**
     * View Menu: View by Day or Month
     * @param s - Scanner for user input
     */
    public void ViewMenu(Scanner s) {
        String choice;
        char option;
        LocalDate today = LocalDate.now();
        do {
            System.out.print("\n[D]ay view or [M]onth view or [G]o back to main menu" +
                    "\nEnter your choice (D,M,G): ");
            choice = s.nextLine();
            option = choice.trim().toUpperCase().charAt(0);
            if (option == 'D') {   //Day View
                printAllEvent(today);
                SubViewMenu(today, option, s);
            } else if (option == 'M') {    //Month View
                printCalendar(today);
                SubViewMenu(today, option, s);
            } else if (option == 'G') {
                break;
            }
        }while(option != 'G');
        return;
    }

    /**
     * Perform viewing previous/next month/day
     * @param today - print information of today
     * @param view - option view by Day or Month
     * @param s - Scanner for user input
     */
    public void SubViewMenu(LocalDate today, char view, Scanner s) {
        LocalDate curDay = today;
        LocalDate newDay;
        char option;
        String choice;

        do {
            System.out.print("\n[P]revious or [N]ext or [G]o back to main menu ?" +
                    "\nEnter your choice (P,N,G): ");
            choice = s.nextLine();
            option = choice.trim().toUpperCase().charAt(0);
            if (view == 'D') {                      //Day View
                if (option == 'P') {                //Previous
                    newDay = curDay.minusDays(1);
                    printAllEvent(newDay);
                    curDay = newDay;
                } else if (option == 'N') {         //Next
                    newDay = curDay.plusDays(1);
                    printAllEvent(newDay);
                    curDay = newDay;
                }
            } else if (view == 'M') {               //Month View
                if (option == 'P') {                //Previous
                    newDay = curDay.minusMonths(1);
                    printCalendar(newDay);
                    curDay = newDay;;
                } else if (option == 'N') {         //Next
                    newDay = curDay.plusMonths(1);
                    printCalendar(newDay);
                    curDay = newDay;
                }
            } else if (option == 'G') {             //Go back to Main Menu
                break;
            }
        }while(option != 'G');
        return;
    }
    /**
     * Prompt user for a specific date
     * @param s - Scanner for user input
     */
    public LocalDate chooseDate(Scanner s){
        System.out.print("\nEnter Event Date in format (MM/DD/YYYY): ");
        int month, day, year;
        String temp = s.nextLine();
        month = Integer.parseInt(temp.substring(0,2));
        day = Integer.parseInt(temp.substring(3,5));
        year = Integer.parseInt(temp.substring(6));
        LocalDate date = LocalDate.of(year, month, day);
        return date;
    }

    /**
     * Prompt user for specific event
     * @param s - Scanner for user input
     */
    public Event chooseEvent(Scanner s){
        int month, day, year, hour1, hour2, min1, min2;
        System.out.print("\nEnter Event Name: ");
        String name = s.nextLine();

        System.out.print("\nEnter Event Date in format (MM/DD/YYYY): ");
        String temp = s.nextLine();
        month = Integer.parseInt(temp.substring(0,2));
        day = Integer.parseInt(temp.substring(3,5));
        year = Integer.parseInt(temp.substring(6));

        System.out.print("\nEnter Event Starting Time in format (HH:MM): ");
        temp = s.nextLine();
        hour1 = Integer.parseInt(temp.substring(0,2));
        min1 = Integer.parseInt(temp.substring(3));

        System.out.print("\nEnter Event Ending Time in format (HH:MM): ");
        temp = s.nextLine();
        hour2 = Integer.parseInt(temp.substring(0,2));
        min2 = Integer.parseInt(temp.substring(3));

        LocalDateTime start = LocalDateTime.of(year,month,day,hour1, min1);
        LocalDateTime end = LocalDateTime.of(year,month,day,hour2, min2);
        Event newEvent = new Event (name, start, end);
        return newEvent;
    }

    /**
     * Create a new Event and add to HashMap
     * @param s - Scanner for user input
     */
    public void create(Scanner s){
        Event newEvent = chooseEvent(s);
        newEvent.printDate();
        addMap(newEvent.getStartTime(), newEvent);
    }

    /**
     * Check if new event is conflicted with existing ones
     * @param key - HashMap key
     * @param newEvent - new event to be added to HashMap
     */
    public boolean checkConflict(LocalDate key, Event newEvent){
        ArrayList<Event> list = map.get(key);
        for (int i = 0; i<list.size(); i++){
            Event exist = list.get(i);
            LocalDateTime existStart = exist.getStartTime();
            LocalDateTime existEnd = exist.getEndTime();

            if (existStart.getHour() == newEvent.getStartTime().getHour()) return false;
            if (existStart.getHour() == newEvent.getEndTime().getHour()) return false;
            if (existEnd.getHour() == newEvent.getEndTime().getHour()) return false;
            if (existEnd.getHour() == newEvent.getStartTime().getHour()) return false;
            if (existStart.getHour() < newEvent.getStartTime().getHour() && existEnd.getHour() > newEvent.getStartTime().getHour())
                return false;
            else if (existStart.getHour() > newEvent.getStartTime().getHour() && existStart.getHour() < newEvent.getEndTime().getHour())
                return false;
            else if (existEnd.getHour() > newEvent.getStartTime().getHour() && existEnd.getHour() < newEvent.getEndTime().getHour())
                return false;
        }
        return true;
    }

    /**
     * Go to a specific date asked by users
     * @param s - Scanner for user input
     */
    public void goTo(Scanner s){
        LocalDate date = chooseDate(s);
        sortArrayList();
        printAllEvent(date);
    }


    /**
     * List all events in starting date order
     */
    public void eventList (){
        map = sortHashMap();
        sortArrayList();
        int year = LocalDate.now().getYear();
        System.out.println("\n" + year);
        for (Map.Entry<LocalDate, ArrayList<Event>> event : map.entrySet()) {
            if (event.getKey().getYear() != year)
                System.out.println("\n" + event.getKey().getYear());
            printAllEvent(event.getKey());
        }
    }

    /**
     * Sort all events of by starting time
     */
    public void sortArrayList(){
        for (Map.Entry<LocalDate, ArrayList<Event>> eventList : map.entrySet()) {
            QuickSort(0, eventList.getValue().size()-1, eventList.getKey());
        }
    }


    /**
     * Perform viewing previous/next month/day
     */
    public HashMap<LocalDate, ArrayList<Event>> sortHashMap()
    {
        List<Map.Entry<LocalDate, ArrayList<Event>> > eventList =
                new LinkedList<Map.Entry<LocalDate, ArrayList<Event>> >(map.entrySet());

        //Using Comparator to sort the HashMap LocalData in order
        Collections.sort(eventList, new Comparator<Map.Entry<LocalDate, ArrayList<Event>> >() {
            public int compare(Map.Entry<LocalDate, ArrayList<Event>> date1, Map.Entry<LocalDate, ArrayList<Event>> date2)
            {
                return (date1.getKey().compareTo(date2.getKey()));
            }
        });

        HashMap<LocalDate, ArrayList<Event>> tempMap = new LinkedHashMap<>();
        for (Map.Entry<LocalDate, ArrayList<Event>> map : eventList) {
            tempMap.put(map.getKey(), map.getValue());
        }
        return tempMap;
    }

    /**
     * QuickSort_Keyword method sort ArrayList of Event
     * @param low - index of ArrayList
     * @param high - index of ArrayList
     * @param key - key of HashMap
     */
    private void QuickSort(int low, int high, LocalDate key){
        if (high == 1) return;
        if (low < high) {
            int q = Partition(low, high, key);
            QuickSort(low, q-1, key);
            QuickSort(q+1, high, key);
        }
    }

    /**
     * Partition_Keyword method rearranges the sub-ArrayList of Event
     * @param low - index of ArrayList
     * @param high - index of ArrayList
     * @param key - key of HashMap
     */
    private int Partition(int low, int high, LocalDate key){
        Event pivot = map.get(key).get(high);
        int index = low - 1; //Index of smaller element
        for (int i = low; i < high; i++) {
            if (map.get(key).get(i).getStartTime().getHour() <= pivot.getStartTime().getHour()) {
                index++;
                swapKey(index, i, key);
            }
        }
        swapKey(index + 1, high, key);
        return index + 1;
    }

    /**
     * Swap Events in ArrayList of Events
     * @param index1 - index of ArrayList
     * @param index2 - index of ArrayList
     * @param key - key of HashMap
     */
    private void swapKey(int index1, int index2, LocalDate key){
        Event temp = this.map.get(key).get(index1);
        this.map.get(key).set(index1, map.get(key).get(index2));
        this.map.get(key).set(index2, temp);
    }

    /**
     * Perform 4 options for delete
     * @param s - Scanner for user input
     */
    public void deleteMenu(Scanner s){
        String choice;
        char option;
        do {
            System.out.print("\n[S]elected or [A]ll or [DR]egular or [G]o back to main menu?" +
                    "\nEnter your choice (S,A,DR,G): ");
            choice = s.nextLine();
            option = choice.trim().toUpperCase().charAt(0);
            if (option == 'S') {           //Selected
                deleteEvent(option, s);
            } else if (option == 'A') {    //All
                deleteEvent(option, s);
            } else if (option == 'D') {    //DeleteRegular
                deleteRegular(s);
            } else if (option == 'G') {    //Quit
                break;
            } else System.out.println("Invalid Option\n");
        }while(option != 'G');
    }

    /**
     * Search for an event in Hashmap, return index if found or -1 if not found
     * @param searchDate - HashMap key
     * @param name - name of event to be searched
     */
    public int searchEvent(LocalDate searchDate, String name){
        for (int i = 0; i < map.get(searchDate).size(); i++) {
            if(map.get(searchDate).get(i).getEventName().compareTo(name) == 0)
                return i;
        }
        return -1;
    }

    /**
     * Check if event existed and delete single or all events
     * @param option - check whether user wants to delete selected or all events
     * @param s - Scanner for user input
     */
    public void deleteEvent(char option, Scanner s){
        LocalDate deleteEvent = chooseDate(s);
        printAllEvent(deleteEvent);
        if (option == 'S') {
            System.out.print("\nEnter the name of the event to delete: ");
            String name = s.nextLine();
            int action = searchEvent(deleteEvent, name);
            if (action == -1) System.out.println("Event not exist!");
            else {
                map.get(deleteEvent).get(action).print();
                map.get(deleteEvent).remove(action);
                System.out.println("\nThe event is deleted. Here is the current scheduled event:");
                printAllEvent(deleteEvent);
            }
        }else if (option == 'A'){
            if(map.containsKey(deleteEvent)) {
                map.remove(deleteEvent);
                System.out.println("The event(s) are deleted. Here is the current scheduled event:");
                printAllEvent(deleteEvent);
            }else System.out.println("Event not exist!");
        }
    }

    /**
     * Iterate through HashMap and delete all regular events
     * @param s - Scanner for user input
     */
    public void deleteRegular(Scanner s){
        System.out.print("\nEnter the name of the event to delete: ");
        String name = s.nextLine();
        Iterator<Map.Entry<LocalDate, ArrayList<Event>>> itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<LocalDate, ArrayList<Event>> eventList = (Map.Entry) itr.next();
            int action = searchEvent(eventList.getKey(), name);
            if(action != -1){   //if the event is found
                if(eventList.getValue().size()==1) //if there is only one event on that day
                    itr.remove();
                else
                    eventList.getValue().remove(action);
            }
        }
    }

    /**
     * Populate Output file
     */
    public void outputFile() throws IOException {
        try {
            FileWriter f = new FileWriter("src/output.txt");
            BufferedWriter b = new BufferedWriter(f);

            for (Map.Entry<LocalDate, ArrayList<Event>> eventList : map.entrySet()){
                for (int i = 0; i < eventList.getValue().size(); i++) {
                    b.write(eventList.getValue().get(i).toString() + "\n");
                }
            }
            b.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Print all events given the key in HashMap
     * @param key - HashMap key to print all events of that key
     */
    public void printAllEvent(LocalDate key){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("\nE, MM/dd/yyyy");
        System.out.println(formatter.format(key));
        if(map.containsKey(key)) {
            ArrayList<Event> curr = map.get(key);
            for (int i = 0; i < curr.size(); i++) {
                curr.get(i).print();
            }
        } else System.out.println("No Event!");
    }
}

