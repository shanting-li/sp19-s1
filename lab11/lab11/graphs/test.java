package lab11.graphs;

import java.util.PriorityQueue;

public class test {
    public static void main(String[] args) {
        PriorityQueue<Integer> x = new PriorityQueue<>();
        x.add(5);
        x.add(5);
        x.add(3);
        x.add(1);
        x.add(2);
        x.add(3);
        for (int s : x) {
            System.out.println(s);
        }

    }
}
