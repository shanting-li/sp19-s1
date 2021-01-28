public class ArrayDeque <T> {
    /** invariants
     * size: the number of items in the list;
     * last: pointing to the last item of the list;
     * items: the array box containing the list;
     */
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    /**starting size of your array should be 8*/
    public ArrayDeque(){
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
        size = 0;
    }

    private void resize(){
        T[] a = (T []) new Object[size * 3];

        for(int i =0; i < size; i++){
            int dex = nextFirst + 1 + i;
            if (dex >= items.length){
                dex -= items.length;
            }
            a[size + i] = items[dex];
        }
        nextFirst = size - 1;
        nextLast = nextFirst + size + 1;
        items = a;
    }

    private void shiftFirst(){
        if(nextFirst == 0){
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
    }

    private void shiftLast(){
        if(nextLast == items.length - 1){
            nextLast = 0;
        } else {
            nextLast += 1;
        }
    }


    /** adds an item of type T to the front of the deque
     *  must not involve any looping or recursion
     *  Big O = c.
     */
    public void addFirst (T x){
        if(size == items.length - 2) {
            resize();
        }
        items[nextFirst] = x;
        shiftFirst();
        size += 1;
    }


    /** Adds an item of type T to the back of the deque.
     * must not involve any looping or recursion
     * Big O = c.*/
    public void addLast(T x){
        if(size == items.length - 2) {
            resize();
        }
        items[nextLast] = x;
        shiftLast();
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty(){
        return size == 0;
    }

    /** Returns the number of items in the deque.
     * Big O = c.*/
    public int size(){
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space.*/
    public void printDeque(){
        for(int i = 0; i < size; i ++){
            int printIndex = nextFirst + 1 + i;
            if (printIndex >= items.length){
                printIndex -= items.length;
            }
            System.out.print(items[printIndex].toString() + " ");
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * must not involve any looping or recursion
     * Big O = c.
     * For arrays of length 16 or more, your usage factor should always be at least 25%.*/
    public T removeFirst() {
        if(size == 0){
            return null;
        }else{
            T ans;
            if (nextFirst == items.length - 1) {
                ans = items[0];
                items[0] = null;
                nextFirst = 0;
            } else {
                ans = items[nextFirst + 1];
                items[nextFirst + 1] = null;
                nextFirst += 1;
            }
            size -= 1;

            if(size * 4 < items.length & items.length > 16){
                resize();
            }

            return ans;
        }

    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * must not involve any looping or recursions
     * Big O = c.
     * For arrays of length 16 or more, your usage factor should always be at least 25%*/
    public T removeLast(){
        if(size == 0){
            return null;
        }else{
            T ans;
            if (nextLast == 0) {
                ans = items[items.length - 1];
                items[size - 1] = null;
                nextLast = size - 1;
            } else {
                ans = items[nextLast - 1];
                items[nextLast - 1] = null;
                nextLast -= 1;
            }
            size -= 1;

            if (size * 4 < items.length & items.length > 16){
                resize();
            }

            return ans;
        }

    }

    /** Gets the item at the given index, where 0 is the front.
     * If no such item exists, returns null. Must not alter the deque!
     * use iteration, not recursion
     */
    public T get(int index){
        int getIndex = nextFirst + 1 + index;
        if (index >= size) {
            return null;
        } else if (getIndex >= items.length) {
            getIndex -= items.length;
        }
        return items[getIndex];
    }

}
