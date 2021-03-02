package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

import java.util.Arrays;
import java.util.Stack;
import java.util.Random;

public class TestPhase1 {
    /*public static void testIniWorld() {
        Game test = new Game();
        TETile[][] world = test.initializeWorld();

        Game temp = new Game();
        TETile[][] worldT = temp.initializeWorld();
        TERenderer ter = new TERenderer();
        ter.initialize(temp.WIDTH, temp.HEIGHT);
        ter.renderFrame(world);
        System.out.println("pass");
    }
    public static void testRandomStart() {
        Game test = new Game();
        TETile[][] world = test.initializeWorld();

        Position pt = test.randomStart(1658);
        System.out.println("x1 = " + pt.x);
        System.out.println("y1 = " + pt.y);
        pt = test.randomStart(12357798);
        System.out.println("x2 = " + pt.x);
        System.out.println("y2 = " + pt.y);
        pt = test.randomStart(123798);
        System.out.println("x3 = " + pt.x);
        System.out.println("y3 = " + pt.y);
    }
    public static void testRandomSide() {
        Game temp = new Game();
        TETile[][] worldT = temp.initializeWorld();
        System.out.println(temp.side);

        temp.randomSide();
        System.out.println(temp.side);

        temp.randomSide();
        System.out.println(temp.side);

        temp.randomSide();
        System.out.println(temp.side);

        temp.randomSide();
        System.out.println(temp.side);

        temp.randomSide();
        System.out.println(temp.side);

        temp.randomSide();
        System.out.println(temp.side);

        temp.randomSide();
        System.out.println(temp.side);

        temp.randomSide();
        System.out.println(temp.side);

        temp.randomSide();
        System.out.println(temp.side);

    }
    public static void testSideT() {
        //test pass!
        Game test = new Game();
        TETile[][] world = test.initializeWorld();

        TERenderer ter = new TERenderer();
        ter.initialize(test.WIDTH, test.HEIGHT);

        for (int x = 4; x < 35; x += 1) {
            for (int y = 5; y < 10; y += 1) {
                world[x][y] = Tileset.WALL;
            }
        }

        int YRadius = 6;
        int XRadius = 4;
        Position s1 = new Position(34,8);
        Position s2 = new Position(20,8);
        System.out.println(test.side);
        System.out.println(test.testSide(s1, world, XRadius, YRadius)); // true
        System.out.println(test.testSide(s2, world, XRadius, YRadius)); // false

        test.side = "top";
        Position s3 = new Position(25,9);
        Position s4 = new Position(34,9);
        System.out.println(test.testSide(s3, world, XRadius, YRadius));// true
        System.out.println(test.testSide(s3, world, XRadius, YRadius));// true

        test.side = "bottom";
        Position s5 = new Position(23,5);
        System.out.println(test.testSide(s5, world, XRadius, YRadius));//false

        test.side = "left";
        Position s6 = new Position(4,8);
        System.out.println(test.testSide(s6, world, XRadius, YRadius));//false

        ter.renderFrame(world);
    }
    public static void testPosValid() {
        Game test = new Game();

        Position p1 = new Position(60,30);
        System.out.println(test.PosValid(p1));//false

        Position p2 = new Position(59,29);
        System.out.println(test.PosValid(p2));//true

        Position p3 = new Position(59,18);
        System.out.println(test.PosValid(p3));//true

        Position p4 = new Position(-5,-10);
        System.out.println(test.PosValid(p4));//false

        Position p5 = new Position(0,29);
        System.out.println(test.PosValid(p5));//true

        Position p6 = new Position(3,28);
        System.out.println(test.PosValid(p6));//true

        Position p7 = new Position(-8,0);
        System.out.println(test.PosValid(p7));//false

    }
    public static void testPosSideValid() {
        Game test = new Game();
        TETile[][] world = test.initializeWorld();

        System.out.println("test right side: fttf");
        Position p1 = new Position(74,18);
        Boolean ans = test.PosSideValid(p1, world, "room");
        System.out.println(ans); //false
        p1 = new Position(73,18);
        System.out.println(test.PosSideValid(p1, world, "room")); //true
        p1 = new Position(75,18);
        System.out.println(test.PosSideValid(p1, world, "way")); //true
        p1 = new Position(76,18);
        System.out.println(test.PosSideValid(p1, world, "way")); //false

        test.side = "top";
        System.out.println("test top side: fttf");
        Position p2 = new Position(74,24);
        System.out.println(test.PosSideValid(p2, world, "room")); //false
        p2 = new Position(74,23);
        System.out.println(test.PosSideValid(p2, world, "room")); //true

        Position p3 = new Position(54,25);
        System.out.println(test.PosSideValid(p3, world, "way")); //true
        p3 = new Position(54,26);
        System.out.println(test.PosSideValid(p3, world, "way")); //false



        test.side = "left";
        System.out.println("test left side: ttff");
        Position p4 = new Position(6,24);
        System.out.println(test.PosSideValid(p4, world, "room")); //true

        Position p5 = new Position(4,24);
        System.out.println(test.PosSideValid(p5, world, "way")); //true

        Position p6 = new Position(5,24);
        System.out.println(test.PosSideValid(p6, world, "room")); //false

        Position p7 = new Position(3,24);
        System.out.println(test.PosSideValid(p7, world, "way")); //false



        test.side = "bottom";
        System.out.println("test bottom side: fttf");
        Position p8 = new Position(74,5);
        System.out.println(test.PosSideValid(p8, world, "room")); //false
        p8 = new Position(54,6);
        System.out.println(test.PosSideValid(p8, world, "room")); //true
        p8 = new Position(54,4);
        System.out.println(test.PosSideValid(p8, world, "way")); //true
        p8 = new Position(54,3);
        System.out.println(test.PosSideValid(p8, world, "way")); //false



    }
    public static void testSetRightMost() {
        Position room1 = new Position(1,5);
        Position room2 = new Position(5,17);
        Stack<Position> pos1 = new Stack<>();
        pos1.push(room1);
        pos1.push(room2);

        Position room3 = new Position(3,5);
        Position room4 = new Position(9,27);
        Stack<Position> pos2 = new Stack<>();
        pos2.push(room3);
        pos2.push(room4);

        Game test = new Game();

        test.setRightMost(pos1, "room");
        System.out.println(test.rightMostRoom.peek().x);//5
        test.setRightMost(pos2, "room");
        System.out.println(test.rightMostRoom.peek().x);//9
        test.setRightMost(pos1, "room");
        System.out.println(test.rightMostRoom.peek().x);//9

        System.out.println("room x = " + test.rightMostRoom.peek().x
                + ", room y = " + test.rightMostRoom.peek().y);//x = 9,y = 27

    }

    public static void testSetRoomWay() {
        //test pass!
        Game test = new Game();
        TETile[][] world = test.initializeWorld();
        test.side = "right";
        TERenderer ter = new TERenderer();
        ter.initialize(test.WIDTH, test.HEIGHT);

        Position start0 = new Position(7,3);
        test.swayRecord.push("way");


        Position start1 = test.updateStart(world, start0,Tileset.WALL, Tileset.GRASS);
        Position start2 = test.updateStart(world, start1,Tileset.SAND, Tileset.GRASS);
        Position start3 = test.updateStart(world, start2,Tileset.WALL, Tileset.GRASS);
        Position start4 = test.updateStart(world, start3,Tileset.SAND, Tileset.GRASS);


        ter.renderFrame(world);
    }*/

    public static void testChooseDoor() {
        Game test = new Game();

        TERenderer ter = new TERenderer();
        ter.initialize(test.WIDTH, test.HEIGHT);

        ter.renderFrame(test.playWithInputString("N969001SDDDWWWDDD"));
        ter.renderFrame(test.playWithInputString("N9SDDDWWWDDD"));
        ter.renderFrame(test.playWithInputString("N91001SDDDWWWDDD"));
    }

    public static void testAllString() {
        Game test = new Game();

        TERenderer ter = new TERenderer();
        ter.initialize(test.WIDTH, test.HEIGHT);

        //1.test single time
        /*String input1 = "n123sss";//pass
        input1 ="n18823swwwwwwwwwwddddddddddd";//pass
        input1 ="n9223372036854775806s";//pass
        input1 ="n999378s";//pass
        input1 ="n954378s";//pass
        input1 ="n331s:q";//pass
        //input1 ="n5swwwwwwwwwwwwwwwddddddd:Q";//pass
        String input2 = "L";//pass
        TETile[][] world = test.playWithInputString(input1);
        TETile[][] world2 = test.playWithInputString(input2);
        System.out.println(Arrays.deepEquals(world, world2));*/

        //2.test load 接替
        TETile[][] world = test.playWithInputString("n3415218040718096461ssdsddaddaad");
        //TETile[][] world2 = test.playWithInputString("n3415218040718096461ssdsddaddaad");
        TETile[][] world2 = test.playWithInputString("n3415218040718096461ssdsddaddaad:q");
        //s1, pass
        /*test.playWithInputString("N5001SDDD");
        TETile[][] world2 = test.playWithInputString("N999001SDDD:Q");
        world2 = test.playWithInputString("LWWWDDD");*/

        //s2, pass
        /*TETile[][] world2 = test.playWithInputString("N999SDDD:Q");
        world2 = test.playWithInputString("LWWW:Q");
        world2 = test.playWithInputString("LDDD:Q");*/

        //s3, pass
        /*TETile[][] world2 = test.playWithInputString("N999SDDD:Q");
        world2 = test.playWithInputString("L:Q");
        world2 = test.playWithInputString("L:Q");
        world2 = test.playWithInputString("LWWWDDD");*/

        /*for(int x = 0; x < test.WIDTH; x++) {
            for(int y = 0; y < test.HEIGHT; y++) {
                if(!(world[x][y].equals(world2[x][y]))) {
                    System.out.println("world is: " + world[x][y] + " world2 is: " + world2[x][y]);
                }
            }
        }*/
        boolean a = Arrays.deepEquals(world, world2);
        System.out.println(a);
        ter.renderFrame(world);




        // test save
        /*
        String input1 = "n123sss:q";
        TETile[][] world = test.playWithInputString(input1);
        input1 = "l";
        TETile[][] world1 = test.playWithInputString(input1);
        System.out.println(Arrays.deepEquals(world, world1));//true

        test = new Game();
        String input2 = "n158923sss:q";
        //input2 = "n123sss";//false
        TETile[][] world2 = test.playWithInputString(input2);
        input2 = "l";
        TETile[][] world3 = test.playWithInputString(input2);
        System.out.println(Arrays.deepEquals(world2, world3)); //true

        test = new Game();
        String input4 = "n123sswwdaa";
        TETile[][] world4 = test.playWithInputString(input4);
        input4 = "l:q";
        TETile[][] world5 = test.playWithInputString(input4);
        System.out.println(Arrays.deepEquals(world4, world5));//false

        test = new Game();
        String input5 = "n18823swwwwwwwwwwddddddddddd";
        TETile[][] world6 = test.playWithInputString(input5);
        input5 = "l:q";
        TETile[][] world7 = test.playWithInputString(input5);
        System.out.println(Arrays.deepEquals(world4, world5));//false
        */



    }



    public static void main(String[] args) {
        //testIniWorld();
        //testRandomStart();
        //testRandomSide();
        //testSideT();
        //testPosValid();
        //testPosSideValid();
        //testSetRightMost();
        //testSetRoomWay();
        //testShiftStart();
        testAllString();
        //testGenTip();






















        //switch 语句每个case结束后如果没有break，还会继续走其他的case。case只是用来标注语句开始执行的地方，往后就算不符合的case也会执行
        //test.side = "top";
        //test.side = "bottom";
        //test.side = "left";
        //test.side = "right";
        //Position start0 = new Position(10,15);
        //Position start1 = test.setWay(world, Tileset.SAND, Tileset.GRASS, start0);

        //(int)(math.random) 后面的括号不可以省略，否则全是0
        //int seed;
        //for (int i = 0; i < 5; i++) {
        //    seed = (int) (Math.random() * 4);
        //    System.out.println(seed);
        //}

    }

}
