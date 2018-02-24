import java.util.*;
import java.io.*;
public class LinkedLoopIterator<E> implements Iterable<E>{
    private DblListnode<E> curr;
    public LinkedLoopIterator<E>(DblListnode<E> pass){
        this.curr=pass;
    }
    @Override
    public boolean hasNext(){
        if(curr==null)
            return false;
        return true;
    }
    @Override
    public E next(){
        if(!this.hasNext())
            throw new NoSuchElementException();
        E ret=curr.getData();
        curr=curr.getNext();
        return ret;
    }
    @Override
    public void remove(){
        throw new UnsupportedEncodingException();
    }
}