///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  NA
// File:             EmployeeDatabase.java
// Semester:         (CS367) Spring 2018
//
// Author:           Xianrun (Sheeran) Qu (xqu25@wisc.edu
// CS Login:         xianrun
// Lecturer's Name:  Charles Fischer
// Lab Section:      NA
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Author:     Yiqiao (Bob) Xin
// Email:            xin23@wisc.edu
// CS Login:         yiqiao
// Lecturer's Name:  Charles Fischer
// Lab Section:      NA
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
import java.util.*;
public class EmployeeDatabase {
 private List < Employee > database;
 private List < String > names;
 //use only one employee exists
 private Employee empFinder(String e) {
  Iterator < Employee > ptr = this.database.iterator();
  // create an iterator used to traverse all the data in database 

  while (ptr.hasNext()) {
   Employee temp = ptr.next(); //traverse through the entire database 
   if (temp.getUsername().equals(e)) //search for matching username of e in the database 
    return temp; //when found, return temp. 
  }

  throw new IllegalArgumentException();
 }

 /**
  * construct employee database
  * 
  * @param args UNUSED
  */
 public EmployeeDatabase() {
  database = new ArrayList < Employee > (); //create new database Arraylist using Employee object. 
  names = new ArrayList < String > (); //create new names Arraylist using String object. 
 }

 /**
  * add a new employee e to the end of the list, if contained just return.
  * 
  * @param e
  */
 public void addEmployee(String e) {
  if (e == null)
   throw new IllegalArgumentException();
  e = e.toLowerCase().trim(); //all that's contained in the database needs to be lower-cased. 
  if (names.contains(e)) //check if names are already contained in the list. 
   return; //if name contained, just return
  database.add(new Employee(e)); //if not contained, add new employee parameter e. 
  names.add(e); //pass string type e into the Arraylist.
 }

 /**
  * add new destination d to the wish list for employee e
  * if e doesn't exit in the list, throw java.lang.IllegalArgumentException
  * if d is already on e's wish list just return
  *  
  * @param e
  * @param d
  */
 public void addDestination(String e, String d) {
  if (e == null || d == null)
   throw new IllegalArgumentException(); //exception thrown if not found.
  d = d.toLowerCase().trim(); //all passed parameter needs to be lower-cased. 
  e = e.toLowerCase().trim(); //all passed parameter needs to be lower-cased. 

  List < String > temp = empFinder(e).getWishlist(); //throws illegal argument exception
  if (!temp.contains(d))
   temp.add(d); //add new destination if d not found in wishlist. 
 }

 /**
  * search through the database and return true if employee e is in the database
  * 
  * @param e
  * @return
  */
 public boolean containsEmployee(String e) {
  if (e == null)
   throw new IllegalArgumentException();
  e = e.toLowerCase().trim();
  boolean ret = false;
  if (names.contains(e)) //if names in the list, return true. 
   return !ret;
  return ret;
 }

 /**
  * Return true if and only if destination d appears in at least one employee's wish list in the database.
  * 
  * @param d
  * @return
  */
 public boolean containsDestination(String d) {
  if (d == null)
   throw new IllegalArgumentException();
  d = d.toLowerCase().trim();
  Iterator < Employee > destPtr = this.database.iterator(); //create an iterator for searching through the 
  //entire database
  while (destPtr.hasNext()) {
   Employee temp = destPtr.next();
   if (temp.getWishlist().contains(d)) return true; //return true if destination found 
   //in current temp
  }
  return false;
 }

 /**
  * Returns true if and only if destination d is in the wish list for employee e. 
  * If employee e is not in the database, return false.
  * 
  * @param e
  * @param d
  * @return
  */
 public boolean hasDestination(String e, String d) {
  if (e == null || d == null) //check null 
   throw new IllegalArgumentException();
  e = e.toLowerCase().trim(); //parameters need to be in lowercase.  
  d = d.toLowerCase().trim();
  if (!names.contains(e) || !empFinder(e).getWishlist().contains(d)) //check if e is in the database
  //check if e contains d, return false if either one is not met. 
   return false;
  return true;
 }

 /**
  * Return the list of employees who have destination d in their wish list. 
  * If destination d is not in the database, return a null list.
  * 
  * @param d
  * @return
  */
 public List < String > getEmployees(String d) {
  if (d == null)
   throw new IllegalArgumentException();
  d = d.toLowerCase().trim();
  if (!this.containsDestination(d)) //first, check if d is in the database, return null if not. 
   return null;
  List < String > ret = new ArrayList < String > (); //create a string list for storing list of employees.
  Iterator < String > ptr = names.iterator(); //create an iterator for traversing through the names 
  while (ptr.hasNext()) {
   String temp = ptr.next();
   if (this.hasDestination(temp, d)) //check if destination d is in employee e's wishlist. 
    ret.add(temp); //add e to the return list if true. 
  }
  return ret;
 }

 /**
  * Return the wish list for the employee e. If an employee e is not in the database, return null.
  * 
  * @param e
  * @return
  */
 public List < String > getDestinations(String e) {
  if (e == null) //check null
   throw new IllegalArgumentException();
  e = e.toLowerCase().trim();
  if (!this.containsEmployee(e)) return null; //check if e in the list 
  return empFinder(e).getWishlist(); //use iterator, return wishlist.
 }

 /**
  * Return an Iterator over the Employee objects in the database. 
  * The employees should be returned in the order they were added to the database 
  * (resulting from the order in which they are in the text file).
  * 
  * @return
  */
 public Iterator < Employee > iterator() {
  return database.iterator();
 }

 /**
  * Remove employee e from the database. If employee e is not in the database, return false; 
  * otherwise (i.e., the removal is successful) return true.
  * 
  * @param e
  * @return
  */
 public boolean removeEmployee(String e) {
  if (e == null)
   throw new IllegalArgumentException();
  e = e.toLowerCase().trim();
  if (!this.containsEmployee(e))
   return false;
  names.remove(e);
  database.remove(this.empFinder(e));
  return true;
 }

 /**
  * Remove destination d from the database, i.e., remove destination d from every wish list 
  * in which it appears. If destination d is not in the database, 
  * return false; otherwise (i.e., the removal is successful) return true.
  * 
  * @param d
  * @return
  */
 public boolean removeDestination(String d) {
  if (d == null)
   throw new IllegalArgumentException();
  d = d.toLowerCase().trim();
  if (!this.containsDestination(d)) //if destination is not contained in the database, return false. 
   return false;
  Iterator < Employee > ptr = database.iterator();
  while (ptr.hasNext()) {
   List < String > temp = ptr.next().getWishlist(); //go through the databse, and delete all d. 
   temp.remove(d);
  }
  return true;
 }

 /**
  * Return the number of employees in this database.
  * 
  * @return
  */
 public int size() {
  return names.size();
 }

}