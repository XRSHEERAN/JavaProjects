import javax.swing.text.html.HTMLDocument;
import java.util.*;
import java.lang.*;
public class EmployeeDatabase{
    //check null parameters!!!!!!!!!!!!
    private List<Employee> database;
    private List<String> names;
    //use only one employee exists
    private Employee empFinder(String e){
        Iterator<Employee> ptr=this.database.iterator();

        while(ptr.hasNext()){
            Employee temp=ptr.next();
            if(temp.getUsername().equals(e))
                return temp;
        }

        throw new IllegalArgumentException();
    }

    public EmployeeDatabase(){
        database=new ArrayList();
        names=new ArrayList();
    }
    public void addEmployee(String e){
        if(e==null)
            throw new IllegalArgumentException();
        e=e.toLowerCase();
        if(names.contains(e))
            return;
        database.add(new Employee(e));
        names.add(e);
    }
    public void addDestination(String e, String d){
        if(e==null || d==null)
            throw new IllegalArgumentException();
        d=d.toLowerCase();
        List<String> temp=empFinder(e).getWishlist();//throws illegal argument exception
        if(!temp.contains(d))
            temp.add(d);
    }
    public boolean containsEmployee(String e){
        if(e==null)
            throw new IllegalArgumentException();
        e=e.toLowerCase();
        boolean ret=false;
        if(names.contains(e))
            return !ret;
        return ret;
    }
    public boolean containsDestination(String d){
        if(d==null)
            throw new IllegalArgumentException();
        d=d.toLowerCase();
        Iterator<Employee> destPtr=database.iterator();
        while(destPtr.hasNext()){
            Employee temp=destPtr.next();
            if(temp.getWishlist().contains(d)) return true;
        }
        return false;
    }
    public boolean hasDestination(String e, String d){
        if(e==null || d==null)
            throw new IllegalArgumentException();
        e=e.toLowerCase();
        d=d.toLowerCase();
        if(!names.contains(e) || !empFinder(e).getWishlist().contains(d))
            return false;
        return true;
    }
    public List<String> getEmployees(String d){
        if(d==null)
            throw new IllegalArgumentException();
        d=d.toLowerCase();
        if(!this.containsDestination(d))
            return null;
        List<String> ret= new ArrayList<String>();
        Iterator<String> ptr=names.iterator();
        while(ptr.hasNext()){
            String temp= ptr.next();
            if(this.hasDestination(temp,d))
                ret.add(temp);
        }
        return ret;
    }
    public List<String> getDestinations(String e){
        if(e==null)
            throw new IllegalArgumentException();
        e=e.toLowerCase();
        if(!this.containsEmployee(e)) return null;
        return empFinder(e).getWishlist();
    }
    public Iterator<Employee> iterator(){
        return database.iterator();
    }
    public boolean removeEmployee(String e){
        if(e==null)
            throw new IllegalArgumentException();
        e=e.toLowerCase();
        if(!this.containsEmployee(e))
            return false;
        names.remove(e);
        database.remove(this.getEmployees(e));
        return true;
    }
    public boolean removeDestination(String d){
        if(d==null)
            throw new IllegalArgumentException();
        d=d.toLowerCase();
        if(!this.containsDestination(d))
            return false;
        Iterator<Employee> ptr= database.iterator();
        while(ptr.hasNext()){
            List<String> temp=ptr.next().getWishlist();
            temp.remove(d);
        }
        return true;
    }
    public int size(){
        return names.size();
    }
}
