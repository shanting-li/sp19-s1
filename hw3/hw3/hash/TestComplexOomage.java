package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        List<Integer> namesList = Arrays.asList( 8, 12, 1, 0, 8);
        List<Integer> p1 = new ArrayList<>(namesList);

        namesList = Arrays.asList( 129, 12, 1, 0, 8);
        List<Integer> p2 = new ArrayList<>(namesList);

        namesList = Arrays.asList( 130, 12, 1, 0, 8);
        List<Integer> p3 = new ArrayList<>(namesList);

        namesList = Arrays.asList( 131, 12, 1, 0, 8);
        List<Integer> p4 = new ArrayList<>(namesList);

        namesList = Arrays.asList( 134, 12, 1, 0, 8);
        List<Integer> p5 = new ArrayList<>(namesList);

        namesList = Arrays.asList( 135, 12, 1, 0, 8);
        List<Integer> p6 = new ArrayList<>(namesList);

        namesList = Arrays.asList( 136, 12, 1, 0, 8);
        List<Integer> p7 = new ArrayList<>(namesList);

        ComplexOomage t1 = new ComplexOomage(p1);
        ComplexOomage t2 = new ComplexOomage(p2);
        ComplexOomage t3 = new ComplexOomage(p3);
        ComplexOomage t4 = new ComplexOomage(p4);
        ComplexOomage t5 = new ComplexOomage(p5);
        ComplexOomage t6 = new ComplexOomage(p6);
        ComplexOomage t7 = new ComplexOomage(p7);

        deadlyList.add(t1);
        deadlyList.add(t2);
        deadlyList.add(t3);
        deadlyList.add(t4);
        deadlyList.add(t5);
        deadlyList.add(t6);
        deadlyList.add(t7);

        // Your code here.


        //HashTableVisualizer.visualize(deadlyList,100, 0.5);
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
