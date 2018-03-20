import java.util.NoSuchElementException;

public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {

    // default number of items the heap can hold before expanding
    private static final int INIT_SIZE = 100;
    private E[] arr;
    private int heapsize = 0;
    //pos starts from one
    private int parent(int pos){
        return pos/2;
    }
    private int left(int pos){
        return pos*2;
    }
    private int right(int pos){
        return pos*2+1;
    }
    private void swap(E[] ary,int p1, int p2){
        E temp=ary[p1];
        ary[p1]=ary[p2];
        ary[p2]=temp;
    }
    // TO DO:
    //
    // Add a no-argument constructor that constructs a heap whose underlying
    // array has enough space to store INIT_SIZE items before needing to 
    // expand.
    //
    // Add a 1-argument constructor that takes an integer parameter and 
    // constructs a heap whose underlying array has enough space to store the 
    // number of items given in the parameter before needing to expand.  If
    // the parameter value is less 0, an IllegalArgumentException is thrown.
    //
    // Add your code to implement the PriorityQueue ADT operations using a
    // heap whose underlying data structure is an array.
    public ArrayHeap(){
        arr=(E[])(new Prioritizable[INIT_SIZE]);
    }
    public ArrayHeap(int size){
        if(size<0)
            throw new IllegalArgumentException();
        else if(size==0)
            arr=(E[])(new Prioritizable[1]);
        else
            arr=(E[])(new Prioritizable[size]);
    }

    public boolean isEmpty() {
        return (heapsize==0);  // replace this stub with your code
    }

    public void insert(E item) {
        // add your code

        if(heapsize+2>arr.length)
            resize();
        arr[++heapsize]=item;

        int pos=heapsize;
        while(pos!=1 && (arr[parent(pos)].getPriority() < arr[pos].getPriority())) {
            swap(arr, parent(pos), pos);
            pos = pos / 2;
        }
    }

    public void resize(){
        if(arr.length==0)
            arr=(E[])(new Prioritizable[1]);
        else{
            E[] temp= (E[])(new Prioritizable[2*arr.length]);
            System.arraycopy(arr,0,temp,0,arr.length);
            arr=temp;
        }
    }

    public E removeMax() {
        if(heapsize==0)
            throw new NoSuchElementException();
        if(heapsize==1){
            heapsize--;
            return arr[1];
        }
        E ret=arr[1];
        arr[1]=arr[heapsize--];
        int curr=1,lft=this.left(curr),rgt=this.right(curr);
        while(lft<=heapsize){
            if(rgt>heapsize && (arr[lft].getPriority()>arr[curr].getPriority())){
                swap(arr,lft,curr);
                curr=lft;
                lft=left(curr);
                rgt=right(curr);
            }
            else if(rgt<=heapsize){
                int temp=(arr[lft].getPriority()>arr[rgt].getPriority())?lft : rgt;
                if(arr[temp].getPriority()>arr[curr].getPriority()) {
                    swap(arr, curr, temp);
                    curr = temp;
                    lft = left(curr);
                    rgt = right(curr);
                }
                else
                    break;
            }
            else
                break;
        }
        return ret;  // replace this stub with your code
    }

    public E getMax() {
        if(heapsize==0)
            throw new NoSuchElementException();
        return arr[1];  // replace this stub with your code
    }

    public int size() {
        return heapsize;  // replace this stub with your code
    }
}
