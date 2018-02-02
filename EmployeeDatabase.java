import java.util.*;
import java.lang.*;
public class EmployeeDatabase{
  //check null parameters!!!!!!!!!!!!
  List<Employee> database;
  List<String> names;
  public EmployeeDatabase(){
    database=new ArrayList();
    names=new ArrayList();
  }
  public void void addEmployee(String e){
    if(e==null)
      throw new IllegalArgumentException();
    if(names.contains(e))
      return;
    database.add(new Employee(e));
  }
  public void addDestination(String e, String d){
  }
  public boolean containsEmployee(String e){
  }
  public boolean containsDestination(String d){
  }
  public boolean hasDestination(String e, String d){
  }
  public List<String> getEmployees(String d){
  }
  public List<String> getDestinations(String e){
  }
  public Iterator<Employee> iterator(){
  }
  public boolean removeEmployee(String e){
  }
  public boolean removeDestination(String d){
  }
  public int size(){
  }
}
