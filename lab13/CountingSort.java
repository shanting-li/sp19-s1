/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 *
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    private static int[] help(int[] arr, int startIndex) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i = startIndex; i < arr.length; i++) {
            max = Math.max(arr[i], max);
            min = Math.min(arr[i], min);
        }

        // 创建count[]，记录每个alphabet出现的次数
        int offset = Math.min(0,min);
        int[] count = new int[max + 1 + Math.abs(offset)];

        for (int i = startIndex; i < arr.length; i++) {
            count[arr[i] - offset] ++;
        }

        // 创建start，记录每个alphabet在sorted[]里出现的index
        int[] start = new int[count.length];
        int add = startIndex;
        for (int i = 0; i < start.length; i++) {
            start[i] = add;
            add += count[i];
        }


        //创建sorted[]
        int[] sorted = new int[arr.length];
        System.arraycopy(arr, 0, sorted, 0, startIndex);
        for (int i = startIndex; i < arr.length; i++) {
            int value = arr[i];
            int index = start[value - offset];
            sorted[index] = value;
            start[value - offset] ++;
        }
        return sorted;
    }
    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        // TODO make counting sort work with arrays containing negative numbers.
        // clear Min_Value
        int[] sorted = new int[arr.length];
        int minNum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                int temp = sorted[minNum];
                sorted[minNum] = arr[i];
                sorted[i] = temp;
                minNum ++;
            } else {
                sorted[i] = arr[i];
            }
        }

        // sort the rest
        sorted = help(sorted, minNum);
        return sorted;
    }

    public static void main(String[] args) {
        //int[] x = {9, 5, 2, 1, 5, 3, 0, 3, 1, 1};
        int[] x = {9, 5, -4, 2, 1, -2, 5, 3, 0, -2, 3, 1, 1};
        //int[] x = {-85, Integer.MIN_VALUE, -23, -101};
        //int[] x = {-85, -85, -85 -30, -23, -3};
        int[] y = betterCountingSort(x);
        for (int i : y) {
            System.out.print(i + " ");
        }
        //System.out.println(Integer.MIN_VALUE);
    }
}
