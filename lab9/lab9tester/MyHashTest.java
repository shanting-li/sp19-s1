package lab9tester;

import lab9.MyHashMap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashTest {
    public static void main(String[] args) {
        MyHashMap<String, Integer> x = new MyHashMap<>();
        x.put("Levi", 1225);
        x.put("Erwin", 1014);
        x.put("Hange", 905);
        x.put("Armin", 1103);
        x.put("Mikasa", 210);
        x.put("Jean", 407);
        x.put("Sasha",726);
        x.put("Falcon",210);
        x.put("Gabi",414);
        x.put("Reiner",700);
        x.put("Reiner",801);
        x.put("Zeke",801);
        x.put("Annie",322);
        x.put("Petra",0);
        x.put("Bertholdt ",1230);
        x.put("Ymir",217);
        x.put("Christa",115);
        x.put("Conny",502);
        x.put("Keith",0);

        System.out.println(x.get("Levi"));
        System.out.println(x.get("Falcon"));
        System.out.println(x.get("Conny"));
        System.out.println(x.size());
        x.put("Erwin", 1014);
        System.out.println(x.size());

        Iterator<String> it = x.iterator();
        for(String i : x) {
            System.out.println(i);
        }

    }
}
