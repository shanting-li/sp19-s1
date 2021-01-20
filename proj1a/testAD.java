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
        ArrayDeque<Integer> a = new ArrayDeque<Integer>();
        for (int i = 0; i < 12; i ++){
            a.addLast(i);
        }
        for (int i = 0; i < 2; i ++){
            a.removeLast();
        }
        a.printDeque();
        /**System.out.println(a.removeFirst());
        System.out.println("size = " + a.size());//0
        for (int i = 0; i < 18; i ++){
            a.addLast(i);
        }
        a.printDeque();//0,1,...17
        System.out.println();
        System.out.println(a.get(0));//0
        System.out.println(a.get(5));//5
        System.out.println(a.get(11));//11
        System.out.println(a.get(30));//null
        a.removeFirst();//0-1,2,3,...
        a.removeFirst();//1-2,3,4,...
        a.removeLast();//17-2,3,4...16
        a.removeFirst();//2-3,4,...,16
        System.out.println("size = " + a.size());//14
        a.printDeque();
        System.out.println();
        a.removeLast();//16-3,4,...15
        a.removeLast();//15-3,4,5,...,14
        a.removeFirst();//3-4,5,...,14
        a.removeFirst();//4-5,6,...,14
        a.printDeque();
        System.out.println();
        System.out.println("size = " + a.size());//10*/

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

