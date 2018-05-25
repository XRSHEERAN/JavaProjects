///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  ImageLoopEditor.java
// File:             LinkedLoopIterator.java
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
import java.util.*;
/**
 * This is a customized iterator
 *
 * Bugs: NA
 *
 * @author Xianrun Qu
 */
public class LinkedLoopIterator < E > implements Iterator < E > {
    private DblListnode < E > curr;
    private DblListnode < E > head;
    private boolean fst;
    /**
     * constructor
     *
     * @param current element of the loop
     * @return NA
     */
    public LinkedLoopIterator(DblListnode < E > pass) {
        this.curr = pass;
        this.head = pass;
        this.fst = true;
    }
    /**
     * check if there is next
     *
     * @param NA
     * @return true/false
     */
    @Override
    public boolean hasNext() {
        if (curr == null || (curr == head && !fst))//check if the whole loop is visited
            return false;

        return true;
    }
    /**
     * return an item
     *
     * @param NA
     * @return item
     */
    @Override
    public E next() {
        if (!this.hasNext())
            throw new NoSuchElementException();
        E ret = curr.getData();
        curr = curr.getNext();
        if (fst)
            fst = false;
        return ret;
    }
    /**
     * remove an item
     *
     * @param NA
     * @return NA
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }


}