public class testAD {

    public static ArrayDeque testAddFirst() {
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4); //4,3,2,1,0
        System.out.println("size = " + a.size());
        a.printDeque();
        return a;
    }
    public static ArrayDeque testAddLast() {
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        a.addLast(0);
        a.addLast(1);
        a.addLast(2);
        a.addLast(3);
        a.addLast(4);
        System.out.println("size = " + a.size());
        a.printDeque();
        return a;
    }
    public static void testRemoveFirst() {
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4); //4,3,2,1,0
        a.printDeque();
        System.out.println("");
        System.out.println(a.removeFirst()); //4
        a.printDeque();
        System.out.println("");
        System.out.println("size = " + a.size());
    }

    public static void testRemoveLast() {
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4);
        a.printDeque(); //4,3,2,1,0
        System.out.println("");
        System.out.println(a.removeLast()); //0
        a.printDeque(); //4,3,2,1
        System.out.println("");
        System.out.println("size = " + a.size());
    }

    public static void testGet() {
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4);
        a.printDeque(); //4,3,2,1,0
        System.out.println("");
        System.out.println(a.get(0)); //4
        System.out.println(a.get(2)); //2
        System.out.println(a.get(4)); //0
    }

    public static void testAll() {
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        for (int i = 0; i < 20; i ++) {
            a.addLast(i);
            //a.addFirst(i);
        }
        a.printDeque(); //0,1,2,...,12,13,...,19
        System.out.println("");

        for (int i = 0; i < 17; i ++) {
            a.removeFirst();
        }
        a.printDeque();//17,18,19
        System.out.println("");
        System.out.println(a.removeFirst());//17
        System.out.println("size = " + a.size());//2
        for (int i = 0; i < 6; i ++){
            a.addLast(i+20);
        }
        a.printDeque();//18,19,20,21,22,23,24,25
        System.out.println();
        System.out.println(a.get(0));//18
        System.out.println(a.get(5));//23
        System.out.println(a.get(7));//25
        System.out.println(a.get(8));//null
        a.removeFirst();//18
        a.removeFirst();//19
        a.removeLast();//25
        a.removeFirst();//20
        System.out.println("size = " + a.size());//4
        a.printDeque(); //21,22,23,24
        System.out.println();
        a.removeLast();//24
        a.removeLast();//23
        a.removeFirst();//21
        a.printDeque();//22
        System.out.println();
        System.out.println("size = " + a.size());//1

    }
    public static void main(String[] args) {
        //testAddFirst();
        //testAddLast();
        //testRemoveFirst();
        //testRemoveLast();
        //testGet();
        testAll();
    }
}

