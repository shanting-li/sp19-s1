public class testAD {

    public static ArrayDeque testAddFirst(){
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4);//4,3,2,1,0
        a.printDeque();
        return a;
    }
    public static ArrayDeque testAddLast(){
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        a.addLast(0);
        a.addLast(1);
        a.addLast(2);
        a.addLast(3);
        a.addLast(4);
        a.printDeque();
        return a;
    }
    public static void testRemoveFirst(){
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4);//4,3,2,1,0
        a.printDeque();
        System.out.println("");
        System.out.println(a.removeFirst());//0
        a.printDeque();
    }

    public static void testRemoveLast(){
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        a.addFirst(0);
        a.addFirst(1);
        a.addFirst(2);
        a.addFirst(3);
        a.addFirst(4);//4,3,2,1,0
        a.printDeque();
        System.out.println("");
        System.out.println(a.removeLast());//0
        a.printDeque();
    }

    public static void testGet(){
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
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

    public static void testAll(){
        LinkedListDeque<Integer> a = new LinkedListDeque<Integer>();
        for (int i = 0; i < 30; i ++){
            a.addLast(i);
        }
        a.printDeque();//0,1,...29
        System.out.println();
        System.out.println(a.getRecursive(0));//0
        System.out.println(a.getRecursive(5));//5
        System.out.println(a.getRecursive(29));//29
        System.out.println(a.getRecursive(30));//null
    }
    public static void main(String[] args){
        //testAddFirst();
        //testAddLast();
        //testRemoveFirst();
        //testRemoveLast();
        //testGet();
        testAll();
    }
}

