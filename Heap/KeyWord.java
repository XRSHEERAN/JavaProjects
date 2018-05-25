/**
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary.  The iterator iterates over the tree in
 * order of the key values (from smallest to largest).
 */
///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:  	         Keyword.java
// File:             KeyWord.java
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
public class KeyWord implements Comparable<KeyWord>,Prioritizable {
    private String key;
    private int occur;
    public KeyWord(String word){
        occur=0;
        this.key=word;
    }
    @Override
    public int compareTo(KeyWord keyWord) {
        return this.key.compareTo(keyWord.getWord());
    }

    @Override
    public boolean equals(Object o){
        KeyWord comp=(KeyWord) o;
        if(key.equals(comp.getWord()))
            return true;
        return false;
    }

    @Override
    public int getPriority() {
        return this.occur;
    }


    public String getWord(){
        return this.key;
    }

    public void increment(){
        this.occur++;
    }
}
