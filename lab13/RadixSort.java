import java.lang.reflect.Array;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        //遍历每个string，找出digit最多的那个，记录maxDigit
        //把每个String的每一位转化成数字，占位符位于后面，
        // 比如abc = [97,98,99], b = [98,0,0]
        //对每一位进行counting sort
        int maxDigit = 0;
        String[] sorted = new String[asciis.length];

        for (int i = 0; i < asciis.length; i++) {
            maxDigit = Math.max(asciis[i].length(), maxDigit);
            sorted[i] = asciis[i];
        }

        sortHelperLSD(sorted, maxDigit - 1);
        //sortHelperMSD(sorted, 0, asciis.length, 0);
        return sorted;

    }

    private static int getCountIndex(String s, int index) {
        if (index >= s.length() || index < 0) {
            return 0;
        } else {
            return s.charAt(index) + 1;
        }

        /*try{
            return s.charAt(index);
        } catch (StringIndexOutOfBoundsException e){
            return 0;
        }*/

    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        //把每个String的每一位转化成数字，占位符位于后面，
        // 比如abc = [97,98,99], b = [98,0,0]
        //对每一位进行counting sort
        if (index < 0) {
            return;
        } else {
            int[] count = new int[257];
            for (int i = 0; i < asciis.length; i++) {
                int countIndex = getCountIndex(asciis[i], index);
                count[countIndex] ++;
            }

            int[] start = new int[257];
            int add = 0;
            for (int i = 0; i < count.length; i++) {
                start[i] = add;
                add += count[i];
            }

            String[] temp = new String[asciis.length];
            System.arraycopy(asciis, 0, temp, 0, asciis.length);

            for (int i = 0; i < temp.length; i++) {
                String value = temp[i];
                int sortIndex = start[getCountIndex(temp[i], index)];
                asciis[sortIndex] = value;
                start[getCountIndex(temp[i], index)] ++;
            }

            sortHelperLSD(asciis, index - 1);
        }

    }


    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        if (end - start <= 1) {
            return;
        } else {
            //创建count[]
            int[] count = new int[257];
            for (int i = start; i < end; i++) {
                int countIndex = getCountIndex(asciis[i], index);
                count[countIndex]++;
            }

            //创建start[]
            int[] indexToStart = new int[257];
            int add = start;
            for (int i = 0; i < count.length; i++) {
                indexToStart[i] = add;
                add += count[i];
            }

            // counting sort
            String[] temp = new String[asciis.length];
            System.arraycopy(asciis, 0, temp, 0, asciis.length);
            for (int i = start; i < end; i++) {
                String value = temp[i];
                int sortIndex = indexToStart[getCountIndex(value, index)];
                asciis[sortIndex] = value;
                indexToStart[getCountIndex(value, index)] ++;
            }

            // 但凡count > 1的继续sorting
            int subStart = start;
            for (int j : count) {
                if (j != 0) {
                    int subEnd = subStart + j;
                    sortHelperMSD(asciis, subStart, subEnd, index + 1);
                    subStart = subEnd;
                }
            }

        }

    }
}
