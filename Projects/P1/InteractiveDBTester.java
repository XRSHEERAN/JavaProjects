///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  NA
// File:             InteractiveDBTester.java
// Semester:         (CS367) Spring 2018
//
// Author:           Xianrun (Sheeran) Qu (xqu25@wisc.edu
// CS Login:         xianrun
// Lecturer's Name:  Charles Fischer
// Lab Section:      NA
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Yiqiao Xin
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//
//////////////////// STUDENTS WHO GET HELP FROM OTHER THAN THEIR PARTNER //////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
//////////////////////////// 80 columns wide //////////////////////////////////
import java.util.*;//General usage
import java.io.*;//for file I/O
/**
 * Test the Interactive Database class.
 *
 * Bugs: none known
 *
 * @author       Xianrun Qu & Yiqiao Xin
 */
public class InteractiveDBTester {

    // make the Employee database a static data member so it does not have to 
    // be passed to each help method
    // It is protected so that subclasses representing particular testers can
    // access it
	
    protected static EmployeeDatabase EmployeeDB = new EmployeeDatabase();//Store the database used to test

    /**
     * Initialize DB from external data file
     *
     * @param args the command line arguments
     * @return none
     */
    protected static void populateDB(String[] args) {

        // Step 1: check whether exactly one command-line argument is given
        /* Code to implement Step 1 goes here  */
        if (args.length != 1) {
            System.out.println("Please provide input file as command-line argument");
            return;
        }

        // Step 2: check whether the input file exists and is readable
        /* Code to implement Step 2 goes here  */
        try {
        	File src = new File(args[0]);//source file
            Scanner reader = new Scanner(src);
            // Step 3: load the data from the input file and use it to construct a
            //         Employee database
            /* Code to implement Step 3 goes here  */
            while (reader.hasNextLine()) {
                String str = reader.nextLine(); //temporary string storage
                if (str==null || str.length() == 0)
                    break;
                String[] templst = str.split(",");// split the string

                EmployeeDB.addEmployee(templst[0]); //the first appearance should be the employee as the format states
                List < String > tempDst = EmployeeDB.getDestinations(templst[0]);//get the list
                //there is at least one destination as required
                for (int i=1 ; i < templst.length ; i++) {
                    if (templst[i] != null && !templst[i].equals(""))
                        tempDst.add(templst[i]);//add the strings in file one by one
                }
            }
            reader.close();

        } catch (Exception e) {
            System.out.println("Error: Cannot access input file");
            return;
        }
    }

    static boolean GUIactive; //is GUI tester active?

    
    /**
     * Methods that implement GUI buttons or testing actions
     *
     * @param name: the name appear in the front
     * @param lst: the list of strings need to be concatenated 
     * @return employee,destination1,destination2,...,destinationN String
     */
    private static String listConverter(String name, List < String > lst) {
        Iterator < String > ptr = lst.iterator();//go through the list
        StringBuilder strbdr;
        if(name!=null)
        	strbdr=new StringBuilder(name + ":"); // build the list
        else
        	strbdr=new StringBuilder(); // build the list without name
        
        //loop to concatenate all strings
        while (ptr.hasNext()) {
            String temp = ptr.next();
            if (!(temp == null) && !temp.equals(""))
                strbdr.append(temp + ",");
        }
        //check if the database is empty
        if(strbdr.length()<=name.length())
        	return strbdr.toString();
        strbdr.delete(strbdr.length() - 1, strbdr.length());//delete the last comma
        return strbdr.toString();
    }
    /**
     * Helper function to round the number to tenth
     *
     * @param inp: the number to round
     * @return rounded number
     */
    private static double toTenth(double inp) {
        String con = inp + "";//convert to string
        int ptr = 0;//pointer for the decimal point
        while (con.charAt(ptr) != '.') {
            ptr++;
        }
        double ret = inp;// the number to return
        if (ptr < con.length() - 2) {//if the double is more than tenth
            if (con.charAt(ptr + 2) >= '5')//round by the hundredth
                ret = Double.parseDouble(con.substring(0, ptr + 2)) + 0.1;
            else
                ret = Double.parseDouble(con.substring(0, ptr + 2));
        }
        return ret;
    }
    /**
     * If employee is not in the database, return "employee not found". Otherwise, find employee and return the employee (on one line) in the format:
	 * employee:destination1,destination2,destination3
     *
     * @param find employee name
     * @return string
     */
    protected static String pushFind(String employee) {
        /* Code to implement find command goes here:
           Find the supplied employee in the employee database
        */
        if (EmployeeDB.containsEmployee(employee)) {
            return listConverter(employee, EmployeeDB.getDestinations(employee));
        }
        return "employee not found";
    }
    /**
     *  	If destination is not in the database, return "destination not found". Otherwise, discontinue destination (i.e., remove the destination from all the wish lists in which it appears) and return "destination discontinued".
     *
     * @param inp: the number to round
     * @return string
     */
    protected static String pushDiscontinue(String destination) {
        /* Code to implement discontinue command goes here:
           The supplied destination is removed from the wish lists
            of all employees in the employee database
        */
        if (!EmployeeDB.containsDestination(destination))
            return "destination not found";
        EmployeeDB.removeDestination(destination);
        return "destination discontinued";
    }
    /*
     * If destination is not in the database, return "destination not found." Otherwise, search for destination and return the destination along with the employees who have that destination in their wish list (on one line) in the format:
	 * destination:employee1,employee2,employee3
     * @param destination: the dest name
     * @return string
     */
    protected static String pushSearch(String destination) {
        /* Code to implement search command goes here:
            Search the employee database for all employees who have
             the supplied destination in their wish list
        */
        if (!EmployeeDB.containsDestination(destination))
            return "destination not found"; //Or "destination not found."
        return listConverter(destination, EmployeeDB.getEmployees(destination));
    }
    /*
     * If employee is not in the database, return "employee not found". Otherwise, remove employee and return "employee removed".
     * @param employee: employee name
     * @return string
     */
    protected static String pushRemove(String employee) {
        /* Code to implement remove command goes here:
           Remove the supplied employee from the employee database
        */
        if (!EmployeeDB.containsEmployee(employee))
            return "employee not found";
        EmployeeDB.removeEmployee(employee);
        return "employee removed";
    }
    /*
     * Return information about this database by doing the following:

    	Return a line: "Employees: integer, Destinations: integer"
    	This is the number of employees followed by the total number of unique destinations.

    	Return a line: "# of destinations/employee: most integer, least integer, average decimal fraction"
    	where most is the largest number of destinations that any employee has in their wish list, least is the fewest, and average is the arithmetic mean number of destinations per employee rounded to the nearest tenth (e.g., 1.2 or 0.7).

    	Return a line: "# of employees/destination: most integer, least integer, average decimal fraction"
    	where most is the largest number of employee wish lists in which any destination appears, least is the fewest, and average is the arithmetic mean number of employees per destination rounded to the nearest tenth (e.g., 1.2 or 0.7).

    	Return a line: "Most popular destination: destination(s) [integer]"
    	This is the destination that shows up in the greatest number of wish lists followed by the number of wish lists containing that destination in square brackets. If there is a tie for most popular destination, display all those tying in the order they appear in the database separated by commas.
     * @param employee: employee name
     * @return string
     */
    protected static String pushInformation() {
        /* Code to implement information command goes here:
            Compute key information on the state of the employee database
        */
        
        //2. info about destination list
        int maxL = 0;//max length
        int minL = Integer.MAX_VALUE;//min
        double sumL = 0;//avg
        //3. the info about popular destinations
        int maxD = 0;//max
        int minD = Integer.MAX_VALUE;//min
        double countD = 0.0;//number of destination
        
        List < String > tbl = new ArrayList < String > ();//store unique destinations
        Iterator < Employee > ptr = EmployeeDB.iterator();//temployee pointer
        while (ptr.hasNext()) {
            Employee curr = ptr.next();//current employee
            List < String > tempDest = curr.getWishlist();//current emp's destination list
            Iterator < String > destptr = tempDest.iterator();
            int ds = tempDest.size();//destination size
            maxL = (maxL < ds) ? ds : maxL;
            minL = (minL > ds) ? ds : minL;
            sumL += ds;//for average calculation
            while (destptr.hasNext()) {
                String dName = destptr.next();
                if (dName != null && !dName.equals("") && !tbl.contains(dName)) {
                    countD++;//for average
                    tbl.add(dName);//unique name
                }
            }
        }
        double numL = (double) EmployeeDB.size();
        double meanL = toTenth(sumL / numL); //result
        double meanD = toTenth(sumL / countD); //results of Dests
        String ret1="Employees: "+EmployeeDB.size()+", Destinations: "+tbl.size()+'\n';
        String ret2="# of destinations/employee: most "+maxL+", least "+minL+", average "+meanL+'\n';
        //third,go through destinations
        Iterator < String > ptrD = tbl.iterator();
        List<String> DLst=new ArrayList<String>();//store the max destination
        
        while (ptrD.hasNext()) {
            ptr = EmployeeDB.iterator();//each employee
            int count = 0;
            String dtemp = ptrD.next();
            while (ptr.hasNext()) {//go through employees
                Employee temp = ptr.next();
                if (EmployeeDB.hasDestination(temp.getUsername(), dtemp))
                    count++;
            }
            if (count > maxD) {
                maxD = count;
                DLst=new ArrayList<String>();
                DLst.add(dtemp);
            }
            if (count < minD)
                minD = count;
            if(count==maxD) {
            	DLst.add(dtemp);
            }
        }
        String ret3="# of employees/destination: most "+maxD+", least "+minD+", average "+meanD+'\n';
        String ret4="Most popular destination: "+listConverter(null,DLst)+" ["+maxD+"]";
        return ret1+ret2+ret3+ret4;

    }
    /*
     * 	Return a list of the contents of the entire employee database, one employee per line in the format:
		employee:destination1,destination2,destination3
     * @param NA
     * @return string
     */
    protected static String pushList() {
        /* Code to implement list command goes here:
           List the current contents of the employee database
        */
        Iterator < Employee > ptr = EmployeeDB.iterator();
        String ret = "";
        while (ptr.hasNext()) {
            Employee temp = ptr.next();
            ret += listConverter(temp.getUsername(), temp.getWishlist()) + '\n';
        }
        return ret;
    }

    // The pushHelp method may be used as is:

    protected static String pushHelp() {
        String cmds = "";
        if (GUIactive) {
            cmds += "Available commands:\n" +
                "\tFind employee\n" +
                "\tDiscontinue destination\n" +
                "\tSearch destination\n" +
                "\tRemove employee\n" +
                "\tInformation on database\n" +
                "\tList contents of database\n" +
                "\tText interface activated\n" +
                "\tHelp on available commands\n" +
                "\tQuit database testing\n";
        } else {
            cmds +=
                ("discontinue/d <destination> - discontinue the given <destination>\n") +
                ("find/f <Employee> - find the given <Employee>\n") +
                ("gui/g Switch to GUI testing interface\n") +
                ("help/h - display this help menu\n") +
                ("information/i - display information about this Employee database\n") +
                ("list/l - list contents of Employee database\n") +
                ("search/s <destination> - search for the given <destination>\n") +
                ("quit/q - quit\n") +
                ("remove/r <Employee> - remove the given <Employee>\n");

        }
        return cmds;
    }

    // The pushQuit method may be used as is:

    protected static String pushQuit() {
        System.exit(0);
        return "";
    }
}
