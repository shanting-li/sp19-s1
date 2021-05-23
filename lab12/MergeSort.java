import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!
        // 新建一个queues的数列
        // 遍历每个元素，把这个新元素建成一个queue，放入到queues中

        Queue<Queue<Item>> queues = new Queue<>();
        for (Item i : items) {
            Queue<Item> single = new Queue<>();
            single.enqueue(i);
            queues.enqueue(single);
        }
        return queues;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        // 对2个数组调用get min，把得到的这个数放到一个新queue里面，
        // 直到其中一个数列为空，把另一个数组直接接到新queue后面
        Queue<Item> merged = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            Item i = getMin(q1, q2);
            merged.enqueue(i);
        }
        return merged;
    }

    private static <Item extends Comparable> Queue<Queue<Item>> helpMergeSort(
            Queue<Queue<Item>> q) {
        if (q.size() <= 1) {
            return q;
        } else {
            Queue<Item> first = q.dequeue();
            Queue<Item> second = q.dequeue();
            q.enqueue(mergeSortedQueues(first, second));
            return helpMergeSort(q);
        }
    }
    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        // 1 把原数列items平分一直平分到只有1个数，makeSingleItemQueues
        // 2 把单个的queue 两个两个merge起来，mergeSortedQueues，然后再两个两个merge起来
        if (items.isEmpty()) {
            return items;
        }

        Queue<Queue<Item>> queues = makeSingleItemQueues(items);
        return helpMergeSort(queues).peek();


        /* while 写法
        Queue<Queue<Item>> queues = makeSingleItemQueues(items);
        int count = 1;
        Queue<Item> first = new Queue<>();
        Queue<Item> second = new Queue<>();
        Queue<Queue<Item>> temp = new Queue<>();

        while (temp.size() != 1) {
            while (!queues.isEmpty()) {
                if (count % 2 != 0) {
                    first = queues.dequeue();
                } else {
                    second = queues.dequeue();
                    temp.enqueue(mergeSortedQueues(first, second));
                    first = new Queue<>();
                    second = new Queue<>();
                }
                count ++;

            }
            if (!first.isEmpty()) {
                temp.enqueue(first);
            }

            if (temp.size() != 1) {
                queues = temp;
                temp = new Queue<>();
            }
        }
        return temp.peek();*/
    }

    public static void main(String[] args) {
        Queue<Integer> test = new Queue<>();
        test.enqueue(5);
        test.enqueue(8);
        test.enqueue(9);
        test.enqueue(3);

        Queue<Integer> sorted = mergeSort(test);
        System.out.println(test);
        System.out.println(sorted);


        // test makeSingleItemQueues
        /*Queue<Queue<Integer>> s = makeSingleItemQueues(test);
        System.out.println(test);
        for (Queue<Integer> q : s) {
            System.out.print(q);
        }*/

        // test mergeSortedQueues
        /*Queue<Integer> s1 = new Queue<>();
        s1.enqueue(1);
        s1.enqueue(8);

        Queue<Integer> s2 = new Queue<>();
        s2.enqueue(2);
        s2.enqueue(4);
        s2.enqueue(9);
        s2.enqueue(14);

        Queue<Integer> mergeS1AndS2 = mergeSortedQueues(s1, s2);
        System.out.println(mergeS1AndS2.size());
        System.out.println(mergeS1AndS2);// 1,2,4,8,9,14*/

    }
}
