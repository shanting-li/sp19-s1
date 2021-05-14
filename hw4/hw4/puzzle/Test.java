package hw4.puzzle;

import java.util.ArrayList;
import java.util.Stack;

public class Test {
    public static void main(String[] args) {
        /*Stack<Integer> x = new Stack<>();
        x.push(1);
        x.push(2);
        x.push(3);
        x.push(4);
        x.push(5);
        x.push(6);

        for (int s : x) {
            System.out.println(s);
        }*/

        ArrayList<Integer> x = new ArrayList<>();
        x.add(1);
        x.add(2);
        x.add(3);
        x.add(4);
        x.add(5);
        x.add(6);

        /*for (int s : x) {
            System.out.println(s);
        }*/

        //System.out.println(Integer.parseInt("it"));
        String s = "home";
        char[] sArray = new char[s.length()];
        sArray = s.toCharArray();
        int ans = 0;
        for (char a : sArray) {
            ans += a;
            System.out.println(ans);
        }
        System.out.println(ans);

        String a = "home";
        String b = "hole";
        System.out.println(b.compareTo(a)); //-1

        int[][] xx = new int[5][5];
        xx[1][4] = 34;
        System.out.println(xx.length);
        System.out.println(xx[4][1]);
        System.out.println(xx[1][4]);

        Stack<Integer> y = new Stack<>();
        y.push(null);
        System.out.println(y.size());

        String ss = "a";
        String sx = "4pop";
        char c ='v';
        System.out.println(ss.matches("[a-zA-Z]+"));
        System.out.println(sx.matches("[a-zA-Z]+"));
        System.out.println(Character.toString(c).matches("[a-zA-Z]+"));
    }

}
