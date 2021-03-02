package byog.Core;

import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

public class testPhase2 {
    public static Game test = new Game();

    public static void testAll() {
        test.playWithKeyboard();
        //test.playWithInputString("N999001SDDDWWWDDD:q");
    }
    public static void testRender() {
        Game test = new Game();
        TETile[][] world = test.playWithInputString("N990001SDDDWWWDDD");
        gameRenderer ren = new gameRenderer();
        /*ren.initialize(world, "look at me", "you have won");
        ren.renderMaze();*/


        test.initializeWorld();
        ren.initialize(test.world);
        ren.renderUI();
        StdDraw.pause(1000);
        ren.renderUISel("n");
        StdDraw.pause(1000);
        ren.renderSeed();

    }

    public static void main(String[] args) {
        testAll();


        //testRender();

        //test.playWithKeyboard();
        //Game x = new Game();
        //渲染有左上角有文字提示的world
        /*TETile[][] world = test.playWithInputString("N999001SDDDWWWDDD");
        int numXTiles = world.length;
        int numYTiles = world[0].length;

        StdDraw.setCanvasSize(numXTiles * 16, numYTiles * 16);
        Font font = new Font("Monaco", Font.BOLD, 16 - 2);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, numXTiles);
        StdDraw.setYscale(0, numYTiles);
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.enableDoubleBuffering();

        StdDraw.clear(new Color(0, 0, 0));
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x, y);
            }
        }

        StdDraw.setPenColor(255, 255, 255);
        StdDraw.textLeft(1, numYTiles - 1, "look at me");
        StdDraw.textLeft(1, numYTiles - 2, "look at me");
        StdDraw.show();*/
    }

}

