package byog.lab5;

import java.io.Serializable;

public class Position implements Serializable {
    public int x;
    public int y;

    public Position(int a, int b) {
        x = a;
        y = b;
    }
}
