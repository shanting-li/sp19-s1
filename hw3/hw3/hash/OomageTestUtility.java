package hw3.hash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        HashMap<Integer, Integer> m = new HashMap<>();

        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            if (m.containsKey(bucketNum)) {
                int newCount = m.get(bucketNum) + 1;
                m.put(bucketNum, newCount);
            } else {
                m.put(bucketNum, 1);
            }

        }

        boolean ans = true;
        for (int k : m.keySet()) {
            if (m.get(k) < oomages.size() / 50 || m.get(k) > oomages.size() / 2.5) {
                ans = false;
            }
        }
        return ans;

    }
}
