import java.util.Iterator;

public class BSTDictionary<K extends Comparable<K>> implements DictionaryADT<K> {
    private BSTnode<K> root;  // the root node
    private BSTnode<K> curr;  // the root node
    private int numItems;     // the number of items in the dictionary

    // TO DO:
    //
    // Add a no-argument constructor
    //
    // Add your code to implement the Dictionary ADT operations using a binary
    // search tree.
    // You may use any code given in the on-line reading on BSTs.
    public BSTDictionary(){
        numItems=0;
    }
    public void insert(K key) throws DuplicateException {
        // add your code
        if(root==null){
            root=new BSTnode<K>(null);
            return;
        }
        BSTnode<K> prev=root;
        curr=root;
        while (curr != null) {
            
        }
    }

    public boolean delete(K key) {
        return false;  // replace this stub with your code
    }

    public K lookup(K key) {
        return null;  // replace this stub with your code
    }

    public boolean isEmpty() {
        return false;  // replace this stub with your code
    }

    public int size() {
        return 0;  // replace this stub with your code
    }
    
    public int totalPathLength() {
        return 0;  // replace this stub with your code
    }
    
    public Iterator<K> iterator() {
        return null;  // replace this stub with your code
    }
}
