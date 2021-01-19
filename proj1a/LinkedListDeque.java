public class LinkedListDeque <T> {
    /** invariants
     * size: the number of items in the list;
     * sentinel:the only node in an empty list;
     * sentinel.next:pointing to the first item of the list;
     * last: pointing to the last item of the list;
     */
    private int size;
    private T place;
    private class TNode<T>{
        public T item;
        public TNode next;
        public TNode pre;
        public TNode(T x, TNode y, TNode z){
            item = x;
            next = y;
            pre = z;
        }
    }
    private TNode sentinel;
    private TNode last;

    public LinkedListDeque(){
        sentinel = new TNode (place, null,null);
        sentinel.next = sentinel;
        sentinel.pre = sentinel;
        last = sentinel;
        size = 0;
    }

    public LinkedListDeque(T x){
        sentinel = new TNode (place, null, null);
        sentinel.next= new TNode (x, sentinel,sentinel);
        sentinel.pre = sentinel.next;
        last = sentinel.next;
        size = 1;
    }

    /** adds an item of type T to the front of the deque
     *  must not involve any looping or recursion
     *  Big O = c.
     */
    public void addFirst (T x){
        sentinel.next = new TNode (x,sentinel.next,sentinel );
        sentinel.next.next.pre = sentinel.next;
        size += 1;
    }

    /** Adds an item of type T to the back of the deque.
     * must not involve any looping or recursion
     * Big O = c.*/
    public void addLast(T x){
        TNode temp = new TNode (x,sentinel,last);
        last.next = temp;
        last = temp;
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
        TNode temp= sentinel.next;
        for(int i = 0; i < size; i ++){
            System.out.print(temp.item.toString() + " ");
        }
    }

    /** Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * must not involve any looping or recursion
     * Big O = c.
     * Do not maintain references to items that are no longer in the deque.*/
    public T removeFirst(){
        if (size == 0){
            return null;
        } else {
            TNode temp = sentinel.next;
            T ans = (T) temp.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.pre = sentinel;
            size -= 1;
            temp = null;
            return ans;
        }
    }

    /** Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * must not involve any looping or recursion
     * Big O = c.
     * Do not maintain references to items that are no longer in the deque.*/
    public T removeLast(){
        if (size == 0){
            return null;
        } else {
            T ans = (T) last.item;
            last.pre.next = sentinel;
            sentinel.pre = last.pre;
            last = last.pre;
            size -= 1;
            return ans;
        }
    }

    /** Gets the item at the given index, where 0 is the front.
     * If no such item exists, returns null. Must not alter the deque!
     * use iteration, not recursion
     */
    public T get(int index){
        if (size == 0){
            return null;
        } else {
            TNode temp = sentinel;
            for(int i = 0; i < index; i ++){
                temp = temp.next;
            }
            return (T) temp.item;
        }

    }

    private LinkedListDeque subCopy(LinkedListDeque x){
        LinkedListDeque ans = new LinkedListDeque();
        ans.sentinel = x.sentinel.next;
        ans.size = x.size-1;
        return ans;
    }
    /**Same as get, but uses recursion*/
    public T getRecursive(int index){
        if(index >= size) {
            return null;
        } else if (index == 0){
                return (T) sentinel.next.item;
            } else{
                return (T) subCopy(this).getRecursive(index-1);
            }
        }

}
