package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void enqueueTest() {
        ArrayRingBuffer<Integer> x = new ArrayRingBuffer(10);
        for (int i = 0; i < 10; i++) {
           x.enqueue(i);
        } //x:0,1,2,...,8,9
        x.enqueue(15);
        System.out.println(x.peek());//0
        System.out.println(x.peek());//0
        for (int i = 0; i < 10; i++) {
            System.out.print(x.dequeue() + " ");//0,1,2,...,8,9
        }

    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
