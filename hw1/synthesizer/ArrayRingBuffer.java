// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import synthesizer.AbstractBoundedQueue;

import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* inherited from interface BoundedQueue:
     * public boolean isEmpty()
     * public boolean isFull()
     *
     * inherit from abstract class AbstractBoundedQueue:
     * public int capacity();
     * public int fillCount();
     *
     * override from interface:
     * public abstract T peek();
     * public abstract T dequeue();
     * public abstract void enqueue(T x);
     */

    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        first = 0;
        last = 0;
        this.fillCount = 0; //number of items currently in the buffer
        this.capacity = capacity;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.

        // if the queue is full, do nothing,
        // otherwise give x to the last item,update fillCount
        if (isFull()) {
            return; // change to throw runtime error
        } else {
            rb[last] = x;
            fillCount += 1;
        }

        // if now the queue has 1 item, then update first
        if (fillCount == 1) {
            if (first == 0) {
                first = capacity - 1;
            } else {
                first -= 1;
            }
        }

        // update last
        if (last == capacity - 1) {
            last = 0;
        } else {
            last += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        T ans;

        // if the queue is empty, do nothing
        // otherwise update first, give ans rb[first] and update fillCount
        if (isEmpty()) {
            return null; // change to throw runtime error
        } else {
            if (first == capacity - 1) {
                first = 0;
            } else {
                first += 1;
            }
            ans = rb[first];
            fillCount -= 1;
        }

        // if now the queue is empty, then update last
        if (fillCount == 0) {
            last = first;
        }

        return ans;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        int temp;
        if (first == capacity - 1) {
            temp = 0;
        } else {
            temp = first + 1;
        }
        return rb[temp];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
