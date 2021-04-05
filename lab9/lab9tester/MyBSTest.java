package lab9tester;
import lab9.BSTMap;
import lab9.Map61B;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

public class MyBSTest {
    public static void main(String[] args) {
        BSTMap<Integer, String> x = new BSTMap<>();
        x.put(3, "Levi");
        x.put(1, "Erwin");
        x.put(0, "Ymir");
        x.put(2, "Jean");
        x.put(5, "Sasha");
        x.put(4, "Hange");
        x.put(10, "SitDown");
        x.put(10, "Reiner");
        x.put(7, "Mikasa");
        x.put(11, "Pixis");
        x.put(6, "Ani");
        x.put(9, "Bertholdt");
        x.put(8, "Armin");

        System.out.println(x.get(3));//Levi
        System.out.println(x.get(5));//Sasha
        System.out.println(x.get(2));//Jean
        System.out.println(x.get(7));//Mikasa

        //test set
        /*Set<Integer> keys =  x.keySet();
        for(Integer i : keys) {
            System.out.println(i);//0-11
        }*/

        System.out.println("size = " + x.size());//12
        Iterator<Integer> i = x.iterator();
        while(i.hasNext()) {
            System.out.println(i.next());
        }


        /*test remove*/
        /*System.out.println(x.remove(10));//Reiner
        System.out.println(x.searchNode(9, x.root).left.right.value);//Armin
        System.out.println("size = " + x.size());//11

        System.out.println(x.remove(3, "Ymir"));//null
        System.out.println("size = " + x.size());//11
        System.out.println(x.remove(0, "Ymir"));//Ymir
        System.out.println("size = " + x.size());//10
        System.out.println(x.searchNode(1, x.root).left);//null*/
    }

}
