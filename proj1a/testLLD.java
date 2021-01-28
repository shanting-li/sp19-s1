public class testLLD {
    public static LinkedListDeque testAddFirst(){
        LinkedListDeque<Integer> a = new LinkedListDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4);//4,3,2,1,0
        a.printDeque();
        System.out.println("");
        return a;
    }
    public static LinkedListDeque testAddLast(){
        LinkedListDeque<Integer> a = new LinkedListDeque<Integer>();
        a.addLast(0);
        a.addLast(1);
        a.addLast(2);
        a.addLast(3);
        a.addLast(4);
        a.printDeque();
        System.out.println("");
        return a;
    }
    public static void testRemoveFirst(){
        LinkedListDeque<Integer> a = new LinkedListDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4);//4,3,2,1,0
        a.printDeque();
        System.out.println("");
        System.out.println(a.removeFirst());//4
        a.printDeque();
        System.out.println("");
    }

    public static void testRemoveLast(){
        LinkedListDeque<Integer> a = new LinkedListDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4);//4,3,2,1,0
        a.printDeque();
        System.out.println("");
        System.out.println(a.removeLast());//0
        a.printDeque();
        System.out.println("");
    }

    public static void testGet(){
        LinkedListDeque<Integer> a = new LinkedListDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4);//4,3,2,1,0
        a.printDeque();
        System.out.println("");
        System.out.println(a.get(0));//4
        System.out.println(a.get(2));//2
        System.out.println(a.get(4));//0
    }
    public static void testGetR(){
        LinkedListDeque<Integer> a = new LinkedListDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4);//4,3,2,1,0
        a.printDeque();
        System.out.println("");
        System.out.println(a.getRecursive(0));//4
        System.out.println(a.getRecursive(2));//2
        System.out.println(a.getRecursive(4));//0

    }

    public static void main(String[] args){
        testAddFirst();
        testAddLast();
        testRemoveFirst();
        testRemoveLast();
        testGet();
        testGetR();
    }
}
