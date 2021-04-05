package byog.Core;

import byog.lab5.Position;
import java.util.Random;
import java.util.Stack;

public class ts {
    public static int x = 5;
    public static void main(String[] args) {
        /*String a = "111111111199999911";
        long seed = Long.parseLong(a);
        System.out.println(seed);
        Random RANDOM = new Random(seed);
        for(int i = 0; i < 3; i++) {
            System.out.println(RANDOM.nextInt(2));
        }
        Random RANDOM2 = new Random(seed);
        for(int i = 0; i < 3; i++) {
            System.out.println(RANDOM2.nextInt(2));
        }*/

        Position a = new Position(2,1);
        Position b = a;

        Stack<Position> ina = new Stack<>();
        ina.push(a);

        a = null;

        System.out.println(b.x + " " + b.y);
        System.out.println(ina.peek().x + " " + ina.peek().y);
        System.out.println(b.equals(ina.peek()));

    }
}
