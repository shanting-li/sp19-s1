package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        //initialize rand by the seed input by the player
        rand = new Random(seed);

        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        //更改默认字体
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        //StdDraw.clear(Color.BLACK);//改完字体颜色后取消注释
        //StdDraw.enableDoubleBuffering();
        //String text = "Levi Ackerman";
        //StdDraw.text(width/2, height/2, text);
        //StdDraw.show();

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String ans = "";
        for(int i = 0; i < n; i++) {
            int dex= (int) (rand.nextInt(26)); //[0, 26)
            ans += CHARACTERS[dex];
        }
        return ans;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        Font font = new Font("Monaco", Font.BOLD, 30);
        Font font2 = new Font("Monaco", Font.PLAIN, 20);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(255, 255, 255);
        StdDraw.enableDoubleBuffering();

        if(s.equals("ENCOURAGEMENT")) {
            StdDraw.setFont(font2);

            int r = (int) (Math.random() * (ENCOURAGEMENT.length));
            String info = ENCOURAGEMENT[r];

            StdDraw.text(width/2, height - 2, info);
            StdDraw.show();

        } else if(!s.equals("Your turn")) {
            // clear the canvas
            StdDraw.setFont(font);
            StdDraw.text(width/2, height/2, s);
            StdDraw.show(1000);

        } else {
            // clear the canvas
            StdDraw.setFont(font2);
            StdDraw.text(width/2, height - 2, "Your turn");
            StdDraw.show();
        }



        //TODO: If game is not over, display relevant game information at the top of the screen



    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for(int i = 0; i < letters.length(); i++) {
            String s = "";
            s += letters.charAt(i);
            drawFrame(s);
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String playString = "";
        String moreString = "";
        int count = 0;

        int pauseTime = 20;
        if(playerTurn){
            while (StdDraw.hasNextKeyTyped()) {
                if (count < n) {
                    playString += StdDraw.nextKeyTyped();
                    drawFrame(playString);
                } else {
                    moreString += StdDraw.nextKeyTyped();
                    drawFrame(playString + moreString);
                }
                StdDraw.pause(pauseTime += 30);
                count ++;
            }

        } else {
            while (StdDraw.hasNextKeyTyped()) {
                StdDraw.nextKeyTyped();
            }
        }
        playerTurn = false;
        return playString;
    }


    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        playerTurn = false;

        //TODO: Establish Game loop
        while(!gameOver) {
            drawFrame("Round: " + round);

            String target = generateRandomString(round);
            flashSequence(target);

            drawFrame("");
            solicitNCharsInput(0);

            drawFrame("Your turn");
            playerTurn = true;
            StdDraw.pause(1500);

            String playString = "";
            while(playerTurn) {
                playString = solicitNCharsInput(round);
            }

            if(target.equals(playString)) {
                round += 1;
                String en = "ENCOURAGEMENT";
                drawFrame(en);
                StdDraw.pause(2000);
            } else {
                gameOver = true;
                String over = "Game Over! You made it to round:";
                drawFrame(over + round);
            }
        }
    }

}
