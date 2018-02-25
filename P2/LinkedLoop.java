import java.util.*;
class LinkedLoop<E> implements LoopADT<E>, Iterable<E>{
    private DblListnode<E> curr=null;
    private int num=0;
    public LinkedLoop(){

    }
    @Override
    public void add(E item) {
        this.num++;
        if(curr==null){
            curr=new DblListnode<E>(item);
            curr.setPrev(curr);
            curr.setNext(curr);//self-linked
            return;
        }
        if(num==2){
          DblListnode<E> temp=curr;
          curr=new DblListnode<E>(item);
          temp.setNext(curr);
          temp.setPrev(curr);
          curr.setNext(temp);
          curr.setPrev(temp);
          return;
        }
        DblListnode<E> temp=curr.getPrev();
        //add the item before
        curr.setPrev(new DblListnode<E>(item));//before current
        curr.getPrev().setNext(curr);
        curr=curr.getPrev();
        //circular
        temp.setNext(curr);
        curr.setPrev(temp);
    }

    @Override
    public E getCurrent() throws EmptyLoopException {
        if(curr==null)
            throw new EmptyLoopException();
        return curr.getData();
    }

    @Override
    public E removeCurrent() throws EmptyLoopException {
      
        if(curr==null)
            throw new EmptyLoopException();
        E ret=curr.getData();// return data
        this.num--;
        
        if(num==0)
          curr=null;
        else if(num==1){
          curr=curr.getPrev();
          curr.setNext(curr);
          curr.setPrev(curr);
        }
        else{
        DblListnode<E> pre=curr.getPrev();
        curr=curr.getNext();
        pre.setNext(curr);
        curr.setPrev(pre);
        }
        return ret;
    }

    @Override
    public void next() {
        if(curr==null)
            return;
        curr=curr.getNext();
    }

    @Override
    public void previous() {
        if(curr==null)
            return;
        curr=curr.getPrev();
    }

    @Override
    public boolean isEmpty() {
        if(curr==null)
            return true;
        return false;
    }

    @Override
    public int size() {
        return num;
    }

    @Override
    public Iterator<E> iterator() {
        LinkedLoopIterator<E> itrpar=new LinkedLoopIterator<E> (curr);
        return itrpar;
    }
}
