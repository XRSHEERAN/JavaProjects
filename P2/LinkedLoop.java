import java.util.*;
class LinkedLoop<E> implements LoopADT<E>{
    private DblListnode<E> curr=null;
    private DblListnode<E> head=new DblListnode<E>(null);
    private int num=0;
    public LinkedLoop(){

    }
    @Override
    public void add(E item) {
        this.num++;
        if(curr==null){
            curr=new DblListnode<E>(item);
            head.setPrev(curr);
            return;
        }
        //add the item before
        curr.setPrev(new DblListnode<E>(item));//before current
        DblListnode addee=curr.getPrev();//get new
        addee.setNext(curr);//set next
        curr=addee;//move curr
        //circular
        DblListnode temp=head.getPrev();// the end of list
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
        this.num--;
        DblListnode<E> temp=head.getPrev();
        if(curr==temp){
            head.setPrev(null);
            E data= curr.getData();
            curr=null;
            return data;
        }

        E ret=curr.getData();// return data
        curr=curr.getNext();
        if(curr==temp) {
            curr.setPrev(null);
            curr.setNext(null);
        }
        else{
            temp.setNext(curr);
            curr.setPrev(temp);
        }
        return ret;
    }

    @Override
    public void next() {
        if(curr==null || curr.getNext()==null)
            return;
        curr=curr.getNext();
    }

    @Override
    public void previous() {
        if(curr==null || curr.getPrev()==null)
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
        Iterator<E> ptr=new LinkedLoopIterator (head.getPrev());
        return null;
    }
}
