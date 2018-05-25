///////////////////////////////////////////////////////////////////////////////
//Main Class File:  TestSorts.java
//File:             ComparisonSort.java
//Semester:         Fall 2011
//
//Author:           Xianrun Qu
//CS Login:         xianrun
//Lecturer's Name:  Charles Fischer
//Lab Section:      NA
//
///////////////////////////////////////////////////////////////////////////////
//Partner:          Yiqiao Xin
//CS Login:         yiqiao
//Lecturer's Name:  Charles Fischer
//Lab Section:      NA
//
///////////////////////////////////////////////////////////////////////////////

/**
 * This class implements seven different comparison sorts:
 * <ul>
 * <li>selection sort</li>
 * <li>insertion sort</li>
 * <li>merge sort</li>
 * <li>quick sort using median-of-three partitioning</li>
 * <li>heap sort</li>
 * <li>shaker sort</li>
 * <li>two-way insertion sort</li>
 * </ul>
 * It also has a method that runs all the sorts on the same input array and
 * prints out statistics.
 */

public class ComparisonSort {
    private static int moves = 0; // the moves counter

    /**
     * Sorts the given array using the selection sort algorithm. You may use
     * either the algorithm discussed in the on-line reading or the algorithm
     * discussed in lecture (which does fewer data moves than the one from the
     * on-line reading). Note: after this method finishes the array is in sorted
     * order.
     *
     * @param <E>
     *            the type of values to be sorted
     * @param arr
     *            the array to sort
     **/
    public static <E extends Comparable<E>> void selectionSort(E[] arr) {
        // TODO: implement this sorting algorithm
        E min;
        int len=arr.length, minInd=0;
        for(int k=0; k<len; k++){
            moves++;
            min=arr[k];
            minInd=k;
            for(int j=k+1; j<len; j++){
                if(arr[j].compareTo(min)<0){
                    moves++;
                    min=arr[j];
                    minInd=j;
                }
            }
            arr[minInd]=arr[k];
            arr[k]=min;
            moves+=2;
        }
    }


    /**
     * Sorts the given array using the insertion sort algorithm. Note: after
     * this method finishes the array is in sorted order.
     *
     * @param <E>
     *            the type of values to be sorted
     * @param arr
     *            the array to sort
     **/
    public static <E extends Comparable<E>> void insertionSort(E[] arr) {
        int j, length = arr.length;

        for (int i = 1; i < length; i++) {
            E tmp;

            tmp = arr[i];
            moves++;

            j = i - 1;
            while ((j >= 0) && (arr[j].compareTo(tmp) > 0)) {
                arr[j + 1] = arr[j]; // move one value over one place to the right
                moves++;
                j--;
            }

            arr[j + 1] = tmp;
            moves++;
        }
    }

    /**
     * Sorts the given array using the merge sort algorithm. Note: after this
     * method finishes the array is in sorted order.
     *
     * @param <E>
     *            the type of values to be sorted
     * @param A
     *            the array to sort
     **/
    /**
     * Sorts the given array using the merge sort algorithm. Note: after this
     * method finishes the array is in sorted order.
     *
     * @param <E>
     *            the type of values to be sorted
     * @param A
     *            the array to sort
     **/
    public static <E extends Comparable<E>> void mergeSort(E[] A) {
        mergeHelper(A, 0, A.length - 1); // call the aux. function to do all the
        // work
    }

    /**
     * Recursive method initially called by the wrapper method mergeSort which
     * starts the merge sort algorithm.
     *
     * @param <E>
     *            the type of values to be sorted
     * @param A
     *            the array to sort
     * @param low
     *            the index of the low end of array to merge sort
     * @param high
     *            the index of the high end of array to merge sort
     */
    private static <E extends Comparable<E>> void mergeHelper(E[] A, int low, int high) {
        if (low == high)
            return; //break fron recursion

        int mid = (low + high) / 2;
        //left half
        mergeHelper(A, low, mid);
        //right half
        mergeHelper(A, mid + 1, high);
        //merge array
        E[] tmp = (E[]) (new Comparable[high - low + 1]);
        int left = low,right = mid + 1,ptr=0;

        while (true) {
            if(left>mid || right>high)
                break;
            if (A[left].compareTo(A[right]) < 0) {
                tmp[ptr] = A[left];
                moves++;
                left++;
            } else {
                tmp[ptr] = A[right];
                moves++;
                right++;
            }
            ptr++;
        }
        if(left<=mid) {
            for (int i=left;i <= mid;i++) {
                tmp[ptr] = A[i];
                moves++;
                ptr++;
            }
        }
        else {
            while (right <= high) {
                tmp[ptr] = A[right];
                moves++;
                right++;
                ptr++;
            }
        }

        for (E i : tmp) {
            A[low++]=i;
        }
        moves += tmp.length;
    }

    /**
     * Sorts the given array using the quick sort algorithm, using the median of
     * the first, last, and middle values in each segment of the array as the
     * pivot value. Note: after this method finishes the array is in sorted
     * order.
     *
     * @param <E>
     *            the type of values to be sorted
     * @param A
     *            the array to sort
     **/
    public static <E extends Comparable<E>> void quickSort(E[] A) {
        quickHelper(A, 0, A.length - 1);
    }



    /**
     * Helper function that conducts quick sort
     */
    private static <E extends Comparable<E>> void quickHelper(E[] A, int low, int high) {
        if (high - low >= 2) {
            // Recursive case
            int right = partition(A, low, high);
            quickHelper(A, low, right);
            quickHelper(A, right + 2, high);
        } else {
            if (A[low].compareTo(A[high]) > 0) {
                swap(A, low, high);
            }
        }
    }

    /**
     * Helper function for pivots
     */
    private static <E extends Comparable<E>> int partition(E[] A, int low, int high) {

        E pivot = medianOfThree(A,low,high);
        int left = low + 1;
        int right = high - 2;
        while (left <= right) {
            while (A[left].compareTo(pivot) < 0)
                left++;
            while (A[right].compareTo(pivot) > 0)
                right--;
            if (left > right) {

            } else {
                swap(A, left, right);
                right--;
                left++;

            }
        }
        swap(A, left, high - 1);
        return right;

    }
    /**
     * Get the Median of the three and change orders
     */
    private static <E extends Comparable<E>> E medianOfThree(E[] A, int low,
                                                             int high) {

        int middle = (low + high) / 2;
        if (A[low].compareTo(A[middle]) > 0)
            swap(A, low, middle);
        if (A[low].compareTo(A[high]) > 0)
            swap(A, low, high);
        if (A[middle].compareTo(A[high]) > 0)
            swap(A, middle, high);

        swap(A, middle, high - 1);
        return A[high - 1];
    }


    /**
     * Sorts the given array using the heap sort algorithm outlined below. Note:
     * after this method finishes the array is in sorted order.
     *
     * <p>
     * The heap sort algorithm is:
     * </p >
     *
     * <pre>
     * for each i from 1 to the end of the array
     *     insert A[i] into the heap (contained in A[0]...A[i-1])
     *
     * for each i from the end of the array up to 1
     *     remove the max element from the heap and put it in A[i]
     * </pre>
     *
     * @param <E>
     *            the type of values to be sorted
     * @param A
     *            the array to sort
     **/
    public static <E extends Comparable<E>> void heapSort(E[] A) {
        int length = A.length;
        int size = 0;
        E[] heap = (E[]) (new Comparable[length + 1]);
        size =  heapify(heap,A,length);
        // for each i from the end of the array up to 1
        // remove the max element from the heap and put it in A[i]
        for (int i = length - 1; i >= 0; i--) {
            // Save the root as the value to put at the end of the array
            moves++;
            A[i] = heap[1];

            // Set the last child as the root
            moves++;
            heap[1] = heap[size];
            // Set the old last child as null
            heap[size--] = null;

            // Heapify by swapping down
            int parent = 1;
            while (parent * 2 + 1 < heap.length) {
                boolean error = true;
                if (heap[parent * 2] != null ) {
                    if (heap[parent * 2].compareTo(heap[parent]) <= 0) {
                        error = false;
                    }
                } else {
                    error = false;
                }
                boolean error2 = true;
                if (heap[parent * 2 + 1] != null ) {
                    if ( heap[parent * 2 + 1].compareTo(heap[parent]) <= 0) {
                        error2 = false;
                    }
                } else {
                    error2 = false;
                }
                if (!(error||error2)) {
                    break;
                }

                // Swap the parent with the child if the children are bigger
                moves++;
                E temp = heap[parent];
                // If both children are bigger, pick the biggest and swap
                if (heap[parent * 2] != null ) {
                    if (heap[parent * 2 + 1] != null) {
                        if (heap[parent * 2].compareTo(heap[parent * 2 + 1]) <= 0) {

                            parent =heapSwap (heap, parent, 1, temp);
                        } else {

                            parent =heapSwap (heap, parent, 0, temp);

                        }
                    } else {
                        parent =heapSwap (heap, parent, 0, temp);
                    }

                } else {
                    parent =heapSwap (heap, parent, 1, temp);
                }
            }

        }
    }
    private static <E extends Comparable<E>> int heapify(E[] heap, E[] A, int length) {
        int size = 0;
        for (int i = 0; i < length; i++) {

            moves++;
            heap[++size] = A[i];

            // Heapify by swapping the value up
            int child = size;
            while (heap[child / 2] != null ) {

                if ( heap[child].compareTo(heap[child/2]) < 0){
                    break;
                }
                moves++;
                E temp = heap[child / 2];
                moves++;
                heap[child / 2] = heap[child];
                moves++;
                heap[child] = temp;
                child = child / 2;
            }
        }
        return size;

    }

    private static <E extends Comparable<E>> int heapSwap (E[] heap,int parent, int index,E temp) {

        if (index ==0) {

            moves++;
            heap[parent] = heap[parent * 2];
            moves++;
            heap[parent * 2] = temp;
            parent *= 2;
        } else if (index==1) {
            moves++;
            heap[parent] = heap[parent * 2 + index];
            moves++;
            heap[parent * 2 + index] = temp;
            parent = parent * 2 + index;

        }

        return parent;

    }


private static  <E extends java.lang.Comparable<E>> int[] MinMax(E[] A, int bgn, int end){
        if((end-bgn)<1)
            return null;
        int[] tmp = new int[2];
        if(A[bgn].compareTo(A[bgn+1])<0){
            tmp[0]=bgn;
            tmp[1]=bgn+1;
        }
        else{
            tmp[1]=bgn;
            tmp[0]=bgn+1;
        }
        int ptr=bgn+2;
        while(ptr<=end){
            if(ptr==end){
                if(A[ptr].compareTo(A[tmp[0]])<0){
                    tmp[0]=ptr;
                }
                if(A[ptr].compareTo(A[tmp[1]])>0){
                    tmp[1]=ptr;
                }
                return tmp;
            }
            int sm, lg;
            if(A[ptr].compareTo(A[ptr+1])<0){
                sm=ptr;
                lg=ptr+1;
            }
            else{
                lg=ptr;
                sm=ptr+1;
            }
            if(A[tmp[0]].compareTo(A[sm])>0)
                tmp[0]=sm;
            if(A[tmp[1]].compareTo(A[lg])<0)
                tmp[1]=lg;

            ptr+=2;

        }
        return tmp;
}

    /**
     * Sorts the given array using the selection2 sort algorithm outlined
     * below. Note: after this method finishes the array is in sorted order.
     * <p>
     * The selection2 sort is a bi-directional selection sort that sorts
     * the array from the two ends towards the center. The selection2 sort
     * algorithm is:
     * </p>
     *
     * <pre>
     * begin = 0, end = A.length-1
     *
     * // At the beginning of every iteration of this loop, we know that the
     * // elements in A are in their final sorted positions from A[0] to A[begin-1]
     * // and from A[end+1] to the end of A.  That means that A[begin] to A[end] are
     * // still to be sorted.
     * do
     *     use the MinMax algorithm (described below) to find the minimum and maximum
     *     values between A[begin] and A[end]
     *
     *     swap the maximum value and A[end]
     *     swap the minimum value and A[begin]
     *
     *     ++begin, --end
     * until the middle of the array is reached
     * </pre>
     * <p>
     * The MinMax algorithm allows you to find the minimum and maximum of N
     * elements in 3N/2 comparisons (instead of 2N comparisons). The way to do
     * this is to keep the current min and max; then
     * </p>
     * <ul>
     * <li>take two more elements and compare them against each other</li>
     * <li>compare the current max and the larger of the two elements</li>
     * <li>compare the current min and the smaller of the two elements</li>
     * </ul>
     *
     * @param <E>  the type of values to be sorted
     * @param A    the array to sort
     */
    public static <E extends java.lang.Comparable<E>> void selection2Sort(E[] A) {
        int begin=0,end=A.length-1;
        while(begin<end){
            int[] temp=MinMax(A,begin,end);
            swap(A,temp[1],end);
            swap(A,temp[0],begin);
            begin++;
            end--;
        }
    }


    /**
     * <b>Extra Credit:</b> Sorts the given array using the insertion2 sort
     * algorithm outlined below.  Note: after this method finishes the array
     * is in sorted order.
     * <p>
     * The insertion2 sort is a bi-directional insertion sort that sorts the
     * array from the center out towards the ends.  The insertion2 sort
     * algorithm is:
     * </p>
     * <pre>
     * precondition: A has an even length
     * left = element immediately to the left of the center of A
     * right = element immediately to the right of the center of A
     * if A[left] > A[right]
     *     swap A[left] and A[right]
     * left--, right++
     *
     * // At the beginning of every iteration of this loop, we know that the elements
     * // in A from A[left+1] to A[right-1] are in relative sorted order.
     * do
     *     if (A[left] > A[right])
     *         swap A[left] and A[right]
     *
     *     starting with with A[right] and moving to the left, use insertion sort
     *     algorithm to insert the element at A[right] into the correct location
     *     between A[left+1] and A[right-1]
     *
     *     starting with A[left] and moving to the right, use the insertion sort
     *     algorithm to insert the element at A[left] into the correct location
     *     between A[left+1] and A[right-1]
     *
     *     left--, right++
     * until left has gone off the left edge of A and right has gone off the right
     *       edge of A
     * </pre>
     * <p>
     * This sorting algorithm described above only works on arrays of even
     * length.  If the array passed in as a parameter is not even, the method
     * throws an IllegalArgumentException
     * </p>
     *
     * @param  A the array to sort
     * @throws IllegalArgumentException if the length or A is not even
     */
    public static <E extends Comparable<E>> void insertion2Sort(E[] A) {
        if(A.length%2!=0)
            throw new IllegalArgumentException();//check if the number is even

        int len = A.length, left = len / 2 - 1, right = len / 2, i;
        E tmp;
        //begin swapping
        if (A[left].compareTo(A[right]) > 0)
            swap(A, left, right);
        left--;
        right++;

        while (true) {
            if(left < 0 || right >= len) //break if reach the ends
                break;
            if (A[left].compareTo(A[right]) > 0) {
                swap(A, left, right);
            }

            tmp = A[right];
            moves++;
            //move right
            i = right - 1;
            while (true) {
                if((i < left + 1) || (A[i].compareTo(tmp) <= 0))
                    break;

                A[i + 1] = A[i];
                moves++;
                i--;
            }

            A[i + 1] = tmp;
            moves++;
            tmp = A[left];
            moves++;
            i = left + 1;

            //move left
            while (true) {
                if((i >= right) || (A[i].compareTo(tmp) >= 0)){
                    break;
                }

                A[i - 1] = A[i];
                moves++;
                i++;
            }
            A[i - 1] = tmp;
            moves++;
            left--;
            right++;
        }
        //goes over
    }


    /**
     * swapping helper
     *
     */
    private static <E extends Comparable<E>> void swap(E[] A, int left,int right) {
        E tmp = A[right];
        A[right] = A[left];
        A[left] = tmp;
        moves+=3;
    }

    /**
     * Method used to check if the array is in correct order
     */
    private static <E extends Comparable<E>> boolean testResults(E[] A) {
        int len = A.length, ptr=0;
        while (ptr<len-1) {
            if (A[ptr].compareTo(A[ptr+1]) > 0) {
                return false;
            }
            ptr++;
        }
        return true;
    }

    /**
     * reset test moves
     */
    private static void clearM() {
        moves = 0;
    }

    /**
     * for printing moves
     */
    private static int getM() {
        return moves;
    }

    /**
     * Internal helper for printing rows of the output table.
     *
     * @param sort
     *            name of the sorting algorithm
     * @param compares
     *            number of comparisons performed during sort
     * @param moves
     *            number of data moves performed during sort
     * @param milliseconds
     *            time taken to sort, in milliseconds
     */
    private static void printStatistics(String sort, int compares, int moves, long milliseconds) {
        System.out.format("%-15s%,15d%,15d%,15d\n", sort, compares, moves, milliseconds);
    }

    /**
     * Sorts the given array using the seven different sorting algorithms and
     * prints out statistics. The sorts performed are:
     * <ul>
     * <li>selection sort</li>
     * <li>insertion sort</li>
     * <li>merge sort</li>
     * <li>quick sort using median-of-three partitioning</li>
     * <li>heap sort</li>
     * <li>shaker sort</li>
     * <li>two-way insertion sort</li>
     * </ul>
     * <p>
     * The statistics displayed for each sort are: number of comparisons, number
     * of data moves, and time (in milliseconds).
     * </p>
     * <p>
     * Note: each sort is given the same array (i.e., in the original order) and
     * the input array A is not changed by this method.
     * </p>
     *
     * @param A
     *            the array to sort
     **/
    static public void runAllSorts(SortObject[] A) {
        System.out.format("%-15s%15s%15s%15s\n", "algorithm", "data compares",
                "data moves", "milliseconds");
        System.out.format("%-15s%15s%15s%15s\n", "---------", "-------------",
                "----------", "------------");

        SortObject[] testArr = A.clone(); //temperary array to test
        long tmstrt = System.currentTimeMillis();
        selectionSort(testArr);
        long tmstp = System.currentTimeMillis();
        printStatistics("selection", SortObject.getCompares(), getM(),
                tmstp - tmstrt);
        /**if (!testResults(testArr))
            System.err.println("Wrong Results");
        **/
        resetAll();//method for resetting

        testArr = A.clone();
        tmstrt = System.currentTimeMillis();
        insertionSort(testArr);
        tmstp = System.currentTimeMillis();
        printStatistics("insertion", SortObject.getCompares(), getM(),
                tmstp - tmstrt);
        /*if (!testResults(testArr))
         System.err.println("Wrong Results");
*/
        resetAll();


        testArr = A.clone();
        tmstrt = System.currentTimeMillis();
        mergeSort(testArr);
        tmstp = System.currentTimeMillis();
        printStatistics("merge", SortObject.getCompares(), getM(),
                tmstp - tmstrt);
        /**if (!testResults(testArr))
         System.err.println("Wrong Results");
         **/
        resetAll();

        testArr = A.clone();
        tmstrt = System.currentTimeMillis();
        quickSort(testArr);
        tmstp = System.currentTimeMillis();
        printStatistics("quick", SortObject.getCompares(), getM(),
                tmstp - tmstrt);
        /**if (!testResults(testArr))
         System.err.println("Wrong Results");
         **/
        resetAll();

        testArr = A.clone();
        tmstrt = System.currentTimeMillis();
        heapSort(testArr);
        tmstp = System.currentTimeMillis();
        printStatistics("heap", SortObject.getCompares(), getM(),
                tmstp - tmstrt);
        /**if (!testResults(testArr))
         System.err.println("Wrong Results");
         **/
        resetAll();

        testArr = A.clone();
        tmstrt = System.currentTimeMillis();
        selection2Sort(testArr);
        tmstp = System.currentTimeMillis();
        printStatistics("selection2", SortObject.getCompares(), getM(),
                tmstp - tmstrt);
        //if (!testResults(testArr))
          //  System.err.println("Wrong Results");

        resetAll();

        testArr = A.clone();
        tmstrt = System.currentTimeMillis();
        insertion2Sort(testArr);
        tmstp = System.currentTimeMillis();
        printStatistics("insertion2", SortObject.getCompares(),
                getM(), tmstp - tmstrt);
        /**if (!testResults(testArr))
         System.err.println("Wrong Results");
         **/
        resetAll();
    }
    private static void resetAll(){
        SortObject.resetCompares();
        clearM();
    }
}