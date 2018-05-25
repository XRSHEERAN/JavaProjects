///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:  	         BSTDictionary.java
// File:             BSTDictionary.java
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
import java.util.Iterator;

public class BSTDictionary<K extends Comparable<K>> implements DictionaryADT<K> {
    private BSTnode<K> root;  // the root node
    private int numItems;     // the number of items in the dictionary
    private boolean del;
    private BSTnode<K> look;
    private BSTnode<K> inserthelper(BSTnode<K> n, K key) throws DuplicateException {
        if (n == null) {
            return new BSTnode<K>(key, null, null);
        }

        if (n.getKey().equals(key)) {
            throw new DuplicateException();
        }

        if (key.compareTo(n.getKey()) < 0) {
            // add key to the left subtree
            n.setLeft( inserthelper(n.getLeft(), key) );
            return n;
        }

        else {
            // add key to the right subtree
            n.setRight( inserthelper(n.getRight(), key) );
            return n;
        }
    }
    private BSTnode<K> deletehelper(BSTnode<K> n, K key) {
        if (n == null) {
            return null;
        }

        if (key.equals(n.getKey())) {
            this.numItems--;
            this.del=true;
            if(n.getLeft()==null && n.getRight()==null)
                return null;
            if(n.getLeft()==null)
                return n.getRight();
            if(n.getRight()==null)
                return n.getLeft();
            BSTnode<K> curr=n.getRight();
            while(curr.getLeft()!=null)
                curr=curr.getLeft();
            n.setKey(curr.getKey());
            n.setRight(deletehelper(n.getRight(),curr.getKey()));

        }

        else if (key.compareTo(n.getKey()) < 0) {
            n.setLeft( deletehelper(n.getLeft(), key) );

        }

        else {
            n.setRight( deletehelper(n.getRight(), key) );

        }
        return n;
    }
    private boolean lookuphelper(BSTnode<K> n, K key) {
        if (n == null) {
            return false;
        }

        if (n.getKey().equals(key)) {
            look=n;
            return true;
        }

        if (key.compareTo(n.getKey()) < 0) {
            return lookuphelper(n.getLeft(), key);
        }

        else {
            return lookuphelper(n.getRight(), key);
        }
    }
    private int lengthhelper(BSTnode<K> n,int dps){
        if(n==null)
            return 0;
        return dps+lengthhelper(n.getLeft(),dps+1)+lengthhelper(n.getRight(),dps+1);
    }
    // TO DO:
    //
    // Add a no-argument constructor
    //
    // Add your code to implement the Dictionary ADT operations using a binary
    // search tree.
    // You may use any code given in the on-line reading on BSTs.
    public BSTDictionary(){
        numItems=0;
    }
    public void insert(K key) throws DuplicateException {
        // add your code

        root=inserthelper(root,key);
        this.numItems++;
    }

    public boolean delete(K key) {
        this.del=false;
        root=deletehelper(root,key);
        return this.del;  // replace this stub with your code
    }

    public K lookup(K key) {
        if(lookuphelper(root,key))
            return look.getKey();
        return null;  // replace this stub with your code
    }

    public boolean isEmpty() {
        if(this.root==null)
            return true;
        return false;  // replace this stub with your code
    }

    public int size() {
        return this.numItems;  // replace this stub with your code
    }
    
    public int totalPathLength() {
        return 1+lengthhelper(root.getLeft(),2)+lengthhelper(root.getRight(),2);  // replace this stub with your code
    }
    
    public Iterator<K> iterator() {
        return new BSTDictionaryIterator<K>(root);  // replace this stub with your code
    }
}
