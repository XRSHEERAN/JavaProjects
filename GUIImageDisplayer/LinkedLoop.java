///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  ImageLoopEditor.java
// File:             LinkedLoop.java
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
class LinkedLoop < E > implements LoopADT < E > , Iterable < E > {
    private DblListnode < E > curr = null;
    private int num = 0;
    public LinkedLoop() {

    }
    /**
     * add item
     *
     * @param item to add
     * @return NA
     */
    @Override
    public void add(E item) {
        this.num++;
        if (curr == null) {
            curr = new DblListnode < E > (item);
            curr.setPrev(curr);
            curr.setNext(curr); //self-linked
            return;
        }
        if (num == 2) {
            DblListnode < E > temp = curr;
            curr = new DblListnode < E > (item);
            temp.setNext(curr);
            temp.setPrev(curr);
            curr.setNext(temp);
            curr.setPrev(temp);
            return;
        }
        DblListnode < E > temp = curr.getPrev();
        //add the item before
        curr.setPrev(new DblListnode < E > (item)); //before current
        curr.getPrev().setNext(curr);
        curr = curr.getPrev();
        //circular
        temp.setNext(curr);
        curr.setPrev(temp);
    }
    /**
     * get the current item
     *
     * @param NA
     * @return the current item
     */
    @Override
    public E getCurrent() throws EmptyLoopException {
        if (curr == null)
            throw new EmptyLoopException();
        return curr.getData();
    }
    /**
     * remove current item
     *
     * @param NA
     * @return the removed item
     */
    @Override
    public E removeCurrent() throws EmptyLoopException {

        if (curr == null)
            throw new EmptyLoopException();
        E ret = curr.getData(); // return data
        this.num--;

        if (num == 0)
            curr = null;
        else if (num == 1) {
            curr = curr.getPrev();
            curr.setNext(curr);
            curr.setPrev(curr);
        } else {
            DblListnode < E > pre = curr.getPrev();
            curr = curr.getNext();
            pre.setNext(curr);
            curr.setPrev(pre);
        }
        return ret;
    }
    /**
     * move pointer forward
     *
     * @param NA
     * @return NA
     */
    @Override
    public void next() {
        if (curr == null)
            return;
        curr = curr.getNext();
    }
    /**
     * move pointer backward
     *
     * @param NA
     * @return NA
     */
    @Override
    public void previous() {
        if (curr == null)
            return;
        curr = curr.getPrev();
    }
    /**
     * check if the loop is empty
     *
     * @param NA
     * @return true/false
     */
    @Override
    public boolean isEmpty() {
        if (curr == null)
            return true;
        return false;
    }
    /**
     * get the size of the loop
     *
     * @param NA
     * @return size of loop
     */
    @Override
    public int size() {
        return num;
    }
    /**
     * return a iterator
     *
     * @param NA
     * @return iterator
     */
    @Override
    public Iterator < E > iterator() {
        //System.out.println(curr.toString());
        LinkedLoopIterator < E > itrpar = new LinkedLoopIterator < E > (curr);
        return itrpar;
    }
}
