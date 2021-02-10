package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static Random RANDOM = new Random(2048);;
    /** help method:
     * for certain x, according to certain started y0 point and total number
     * give each (x, yi) a certain tile.(starting from y0 = startY, countY times totally.)
     */
    private static void ySmallSet(int x, int startY, int countY, TETile t, TETile[][] world) {
        int y = startY;
        int count = countY;
        while (!(count == 0)) {
            world[x][y] = t;
            y += 1;
            count -= 1;
        }
    }
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t){
        int leftX = p.x;
        int midXa = p.x + s - 1;
        int midXb = p.x + 2 * s -2;
        int rightX = p.x + 3 * s - 3;

        int startY = p.y;
        int bottomY = p.y - (s - 1);
        int topY = p.y + s;

        int countY = 2;

        for (int x = leftX; x < midXa; x++) {
            HexWorld.ySmallSet(x, startY, countY, t, world);

            countY += 2;
            startY -= 1;
        }

        for (int x = midXa; x < midXb; x++) {
            HexWorld.ySmallSet(x, startY, countY, t, world);
        }


        for (int x = midXb; x <= rightX; x++) {
            HexWorld.ySmallSet(x, startY, countY, t, world);
            countY -= 2;
            startY += 1;
        }

    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.MOUNTAIN;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.WATER;
            case 4: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    public static void yBigSet(int x, int startY, int countY, int s, TETile[][] world) {
        int y = startY;
        int count = countY;
        while (!(count == 0)) {
            Position p = new Position(x, y);
            addHexagon(world, p, s, randomTile());
            y += 2 * s;
            count -= 1;
        }
    }

    public static void bigHex(TETile[][] world, int Width, int Height, int s) {
        int totalY = Height / (s * 2);
        int totalX = (Width - s + 1) / (2 * s - 1);

        int startX = 0;
        int endX = (totalX - 1)  * (2 * s - 1);
        int midX = totalX / 2 * (2 * s - 1);

        int leftTotalY;
        leftTotalY = totalY - totalX / 2;
        int startY = s - 1 + s * (totalY - leftTotalY);

        int countY = leftTotalY;

        for (int c = 0; c < midX; c += 2 * s - 1) {
            yBigSet(c, startY, countY, s, world);
            countY += 1;
            startY -= s;
        }

        for (int c = midX; c <= endX; c += 2 * s - 1) {
            yBigSet(c, startY, countY, s, world);
            countY -= 1;
            startY += s;
        }

    }

    public static void main(String[] args) {
        int WIDTH = 30;
        int HEIGHT = WIDTH;
        int s = 3;

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        /**TETile t =  Tileset.WALL;
        Position p1 = new Position(5,10);
        int s1 = 5;
        Position p2 = new Position(20,10);
        int s2 = 3;
        addHexagon(world, p1, s1, t);
        addHexagon(world, p2, s2, t);*/



        bigHex(world, WIDTH, HEIGHT, s);

        ter.renderFrame(world);
    }
}
