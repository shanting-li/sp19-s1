package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;
    @Override
    public int capacity() {
        return capacity;
    }
    public int fillCount(){
        return fillCount;
    }
    /* inherited from interface BoundedQueue:
     * public boolean isEmpty()
     * public boolean isFull()
     * public abstract T peek();
     * public abstract T dequeue();
     * public abstract void enqueue(T x);*/
}
