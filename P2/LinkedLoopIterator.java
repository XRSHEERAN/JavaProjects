import java.util.*;
public class LinkedLoopIterator<E> implements Iterator<E>{
    private DblListnode<E> curr;
    private DblListnode<E> head;
    
    		@Override
    		public boolean hasNext(){
    	        if(curr==null || curr==head)
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
    	        throw new UnsupportedOperationException();
    	    }
    	
    public LinkedLoopIterator(DblListnode<E> pass){
        this.curr=pass;
        this.head=pass;
    }
    
}
