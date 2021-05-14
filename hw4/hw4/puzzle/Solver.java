package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class Solver {
    private WorldState start;
    private Node end;
    private MinPQ<Node> pq;
    private HashSet<Node> marked;
    private HashMap<WorldState, Double> dToG = new HashMap<>();

    /**
     * a WorldState.
     * the number of moves made to reach this world state from the initial state.（其实就是distTo[]）
     * a reference to the previous search node.（其实就是edgeTo[]）
     */
    private class Node implements Comparable {
        private WorldState self;
        private int distToStart;
        private Node previousNode;

        public Node(WorldState s, int d, Node p) {
            self = s;
            distToStart = d;
            previousNode = p;
        }

        private double getDToG(WorldState x) {
            if (!dToG.containsKey(x)) {
                dToG.put(x, (double) x.estimatedDistanceToGoal());
            }
            return dToG.get(x);
        }

        @Override
        public int compareTo(Object o) {
            Node that = (Node) o;


            String wordClass = "class hw4.puzzle.Word";

            //优化2 cache 对 distance to goal的估算，每个word只算一次，就存入map中
            double thisP = this.distToStart + getDToG(this.self) - 0.2;
            double thatP = that.distToStart + getDToG(that.self) - 0.2;

            //word puzzle还需要考虑字母出现的位置先后和字母本身的顺序
            if (this.self.getClass().toString().equals(wordClass)) {
                double add = this.self.toString().compareTo(that.self.toString());
                if (add < 0) {
                    thisP -= 0.2;
                } else if (add > 0) {
                    thisP += 0.2;
                }
            }

            return Double.compare(thisP, thatP);
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Node that = (Node) o;
            return this.self.equals(that.self);
        }

        @Override
        public int hashCode() {
            return this.self.hashCode();
        }
    }

    /**
     * 1.创建一个pq，pq的priority=每个node从起点至今的move + node到终点的估算值（其实就是distTo+h(w)）
     * 2.移除pq中的最小值F，mark之
     * 如果F就是目标，结束
     * 否则搜索F的每个邻居，更新每个邻居的s-t距离，然后把这些新的邻居放入pq
     * 重复这个循环
     */
    public Solver(WorldState initial) {
        marked = new HashSet<>();
        start = initial;

        pq = new MinPQ<>();
        Node s = new Node(initial, 0, null);
        pq.insert(s);

        end = s;
        while (!(end.self.isGoal() || pq.isEmpty())) {
            end = pq.delMin();
            marked.add(end);
            //要注意一个节点没有neighbors的情况
            if (end.self.neighbors() != null) {
                for (WorldState w : end.self.neighbors()) {
                    if (!marked.contains(w)) {
                        pq.insert(new Node(w, end.distToStart + 1, end));
                    }
                }
            }

        }

    }

    public int moves() {
        return end.distToStart;
    }

    public Iterable<WorldState> solution() {
        Stack<WorldState> reverse = new Stack<>();
        Stack<WorldState> ans = new Stack<>();

        Node temp = end;
        while (temp.previousNode != null) {
            reverse.push(temp.self);
            temp = temp.previousNode;
            }
        reverse.push(start);

        while (!reverse.isEmpty()) {
            ans.push(reverse.pop());
        }

        return ans;



    }
}
