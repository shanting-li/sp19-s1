public class ArrayDeque<T> implements Deque<T> {
    /** invariants
     * size: the number of items in the list;
     * last: pointing to the last item of the list;
     */
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    /**starting size of your array should be 8*/
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
        size = 0;
    }

    private void resize() {
        int newLen = Math.max(8, size * 3);
        T[] a = (T []) new Object[newLen];
        int midOld = size / 2;
        int sNew = a.length / 2 - midOld;
        System.arraycopy(items, nextFirst + 1, a, sNew, size);
        items = a;
        nextFirst = sNew - 1;
        nextLast = sNew + size;
    }

    /** adds an item of type T to the front of the deque
     *  must not involve any looping or recursion
     *  Big O = c.
     */
    @Override
    public void addFirst(T x) {
        if (nextFirst == 0) {
            resize();
        }
        items[nextFirst] = x;//error:item.length = 0, nextFirst = -1
        nextFirst -= 1;
        size += 1;
    }


    /** Adds an item of type T to the back of the deque.
     * must not involve any looping or recursion
     * Big O = c.*/
    @Override
    public void addLast(T x) {
        if (nextLast == items.length - 1) {
            resize();
        }
        items[nextLast] = x; //error:item.length = 0, nextLast = 0
        nextLast += 1;
        size += 1;
    }

    /** Returns true if deque is empty, false otherwise.*/
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque.
     * Big O = c.*/
    @Override
    public int size(){
        return size;
    }

    /** Prints the items in the deque, separated by a space.*/
    @Override
    public void printDeque() {
        for (int i = 0; i < size; i ++) {
            System.out.print(items[nextFirst + 1 + i].toString() + " ");
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * must not involve any looping or recursion
     * Big O = c.
     * For arrays of length 16+, usage factor should always be at least 25%.*/
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            T ans = items[nextFirst + 1];
            items[nextFirst + 1] = null;
            nextFirst += 1;
            size -= 1;
            if (size * 4 < items.length & items.length > 16) {
                resize();
            }
            return ans;
        }

    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * must not involve any looping or recursions
     * Big O = c.
     * For arrays of length 16+, usage factor should always be at least 25%*/
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T ans = items[nextLast - 1];
            items[nextLast - 1] = null;
            nextLast -= 1;
            size -= 1;
            if (size * 4 < items.length & items.length > 16) {
                resize();
            }
            return ans;
        }

    }

    /** Gets the item at the given index, where 0 is the front.
     * If no such item, returns null.
     * use iteration, not recursion
     */
    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return items[nextFirst + 1 + index];
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> a = new ArrayDeque<>();
        a.addFirst(5);
        a.printDeque();
    }

}


