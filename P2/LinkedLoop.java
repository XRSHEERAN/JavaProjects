import java.util.*;
class LinkedLoop<E> implements LoopADT<E>{
    @Override
    public void add(E item) {

    }

    @Override
    public E getCurrent() throws EmptyLoopException {
        return null;
    }

    @Override
    public E removeCurrent() throws EmptyLoopException {
        return null;
    }

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
