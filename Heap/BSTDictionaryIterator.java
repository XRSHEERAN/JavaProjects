import java.util.*;

/**
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary.  The iterator iterates over the tree in 
 * order of the key values (from smallest to largest).
 */
///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:  	         BSTDictionaryIterator.java
// File:             BSTDictionaryIterator.java
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
public class BSTDictionaryIterator<K> implements Iterator<K> {

    // TO DO:
    //
    // Add your code to implement the BSTDictionaryIterator.  To receive full
    // credit:
    // - You must not use recursion in any of methods or constructor.
    // - The constructor must have a worst-case complexity of O(height of BST).
    // 
    // Hint: use a Stack and push/pop nodes as you iterate through the BST.
    // The constructor should push all the nodes needed so the *first* call 
    // to next() returns the value in the node with the smallest key.
    // (You can use the Java API Stack or implement your own Stack - if you
    // implement your own, make sure to hand it in.)
    private Stack<BSTnode<K>> stk;
    public BSTDictionaryIterator(BSTnode<K> root){
        stk=new Stack<BSTnode<K>>();
        while(root!=null){
            stk.push(root);
            root=root.getLeft();
        }
    }
    public boolean hasNext() {
        return !stk.isEmpty();  // replace this stub with your code
    }

    public K next() {
          // replace this stub with your code
        BSTnode<K> curr=stk.pop();
        K ret=curr.getKey();
        if(curr.getRight()!=null){
            curr=curr.getRight();
            while(curr!=null){
                stk.push(curr);
                curr=curr.getLeft();
            }
        }
        return ret;
    }

    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }    
}
