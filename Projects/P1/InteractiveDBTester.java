
import java.util.*;
import java.io.*;
import java.lang.*;
public class InteractiveDBTester {
	
	// make the Employee database a static data member so it does not have to 
	// be passed to each help method
        // It is protected so that subclasses representing particular testers can
        // access it

	protected static EmployeeDatabase EmployeeDB = new EmployeeDatabase();
	
        // Initialize DB from external data file
        protected static void populateDB(String [] args) {
    	
    	// Step 1: check whether exactly one command-line argument is given
	   /* Code to implement Step 1 goes here  */
    	if(args.length != 1){
    	    System.out.println("Please provide input file as command-line argument");
            return;
        }

    	// Step 2: check whether the input file exists and is readable
	   /* Code to implement Step 2 goes here  */
    	File src=new File(args[0]);
        try{
    	    Scanner reader=new Scanner(src);
            // Step 3: load the data from the input file and use it to construct a
            //         Employee database
            /* Code to implement Step 3 goes here  */
            while (reader.hasNextLine()){
                String str=reader.nextLine();
                if(str.length()==0)
                    break;
                String[] templst=str.split(",");

                EmployeeDB.addEmployee(templst[0].toLowerCase());
                List<String> tempDst=EmployeeDB.getDestinations(templst[0].toLowerCase());
                for(String i : templst){
                    if(i!=null && !i.equals(""))
                        tempDst.add(i.toLowerCase());
                }
            }
    	    reader.close();

        }
        catch (Exception e){
            System.out.println("Error: Cannot access input file");
            return;
        }



       }

     static boolean GUIactive;  //is GUI tester active?

// Methods that implement GUI buttons or testing actions
    private static String listConverter(String name,List<String> lst) {
    	Iterator<String> ptr=lst.iterator();
    	StringBuilder strbdr=new StringBuilder(name+":");
    	
    	while(ptr.hasNext()) {
    		String temp=ptr.next();
    		if(!(temp == null) && temp.equals(""))
    			strbdr.append(temp+",");
    	}
    	strbdr.delete(strbdr.length()-1, strbdr.length());
    	return strbdr.toString();
    }
    protected static String pushFind(String employee){
       /* Code to implement find command goes here:
          Find the supplied employee in the employee database
       */
    	if(EmployeeDB.containsEmployee(employee)) {
    		return listConverter(employee, EmployeeDB.getDestinations(employee));
    	}
    	return "employee not found";
    }

    protected static String pushDiscontinue(String destination){
       /* Code to implement discontinue command goes here:
          The supplied destination is removed from the wish lists
           of all employees in the employee database
       */
    	
    }

    protected static String pushSearch(String destination){
       /* Code to implement search command goes here:
           Search the employee database for all employees who have
            the supplied destination in their wish list
       */
    }

    protected static String pushRemove(String employee){
       /* Code to implement remone command goes here:
          Remove the supplied employee from the employee database
       */
    }

    protected static String pushInformation(){
       /* Code to implement information command goes here:
           Compute key information on the state of the employee database
       */
    }

    protected static String pushList(){
       /* Code to implement list command goes here:
          List the current contents of the employee database
       */
    }

    // The pushHelp method may be used as is:

    protected static String pushHelp(){
        String cmds = "";
	if (GUIactive) {
          cmds +="Available commands:\n" +
                "\tFind employee\n" +
                "\tDiscontinue destination\n" +
                "\tSearch destination\n" +
                "\tRemove employee\n" +
                "\tInformation on database\n" +
                "\tList contents of database\n" +
                "\tText interface activated\n" +
                "\tHelp on available commands\n" +
                "\tQuit database testing\n" ;
       }else {
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

    protected static String pushQuit(){
        System.exit(0);
        return "";
    }
}
