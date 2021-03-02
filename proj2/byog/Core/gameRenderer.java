package byog.Core;

import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.Serializable;

public class gameRenderer implements Serializable {
    private int width;
    private int height;
    //private TETile[][] world;
    private String text1;
    private String text2;
    private final Font fontTitle = new Font("Arial", Font.BOLD, 30);
    private final Font fontList = new Font("Arial", Font.BOLD, 20);
    private final Font fontTip = new Font("Monaco", Font.BOLD, 16 - 2);

    public void initialize(TETile[][] maze) {
        width = maze.length;
        height = maze[0].length + 2;
        StdDraw.setCanvasSize(width * 16, height * 16);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.enableDoubleBuffering();
    }

    public void renderOver() {
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.setFont(fontTitle);
        String order = "Game is Over and Saved!";
        StdDraw.text(width / 2, height / 2, order);
        StdDraw.show();
    }
    public void renderWin() {
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.setFont(fontTitle);
        String order = "You are win and the Game is saved!";
        StdDraw.text(width / 2, height / 2, order);
        StdDraw.show();
    }
    public void renderSeed() {
        StdDraw.clear(new Color(0, 0, 0));
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.setFont(fontTitle);
        String order = "Please enter your seed, ending with s";
        StdDraw.text(width / 2, height / 4 * 3, order);
        StdDraw.show();
    }
    public void renderSeed(String num) {
        renderSeed();
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.setFont(fontTitle);

        StdDraw.text(width / 2, height / 4 * 3 - 3, num);
        StdDraw.show();
    }

    public void renderUISel(String sel) {
        StdDraw.setPenColor(255, 255, 255);

        int r = 123;
        int g = 104;
        int b = 238;

        renderUI();

        switch (sel) {
            case "n": {
                StdDraw.setPenColor(r, g, b);
                StdDraw.setFont(fontList);
                String N = "New Game (N)";

                StdDraw.text(width / 2, height / 2, N);

                break;
            }
            case "l": {
                StdDraw.setPenColor(r, g, b);
                StdDraw.setFont(fontList);
                String L = "Load Game (L)";
                StdDraw.text(width / 2, height / 2 - 2, L);
                break;
            }
            case "q": {
                StdDraw.setPenColor(r, g, b);
                StdDraw.setFont(fontList);
                String Q = "Quit (Q)";

                StdDraw.text(width / 2, height / 2 - 4, Q);
                break;
            }
        }
        StdDraw.show();
    }
    public void renderUI() {
        StdDraw.setPenColor(255, 255, 255);
        Font fontTitle = new Font("Arial", Font.BOLD, 30);
        Font fontList = new Font("Arial", Font.BOLD, 20);

        StdDraw.setFont(fontTitle);
        String title = "CS61B: THE GAME";
        StdDraw.text(width / 2, height / 4 * 3, title);

        StdDraw.setFont(fontList);
        String N = "New Game (N)";
        String L = "Load Game (L)";
        String Q = "Quit (Q)";

        StdDraw.text(width / 2, height / 2, N);
        StdDraw.text(width / 2, height / 2 - 2, L);
        StdDraw.text(width / 2, height / 2 - 4, Q);

        StdDraw.show();

    }

    public void renderMaze(TETile[][] maze, String a, String b) {
        text1 = a;
        text2 = b;
        StdDraw.setFont(fontTip);
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.clear(new Color(0, 0, 0));

        //render maze: x * y
        int numXTiles = width;
        int numYTiles = height - 2;

        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (maze[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                maze[x][y].draw(x, y);
            }
        }

        //render title: x * (y + 2)
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.textLeft(1, height - 1, text1);
        StdDraw.textRight(width - 1, height - 1, text2);
        StdDraw.line(0, height - 2, width, height - 2);

        StdDraw.show();
    }
}
