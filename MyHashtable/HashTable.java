///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:  	         HashTable.java
// File:             HashTable.java
// Semester:         (CS367) Spring 2018
//
// Author:           Xianrun (Sheeran) Qu (xqu25@wisc.edu
// CS Login:         xianrun
// Lecturer's Name:  Charles Fischer
// Lab Section:      NA
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Author:           Yiqiao (Bob) Xin
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

import java.io.*;
import java.util.LinkedList;

/**
 * This class implements a hashtable that using chaining for collision handling.
 * Any non-<tt>null</tt> item may be added to a hashtable.  Chains are 
 * implemented using <tt>LinkedList</tt>s.  When a hashtable is created, its 
 * initial size, maximum load factor, and (optionally) maximum chain length are 
 * specified.  The hashtable can hold arbitrarily many items and resizes itself 
 * whenever it reaches its maximum load factor or whenever it reaches its 
 * maximum chain length (if a maximum chain length has been specified).
 * 
 * Note that the hashtable allows duplicate entries.
 */
public class HashTable<T> {
    private LinkedList<T>[] hashtbl;
    private double mxsz;
    private int maxChain;
    private int tblsz;
    private int items;
    private boolean mode;
    /**
     * This function is used to locate the item pos in hashtable
     * @param item
     */
    private int locationHelper(T item){
        int loc=item.hashCode();
        loc%=tblsz;
        loc=(loc>=0)?loc : (loc+tblsz);

        return loc;
    }

    private int maxChain(){
        int max=-1;
        for(LinkedList<T> lst : hashtbl){
            if(lst==null)
                continue;
            max=(max>lst.size())?max:lst.size();
        }
        return (max>=0)?max : 0;
    }
    private int freeChain(){
        int num=0;
        for(LinkedList<T> lst : hashtbl) {
            if (lst == null)
                num++;
        }
        return num;
    }
    /**
     * Resize the array to *2+1
     */
    private void resizeHelper(T item){
        LinkedList<T> addin=new LinkedList<T>();

        for(LinkedList<T> lst : hashtbl){
            if(lst==null)
                continue;
            for(T itm : lst){
                addin.add(itm);
            }
        }
        addin.add(item);
        int times=1;
        while(resizer(addin)<0){
            times++;
            if(times==5)
                this.mode=false;
        }
    }

    /**
     * Enlarge the hashtable array
     * @param curr the items
     */
    private int resizer(LinkedList<T> curr){
        tblsz*=2;
        tblsz+=1;
        LinkedList<T>[] temp=(LinkedList<T>[]) new LinkedList[tblsz];

        for(T itm : curr){
            int loc=this.locationHelper(itm);

            if(temp[loc]==null){
                LinkedList<T> addin=new LinkedList<T>();
                addin.add(itm);
                temp[loc]=addin;
            }
            else{
                LinkedList<T> addin=temp[loc];
                if(mode && addin.size()>maxChain){
                    return -1; //indicate resize
                }
                addin.add(itm);
            }
        }
        hashtbl=temp;
        return 1;
    }
    /**
     * Constructs an empty hashtable with the given initial size, maximum load
     * factor, and no maximum chain length.  The load factor should be a real 
     * number greater than 0.0 (not a percentage).  For example, to create a 
     * hash table with an initial size of 10 and a load factor of 0.85, one 
     * would use:
     * 
     * <dir><tt>HashTable ht = new HashTable(10, 0.85);</tt></dir>
     *
     * @param initSize the initial size of the hashtable.
     * @param loadFactor the load factor expressed as a real number.
     * @throws IllegalArgumentException if <tt>initSize</tt> is less than or 
     *         equal to 0 or if <tt>loadFactor</tt> is less than or equal to 0.0
     **/
    public HashTable(int initSize, double loadFactor) {
        if(initSize<=0 || loadFactor <= 0.0)
            throw new IllegalArgumentException();
        this.hashtbl= (LinkedList<T>[]) new LinkedList[initSize];
        this.mxsz=loadFactor;
        this.tblsz=initSize;
        this.items=0;
        this.mode=false;
    }
    
    
    /**
     * Constructs an empty hashtable with the given initial size, maximum load
     * factor, and maximum chain length.  The load factor should be a real 
     * number greater than 0.0 (and not a percentage).  For example, to create 
     * a hash table with an initial size of 10, a load factor of 0.85, and a 
     * maximum chain length of 20, one would use:
     * 
     * <dir><tt>HashTable ht = new HashTable(10, 0.85, 20);</tt></dir>
     *
     * @param initSize the initial size of the hashtable.
     * @param loadFactor the load factor expressed as a real number.
     * @param maxChainLength the maximum chain length.
     * @throws IllegalArgumentException if <tt>initSize</tt> is less than or 
     *         equal to 0 or if <tt>loadFactor</tt> is less than or equal to 0.0 
     *         or if <tt>maxChainLength</tt> is less than or equal to 0.
     **/
    public HashTable(int initSize, double loadFactor, int maxChainLength) {
        if(initSize<=0 || loadFactor <= 0.0 || maxChainLength<=0)
            throw new IllegalArgumentException();
        this.hashtbl= (LinkedList<T>[]) new LinkedList[initSize];
        this.mxsz=loadFactor;
        this.maxChain=maxChainLength;
        this.mode=true;
        this.tblsz=initSize;
        this.items=0;
    }
    
    
    /**
     * Determines if the given item is in the hashtable and returns it if 
     * present.  If more than one copy of the item is in the hashtable, the 
     * first copy encountered is returned.
     *
     * @param item the item to search for in the hashtable.
     * @return the item if it is found and <tt>null</tt> if not found.
     **/
    public T lookup(T item) {
        if(item==null)
            return null;
        int loc=this.locationHelper(item);
        if(this.hashtbl[loc]==null)
            return null;
        for(T itm : this.hashtbl[loc]){
            if (itm.equals(item))
                return itm;
        }
        return null;
    }
    
    
    /**
     * Inserts the given item into the hashtable.  The item cannot be 
     * <tt>null</tt>.  If there is a collision, the item is added to the end of
     * the chain.
     * <p>
     * If the load factor of the hashtable after the insert would exceed 
     * (not equal) the maximum load factor (given in the constructor), then the 
     * hashtable is resized.  
     * 
     * If the maximum chain length of the hashtable after insert would exceed
     * (not equal) the maximum chain length (given in the constructor), then the
     * hashtable is resized.
     * 
     * When resizing, to make sure the size of the table is reasonable, the new 
     * size is always 2 x <i>old size</i> + 1.  For example, size 101 would 
     * become 203.  (This guarantees that it will be an odd size.)
     * </p>
     * <p>Note that duplicates <b>are</b> allowed.</p>
     *
     * @param item the item to add to the hashtable.
     * @throws NullPointerException if <tt>item</tt> is <tt>null</tt>.
     **/
    public void insert(T item) {
        if(item==null)
            throw new NullPointerException();
        double currLoad=(++items)/((double)tblsz);
        if(currLoad>mxsz) {
            resizeHelper(item);
            return;
        }
        int loc=this.locationHelper(item);

        if(this.hashtbl[loc]==null){
            LinkedList<T> temp=new LinkedList<T>();
            temp.add(item);
            hashtbl[loc]=temp;
        }
        else{
            LinkedList<T> temp=this.hashtbl[loc];
            if(mode && temp.size()+1>maxChain){
                resizeHelper(item);
                return;
            }
            temp.add(item);
        }
    }
    
    
    /**
     * Removes and returns the given item from the hashtable.  If the item is 
     * not in the hashtable, <tt>null</tt> is returned.  If more than one copy 
     * of the item is in the hashtable, only the first copy encountered is 
     * removed and returned.
     *
     * @param item the item to delete in the hashtable.
     * @return the removed item if it was found and <tt>null</tt> if not found.
     **/
    public T delete(T item) {
        T ret=this.lookup(item);
        if(ret==null)
            return null;
        int loc=locationHelper(ret);
        if(hashtbl[loc].size()==1)
            hashtbl[loc]=null;
        else{
            for(int i=0;i<hashtbl[loc].size();i++){
                if(hashtbl[loc].get(i).equals(item)){
                    hashtbl[loc].remove(i);
                    break;
                }
            }
        }
        return ret;
    }
    
    
    /**
     * Prints all the items in the hashtable to the <tt>PrintStream</tt> 
     * supplied.  The items are printed in the order determined by the index of
     * the hashtable where they are stored (starting at 0 and going to 
     * (table size - 1)).  The values at each index are printed according 
     * to the order in the <tt>LinkedList</tt> starting from the beginning. 
     *
     * @param out the place to print all the output.
     **/
    public void dump(PrintStream out) {
        out.println("Hashtable contents:");
        for(int i=0;i<tblsz;i++){
            LinkedList<T> lst=hashtbl[i];
            if(lst==null)
                continue;
            out.print(i+": [");
            for(int j=0; j<lst.size();j++){
                if(j==lst.size()-1){
                    out.println(lst.get(j)+"]");
                    break;
                }
                out.print(lst.get(j)+", ");
            }
        }
    }
    
  
    /**
     * Prints statistics about the hashtable to the <tt>PrintStream</tt> 
     * supplied.  The statistics displayed are: 
     * <ul>
     * <li>the current table size
     * <li>the number of items currently in the table 
     * <li>the current load factor
     * <li>the length of the largest chain
     * <li>the number of chains of length 0
     * <li>the average length of the chains of length > 0
     * </ul>
     *
     * @param out the place to print all the output.
     **/
    public void displayStats(PrintStream out) {
        int currMaxc=maxChain();
        int freeC=freeChain();
        out.println("Hashtable statistics:\n" +"  current table size: "+hashtbl.length+"\n  # items in table: "+items+"\n  current load factor: "+(((double)items)/((double)tblsz)) + "\n  longest chain length: "+currMaxc+"\n  # 0-length chains: "+freeC+"\n  avg (non-0) chain length: "+(items)/((double)(tblsz-freeC)));
    }
}

