package byog.Core;

import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.lab5.Position;

import java.awt.*;
import java.util.Stack;
import java.util.Random;

import java.io.*;

public class Game implements Serializable{
    private static final long serialVersionUID = 1L;

    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    //the scope of hallway and room
    private static final int wayMax = 6;
    private static final int wayMin = 2;
    private static final int roomMax = 8;
    private static final int roomMin = 4;


    // the top element is the world saved last time
    private Stack<TETile[][]> savedFiles = new Stack<>();

    //the top element is the point where the player finally get saved last time
    private Stack<Position> savedEnd = new Stack<>();

    //the top element is the original start point of the maze saved last time
    private Stack<Position> savedNewStart = new Stack<>();

    //the top element is the win point of the maze saved last time
    private Stack<Position> savedLockedDoor = new Stack<>();

    //the top element is the win situation of the maze saved last time
    private Stack<Boolean> savedWin = new Stack<>();

    //the world of a new game
    public TETile[][] world;
    //the start point of a new game
    private Position newStart;
    //the win point of a new game
    private Position lockedDoor;
    private boolean IsWin = false;
    private boolean playerTurn = false;
    private Random RANDOM;
    private final String[] rightTip = {"Keep moving!", "You are win!"};
    private boolean gameOver = false;

    //when drawing a room, one need to choose a side of it randomly
    private String side = "right";

    //keep track of position of the rightmost room
    private Stack<Position> rightMostRoom;

    // keep track of the last shape drawn, a room or a hallway.
    private Stack<String> swayRecord = new Stack<>();



    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */


    /** PART A AID FUNCTIONS
     * */
    /**
     * 1. initializeWorld:
     * initialize rightMostRoom and rightMostWay
     * create world, give it NOTHING tiles, return world[][]
     *
     * 2. randomStart:
     * return a random start point using the player's seed
     *
     * 3. randomSide:
     * choose a side randomly
     *
     * 4. setRightMost:
     * update the rightmost point in the world:
     * if the input.x > rightmost.x
     * make a shallow copy of input and give it to rightmost
     * do not change the input at end
     *
     * 5. shallowCopyRightMost
     * if the diapoint of a room equals rightmost
     * make a shallow copy of the rightmost and give it to the diapoint
     * input/rightMost: (place, lb, rt)
     * output/diapoint: copy(lb', rt')
     * do not change the rightmost at end
     *
     * 6. initializeRightMost
     * give rightmost stack a place position and two positions for creating a room.
     * the function of the place position is to ensure the rightmost will not become empty
     * due to pop()
     */
    public void initializeWorld() {
        world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    private Position randomStart() {
        Position ans = new Position(0, 0);
        ans.x = 2 + RANDOM.nextInt(WIDTH / 15);
        ans.y = 2 + RANDOM.nextInt(HEIGHT / 15);
        //System.out.println(ans.x + " " + ans.y);
        return ans;
    }

    private void randomSide() {
        //if seed is the same, the result is the same
        int seedSide = RANDOM.nextInt(4);
        // total random
        //int seedSide = (int) (Math.random() * 4);

        switch (seedSide) {
            case 0:
                side = "top";
                break;
            case 1:
                side = "bottom";
                break;
            case 2:
                side = "left";
                break;
            case 3:
                side = "right";
                break;
        }
    }

    private void setRightMost(Stack<Position> pos) {
        //rightMostRoom(place', lb', rt')
        //pos(lb,rt)
        if (pos.peek().x > rightMostRoom.peek().x) {
            Position p1 = pos.pop();
            Position p2 = pos.pop();
            Position p3 = new Position(p1.x, p1.y);
            Position p4 = new Position(p2.x, p2.y);
            rightMostRoom = new Stack<>();
            /*rightMostRoom.push(new Position(-1, -1));*/
            rightMostRoom.push(p4);
            rightMostRoom.push(p3);
            pos.push(p2);
            pos.push(p1);
            //System.out.println(rightMostRoom.peek().x);
        }

    }
    private Stack<Position> shallowCopyRightMost(Stack<Position> origin) {
        Stack<Position> copy = new Stack<>();
        Position p1 = origin.pop();
        Position p2 = origin.pop();
        Position p3 = new Position(p1.x, p1.y);
        Position p4 = new Position(p2.x, p2.y);
        copy.push(p4);
        copy.push(p3);
        origin.push(p2);
        origin.push(p1);

        return copy;
    }
    private void initializeRightMost() {
        rightMostRoom = new Stack<>();

        //Hold the space in case rightMostRoom becomes empty due to pop()
        /*Position place = new Position(-1, -1);
        rightMostRoom.push(place);*/

        Position lb = new Position(2, 2);
        Position rt = new Position(5, 5);

        rightMostRoom.push(lb);
        rightMostRoom.push(rt);
    }


    /** PART B DRAW A ROOM
     * 1.draw a room based on the start point (setRoom);
     * 2.find the next start point on the room (SetPtOnRoom);
     * */
    /**
     * 1.setRoom:
     * draw rooms and return the diagonal points of the created room.
     * <p>
     * 1). use the last start point to get a set of valid diagonal points of a new room,
     * ensuring its validity and setting rightMost by setRoomPos
     * 2). run over the room, give the wall and floor
     * 3). set the swayRecord to "room"
     * 4). return the diagonal points (stack)
     */

    private Stack<Position> setRoom(TETile wallTile, TETile flTile, Position modStart) {
        /* 1. use the point given by last hallway to get the leftbottom and righttop points,
         * ensuring its validity and setting rightMost by setRoomPos */
        Stack<Position> pos = setRoomPos(modStart);
        // set rightMost
        setRightMost(pos);

        Position rightTop = pos.pop();
        Position leftBottom = pos.pop();
        int startX = leftBottom.x;
        int endX = rightTop.x;
        int startY = leftBottom.y;
        int endY = rightTop.y;
        int roomWidth = endX - startX;
        int roomHeight = endY - startY;

        /* 2.run over the room, give the wall and floor
         *  if already has tiles, change the tile to floor
         *  */
        if (!(world[modStart.x][modStart.y].equals(Tileset.NOTHING))) {
            world[modStart.x][modStart.y] = flTile;
        }
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                if (x == modStart.x && y == modStart.y
                        && world[modStart.x][modStart.y].equals(flTile)) {
                    continue;
                }
                if (x == startX || x == endX - 1 || y == startY || y == endY - 1) {
                    world[x][y] = wallTile;
                } else {
                    world[x][y] = flTile;
                }
            }
        }

        /* 3. set the swayRecord to "room"
         *  if it is the first time to draw anything,
         *  make the middle point of the room's as the newStart */
        if (swayRecord.peek().equals("")) {
            int midX = (startX + endX) / 2;
            newStart = new Position(midX, startY);
        }
        swayRecord.push("room");


        /* 4.return the position */
        pos.push(leftBottom);
        pos.push(rightTop);
        return pos;
    }

    /**
     * setRoomPos:
     * 1.create and ensure the diagonal positions are valid:
     * 1.in world's scope (PosValid)
     * 2.no tiles in its scope (trimValid)
     * 3.valid size as a room (trimValid)
     * 2.update rightMost
     * 3.return a valid set of diagonal points of a new room
     */
    private Stack<Position> setRoomPos(Position modStart) {
        // create and ensure the diagonal positions are valid:
        // 1.in world's scope (PosValid)
        // 2.no tiles in its scope (trimValid)
        // 3.valid size as a room (trimValid)

        Stack<Position> pos = new Stack<>();
        Position leftBottom = new Position(-1, -1);
        Position rightTop = new Position(-1, -1);
        pos.push(leftBottom);
        pos.push(rightTop);

        while (!(PosValid(leftBottom) && PosValid(rightTop) &&
                trimValid(leftBottom, rightTop, modStart))) {
            pos = helpSetRoomPos(modStart);
            rightTop = pos.pop();
            leftBottom = pos.pop();
        }

        pos.push(leftBottom);
        pos.push(rightTop);

        return pos;
    }

    /**
     * setRoomPos's help methods
     * helpSetRoomPos :
     * generate a new position using a given start point
     * posValid:
     * check whether the position is out of the world's scope
     * trimValid (use trimRoomPos):
     * trim the room if there is any tiles in it;
     * return true if the size of trimed room is valid; return false if not
     * trimRoomPos (use trimRightX + trimLeftX + trimBtY + trimTopY):
     * trim the room if there is any tiles in it;
     */
    private Stack<Position> helpSetRoomPos(Position modStart) {
        Stack<Position> ans = new Stack<>();

        // false random (same seed, same number)
        int roomWidth = 4 + RANDOM.nextInt(roomMax - 4 + 1);//4-8
        int roomHeight = 4 + RANDOM.nextInt(roomMax - 4 + 1);//4-8

        // total random
        //int roomWidth = 4 + (int) (Math.random() * (roomMax - 4 + 1));//4-8
        //int roomHeight = 4 + (int) (Math.random() * (roomMax - 4 + 1));

        int leftBound = modStart.x - roomWidth + 2;
        int btBound = modStart.y - roomHeight + 2;

        int startX;
        int startY;
        int endX;
        int endY;

        switch (side) {
            //新房间的左右边界被限定
            case "top":
                //false random
                startX = leftBound + RANDOM.nextInt(roomWidth - 2);
                //total random
                //startX = leftBound + (int) (Math.random() * (roomWidth - 2));
                startY = modStart.y;
                break;
            case "bottom":
                //false random
                startX = leftBound + RANDOM.nextInt(roomWidth - 2);
                //total random
                //startX = leftBound + (int) (Math.random() * (roomWidth - 2));
                startY = modStart.y - roomHeight + 1;
                break;
            case "right":
                //false random
                startY = btBound + RANDOM.nextInt(roomHeight - 2);
                //total random
                //startY = btBound + (int) (Math.random() * (roomHeight - 2));
                startX = modStart.x;
                break;
            default: //left
                //false random
                startY = btBound + RANDOM.nextInt(roomHeight - 2);
                //total random
                //startY = btBound + (int) (Math.random() * (roomHeight - 2));
                startX = modStart.x - roomWidth + 1;
                break;
        }
        endX = startX + roomWidth;
        endY = startY + roomHeight;


        Position leftBottom = new Position(startX, startY);
        Position rightTop = new Position(endX, endY);
        ans.push(leftBottom);
        ans.push(rightTop);
        return ans;
    }

    private Boolean PosValid(Position p) {
        boolean ans = true;

        if (p.x < 0 || p.x > WIDTH - 1 || p.y < 0 || p.y > HEIGHT - 1) {
            ans = false;
        }
        return ans;
    }

    private boolean trimValid(Position leftBottom, Position rightTop,
                              Position modStart) {
        boolean ans = true;

        int leftX = leftBottom.x;
        int rightX = rightTop.x;
        int btY = leftBottom.y;
        int topY = rightTop.y;
        Stack<Integer> p = trimRoomPos(leftX, rightX, btY, topY, modStart);
        leftBottom.x = p.pop();
        rightTop.x = p.pop();
        leftBottom.y = p.pop();
        rightTop.y = p.pop();

        if (rightTop.x - leftBottom.x < roomMin || rightTop.y - leftBottom.y < roomMin) {
            ans = false;
        }
        return ans;
    }

    private Stack<Integer> trimRoomPos(int leftX, int rightX, int btY, int topY,
                                       Position modStart) {

        Stack<Integer> ans = new Stack<>();

        int leftX1 = leftX;
        int rightX1 = rightX;
        int btY1 = btY;
        int topY1 = topY;

        switch (side) {
            case "right":
                // leftX does not change
                // cut rightX
                rightX = trimRightX(leftX, rightX, btY, topY, modStart);
                //cut btY
                btY = trimBtY(leftX, rightX, btY, topY, modStart);
                //cut topY
                topY = trimTopY(leftX, rightX, btY, topY, modStart);
                break;

            case "left":
                // rightX does not change

                // cut leftX
                leftX = trimLeftX(leftX, rightX, btY, topY, modStart);
                //cut btY
                btY = trimBtY(leftX, rightX, btY, topY, modStart);
                //cut topY
                topY = trimTopY(leftX, rightX, btY, topY, modStart);
                break;

            case "top":
                // btY does not change
                // cut topY
                topY = trimTopY(leftX, rightX, btY, topY, modStart);
                //cut leftX
                leftX = trimLeftX(leftX, rightX, btY, topY, modStart);
                //cut rightX
                rightX = trimRightX(leftX, rightX, btY, topY, modStart);
                break;

            case "bottom":
                // topY does not change

                // cut btY
                btY = trimBtY(leftX, rightX, btY, topY, modStart);
                //cut leftX
                leftX = trimLeftX(leftX, rightX, btY, topY, modStart);
                //cut rightX
                rightX = trimRightX(leftX, rightX, btY, topY, modStart);
                break;

        }

        ans.push(topY);
        ans.push(btY);
        ans.push(rightX);
        ans.push(leftX);
        return ans;

    }

    private int trimRightX(int leftX, int rightX, int btY, int topY,
                           Position modStart) {
        int rightX1 = rightX;
        boolean hasTile;
        boolean notStart;

        for (int i = btY; i < topY; i++) {
            for (int k = leftX; k < rightX; k++) {

                hasTile = !(world[k][i].equals(Tileset.NOTHING));

                if (side.equals("right") || side.equals("left")) {
                    notStart = !((k == modStart.x) && (modStart.y - 1 <= i) && (i <= modStart.y + 1));
                } else {
                    notStart = !((i == modStart.y) && (modStart.x - 1 <= k) && (k <= modStart.x + 1));
                }

                if (hasTile && notStart) {
                    if (k < rightX1) {
                        rightX1 = k;
                        //break;
                    }
                }
            }
        }
        return rightX1;
    }

    private int trimLeftX(int leftX, int rightX, int btY, int topY,
                          Position modStart) {
        int leftX1 = leftX;
        boolean hasTile;
        boolean notStart;

        for (int i = btY; i < topY; i++) {
            for (int k = leftX; k < rightX; k++) {

                hasTile = !(world[k][i].equals(Tileset.NOTHING));

                if (side.equals("right") || side.equals("left")) {
                    notStart = !((k == modStart.x) && (modStart.y - 1 <= i) && (i <= modStart.y + 1));
                } else {
                    notStart = !((i == modStart.y) && (modStart.x - 1 <= k) && (k <= modStart.x + 1));
                }

                if (hasTile && notStart) {
                    if (k > leftX1) {
                        leftX1 = k + 1;
                    }
                }
            }
        }
        return leftX1;
    }

    private int trimBtY(int leftX, int rightX, int btY, int topY,
                        Position modStart) {
        int btY1 = btY;
        boolean hasTile;
        boolean notStart;

        for (int i = leftX; i < rightX; i++) {
            for (int k = btY; k < topY; k++) {

                hasTile = !(world[i][k].equals(Tileset.NOTHING));

                if (side.equals("right") || side.equals("left")) {
                    notStart = !((i == modStart.x) && (modStart.y - 1 <= k) && (k <= modStart.y + 1));
                } else {
                    notStart = !((k == modStart.y) && (modStart.x - 1 <= i) && (i <= modStart.x + 1));
                }

                if (hasTile && notStart) {
                    if (k > btY1) {
                        btY1 = k + 1;
                    }
                }
            }
        }
        return btY1;
    }

    private int trimTopY(int leftX, int rightX, int btY, int topY,
                         Position modStart) {
        int topY1 = topY;
        boolean hasTile;
        boolean notStart;

        for (int i = leftX; i < rightX; i++) {
            for (int k = btY; k < topY; k++) {

                hasTile = !(world[i][k].equals(Tileset.NOTHING));

                if (side.equals("right") || side.equals("left")) {
                    notStart = !((i == modStart.x) && (modStart.y - 1 <= k) && (k <= modStart.y + 1));
                } else {
                    notStart = !((k == modStart.y) && (modStart.x - 1 <= i) && (i <= modStart.x + 1));
                }

                if (hasTile && notStart) {
                    if (k < topY1) {
                        topY1 = k;
                        //break;
                    }
                }
            }
        }
        return topY1;
    }


    /**
     * 2. SetPtOnRoom: find the next start point on the room
     * 1) find a random start point on a randomly chosen side of the room (helpSetPtOnRoom)
     * 2) test its validity (PosSideValid + testSide); if false, find another point and test again
     * 3) if such a valid point seems unavailable, throw an exception.
     */

    private Position SetPtOnRoom(Stack<Position> diaPoint) throws SpaceException {
        // 1.special case:
        // if the diaPoint equals rightmost, shallow copy it
        if (diaPoint.equals(rightMostRoom)) {
            diaPoint = shallowCopyRightMost(diaPoint);
        }
        // 2. create a point and ensure its validity
        // if such a point is unavailable, throw an exception

        Position rightTop = diaPoint.pop();
        Position leftBottom = diaPoint.pop();
        int startX = leftBottom.x;
        int endX = rightTop.x;
        int startY = leftBottom.y;
        int endY = rightTop.y;

        Position ans = new Position(-1, -1);
        String target = "room";

        int count = 0;
        while (!(PosSideValid(ans, target))) {
            if (count < 10) {
                ans = helpSetPtOnRoom(startX, endX, startY, endY);
                count++;
            } else {
                throw new SpaceException("maybe no more space");
            }
        }

        return ans;
    }

    private Position helpSetPtOnRoom(int startX, int endX, int startY, int endY) {
        int roomWidth = endX - startX;
        int roomHeight = endY - startY;

        // update side
        randomSide();

        // choose a point on the chosen side randomly,
        // ensuring that the value on the random dimension does not include the boundary,
        // considering connectivity.
        Position ans = new Position(0, 0);
        switch (side) {
            case "top":
                ans.y = endY - 1;
                //the value range of X does not include the boundary
                // false random
                ans.x = (startX + 1) + RANDOM.nextInt(roomWidth - 2);
                // total random
                //ans.x = (startX + 1) + (int) (Math.random() * (roomWidth - 2));
                break;
            case "bottom":
                ans.y = startY;
                // false random
                ans.x = (startX + 1) + RANDOM.nextInt(roomWidth - 2);
                // total random
                //ans.x = (startX + 1) + (int) (Math.random() * (roomWidth - 2));
                break;
            case "left":
                ans.x = startX;
                // false random
                ans.y = (startY + 1) + RANDOM.nextInt(roomHeight - 2);
                // total random
                //ans.y = (startY + 1) + (int) (Math.random() * (roomHeight - 2));
                break;
            case "right":
                ans.x = endX - 1;
                // false random
                ans.y = (startY + 1) + RANDOM.nextInt(roomHeight - 2);
                // total random
                //ans.y = (startY + 1) + (int) (Math.random() * (roomHeight - 2));
                break;
        }
        return ans;
    }

    private Boolean PosSideValid(Position p, String target) {
        int YRadius;
        int XRadius;

        if (target.equals("room")) {
            YRadius = wayMin + roomMin;
        } else {
            YRadius = roomMin;
        }
        XRadius = roomMin;

        if (testSide(p, XRadius, YRadius)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean testSide(Position p, int shorter, int longer) {
        boolean ans = true;
        //Stack<String> delSide = new Stack<>();
        int startX;
        int startY;
        int endX;
        int endY;


        if (side.equals("top")) {
            startX = p.x - shorter / 2;
            endX = startX + shorter;
            startY = p.y + 1;
            endY = startY + longer;

            if (startX < 0 || startY > HEIGHT || endX > WIDTH || endY > HEIGHT) {
                ans = false;
            } else {
                for (int i = startX; i < endX; i++) {
                    for (int k = startY; k < endY; k++) {
                        if (world[i][k].equals(Tileset.NOTHING)) {
                            continue;
                        } else {
                            ans = false;
                            break;
                        }
                    }
                }
            }

        } else if (side.equals("bottom")) {
            startX = p.x - shorter / 2;
            endX = startX + shorter;
            endY = p.y;
            startY = endY - longer;

            if (startX < 0 || endX > WIDTH || startY < 0) {
                ans = false;
            } else {
                for (int i = startX; i < endX; i++) {
                    for (int k = startY; k < endY; k++) {
                        if (world[i][k].equals(Tileset.NOTHING)) {
                            continue;
                        } else {
                            ans = false;
                            break;
                        }
                    }
                }
            }

        } else if (side.equals("left")) {
            startY = p.y - shorter / 2;
            endY = startY + shorter;
            endX = p.x;
            startX = endX - longer;

            if (startY < 0 || endY > HEIGHT || startX < 0) {
                ans = false;
            } else {
                for (int i = startX; i < endX; i++) {
                    for (int k = startY; k < endY; k++) {
                        if (world[i][k].equals(Tileset.NOTHING)) {
                            continue;
                        } else {
                            ans = false;
                            break;
                        }
                    }
                }
            }

        } else {
            startY = p.y - shorter / 2;
            endY = startY + shorter;
            startX = p.x + 1;
            endX = startX + longer;

            if (startX > WIDTH - 1 || startY < 0 || endY > HEIGHT || endX > WIDTH) {
                ans = false;
            } else {
                for (int i = startX; i < endX; i++) {
                    for (int k = startY; k < endY; k++) {
                        if (world[i][k].equals(Tileset.NOTHING)) {
                            continue;
                        } else {
                            ans = false;
                            break;
                        }
                    }
                }
            }

        }
        return ans;

    }


    /** PART C DRAW A HALLWAY
     * 1.draw a hallway based on the start point (setWay);
     * 2.find the next start point on the hallway (SetPtOnWay);
     * */

    /**
     * 1.setWay:
     * draw hallways and return the diagonal points of the created hallway.
     * <p>
     * 1). use the last start point to get a set of valid diagonal points of a new hallway,
     * ensuring its validity by setRoomPos + helpSetRoomPos
     * 2).run over the hallway, give the wall and floor
     * 3). set the swayRecord to "way"
     * 4). return the diagonal points (stack)
     */
    private Stack<Position> setWay(TETile wallTile, TETile flTile, Position modStart) {
        /* 1. use the point given by last room to get the leftbottom and righttop points,
         * ensuring its validity (setWayPos + helpSetWayPos)*/
        Position ans = new Position(0, 0);
        Stack<Position> pos = setWayPos(modStart);

        Position rightTop = pos.pop();
        Position leftBottom = pos.pop();
        int leftX = leftBottom.x;
        int rightX = rightTop.x;
        int btY = leftBottom.y;
        int topY = rightTop.y;

        /* 2.run over the hallway, give the wall and floor */
        if (!(world[modStart.x][modStart.y].equals(Tileset.NOTHING))) {
            world[modStart.x][modStart.y] = flTile;
        }

        for (int x = leftX; x < rightX; x++) {
            for (int y = btY; y < topY; y++) {
                if (x == modStart.x && y == modStart.y
                        && world[modStart.x][modStart.y].equals(flTile)) {
                    continue;
                }
                if (x == leftX || x == rightX - 1 || y == btY || y == topY - 1) {
                    world[x][y] = wallTile;
                } else {
                    world[x][y] = flTile;
                }
            }
        }


        /* 3. set the swayRecord to "way"*/
        swayRecord.push("way");

        pos.push(leftBottom);
        pos.push(rightTop);
        return pos;

    }

    private Stack<Position> setWayPos(Position modStart) {
        Stack<Position> pos = new Stack<>();
        Position leftBottom = new Position(-1, -1);
        Position rightTop = new Position(-1, -1);
        pos.push(leftBottom);
        pos.push(rightTop);

        while (!(PosValid(leftBottom) && PosValid(rightTop))) {
            pos = helpSetWayPos(modStart);
            rightTop = pos.pop();
            leftBottom = pos.pop();
        }

        pos.push(leftBottom);
        pos.push(rightTop);

        return pos;
    }

    private Stack<Position> helpSetWayPos(Position modStart) {
        Stack<Position> ans = new Stack();

        // false random
        int wayLength = wayMin + RANDOM.nextInt(wayMax - wayMin + 1);//2+8=10
        // total random
        //int wayLength = wayMin + (int) (Math.random() * (wayMax - wayMin + 1));//2+8=10
        int btY = modStart.y;
        int topY = modStart.y;
        int leftX = modStart.x;
        int rightX = modStart.x;

        switch (side) {
            case "top":
                topY += wayLength;
                leftX -= 1;
                rightX += 2;
                break;
            case "bottom":
                btY = modStart.y - wayLength + 1;
                topY += 1;
                leftX -= 1;
                rightX += 2;
                break;
            case "left":
                leftX = modStart.x - wayLength + 1;
                rightX += 1;
                btY -= 1;
                topY += 2;
                break;
            case "right":
                rightX += wayLength;
                btY -= 1;
                topY += 2;
                break;
        }

        Position leftBottom = new Position(leftX, btY);
        Position rightTop = new Position(rightX, topY);
        ans.push(leftBottom);
        ans.push(rightTop);

        return ans;
    }


    /**
     * 2. SetPtOnWay:
     * find the next start point on the the hallway
     */
    private Position SetPtOnWay(Stack<Position> diaPoint, Position modStart)
            throws SpaceException {
        // 1.special case:
        // if the diaPoint equals rightmost, shallow copy it
        if (diaPoint.equals(rightMostRoom)) {
            diaPoint = shallowCopyRightMost(diaPoint);
        }

        // 2. create a point and ensure its validity
        // if such a point is unavailable, throw an exception
        Position rightTop = diaPoint.pop();
        Position leftBottom = diaPoint.pop();
        int leftX = leftBottom.x;
        int rightX = rightTop.x;
        int btY = leftBottom.y;
        int topY = rightTop.y;

        Position ans = new Position(-1, -1);
        String target = "way";

        int count = 0;
        while (!(PosSideValid(ans, target))) {
            if (count < 10) {
                ans = helpSetPtOnWay(leftX, rightX, btY, topY, modStart);
                count++;
            } else {
                throw new SpaceException("maybe no more space");
            }
        }

        return ans;
    }

    private Position helpSetPtOnWay(int leftX, int rightX, int btY, int topY, Position modStart) {
        Position ans = new Position(0, 0);
        switch (side) {
            case "top":
                ans.x = modStart.x;
                ans.y = topY - 1;
                break;
            case "bottom":
                ans.x = modStart.x;
                ans.y = btY;
                break;
            case "right":
                ans.y = modStart.y;
                ans.x = rightX - 1;
                break;
            case "left":
                ans.y = modStart.y;
                ans.x = leftX;
                break;
        }
        return ans;
    }


    /** Part D DRAW A WORLD (setWorld);
     * */

    /**
     * setWorld
     * 1. if the start point is close enough to the edge, return
     * 2. if not, update the start point (updateStart), then setWorld again
     */
    private void setWorld(Position start, TETile wallTile, TETile flTile) {
        if (start.x > WIDTH - 1) {
            return;
        } else {
            start = updateStart(start, wallTile, flTile);
            //System.out.println(start.x);
            setWorld(start, wallTile, flTile);
        }
    }

    /**
     * updateStart
     * 1. if the last thing the game draws is a hallway,
     * use the start point to draw a room
     * 2. update the start point
     * try to choose a new random point on the room/way drawn just now
     * <p>
     * if it gets into an infinite loop
     * choose a new random point on the rightmost room/way
     */
    private Position updateStart(Position start, TETile wallTile, TETile flTile) {
        Position ans = new Position(WIDTH, HEIGHT);

        if (swayRecord.peek().equals("room")) {
            // draw a hallway
            Stack<Position> diaPoint = setWay(wallTile, flTile, start);
            //System.out.println(diaPoint.peek().x);

            //try to choose a new random point on the way drawn just now
            //if get an infinite loop, choose a new random point on the rightmost way
            try {
                ans = SetPtOnWay(diaPoint, start);
            } catch (SpaceException s) {
                try {
                    swayRecord.push("room");
                    ans = SetPtOnRoom(rightMostRoom);
                } catch (SpaceException p) {
                    return ans;
                }
            }
        } else {
            // draw a room
            Stack<Position> diaPoint = setRoom(wallTile, flTile, start);
            //System.out.println(diaPoint.peek().x);

            //try to choose a new random point on the room drawn just now
            //if get an infinite loop, choose a new random point on the rightmost room
            try {
                ans = SetPtOnRoom(diaPoint);
            } catch (SpaceException s) {
                try {
                    ans = SetPtOnRoom(rightMostRoom);
                } catch (SpaceException p) {
                    return ans;
                }

            }
        }
        return ans;
    }


    /**
     * PART E playWithInputString
     * 1. deal with the input, if starting with n, get the seed and the start point
     * if starting with l, load the last game
     * 2. deal with the rest input,
     *    get the moving path and the end point.(checkInMaze + inputAfterSeed)
     *    save the file if required (checkInMaze + inputAfterSeed)
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        /* 1. deal with the input,
           if starting with n, get the seed and the start point
           then draw a new world
         * if starting with l, load the last game */

        //1 initialize swayRecord, finalWorldFrame and rightmost
        swayRecord.push("");
        TETile[][] finalWorldFrame;
        initializeRightMost();

        //2 deal with the input

        input = input.toLowerCase();
        String seed = "";
        int moveIndex = 0;
        long seedNum = 0;
        Position moveStart;

        //2.1 if n, get the seed and create new world
        if (input.charAt(0) == 'n') {
            // get the seed
            for (int i = 1; i < input.length(); i++) {
                char item = input.charAt(i);
                if (Character.isDigit(item)) {
                    seed += item;
                } else if (item == 's') {
                    moveIndex = i + 1;
                    break;
                }
            }
            // initialize world, seed and random array
            initializeWorld();
            seedNum = Long.parseLong(seed);
            //System.out.println(seedNum);
            RANDOM = new Random(seedNum);

            // create world, the start point and win point of the maze
            setWorld(randomStart(), Tileset.WALL, Tileset.GRASS);
            chooseDoor();

            // move the point according to the rest words, saving the maze if necessary
            moveStart = new Position(newStart.x, newStart.y);
            world[moveStart.x][moveStart.y] = Tileset.FLOWER; // flower represents the player
            inputAfterSeed(input, moveIndex, moveStart);

        } else if (input.charAt(0) == 'l' && (!savedFiles.isEmpty())
                && (!savedEnd.isEmpty())) {
            // 1 get the information of the last maze
            // including the world,
            // the point the player get to,
            // the start point and win point of the maze,
            // and whether the player has already won.
            try {
                DeServializePerson();
            } catch (IOException e) {
                e.printStackTrace();
            }
            moveStart = savedEnd.peek();
            world = savedFiles.peek();
            newStart = savedNewStart.peek();
            lockedDoor = savedLockedDoor.peek();
            IsWin = savedWin.peek();

            // 2. move the point and save files according to the rest words
            moveIndex = 1;
            inputAfterSeed(input, moveIndex, moveStart);

        } else {
            initializeWorld();
        }

        finalWorldFrame = world;
        clearStringGame();

        return finalWorldFrame;
    }


    private void chooseDoor() {
        lockedDoor = new Position(WIDTH - 1, HEIGHT - 1);

        int count = 0;
        boolean loopFailed = false;

        while(!(PosValid(lockedDoor)
                && (world[lockedDoor.x][lockedDoor.y].equals(Tileset.WALL))
                && (!isCorner(lockedDoor)))) {
            lockedDoor.x = RANDOM.nextInt(WIDTH);
            lockedDoor.y = RANDOM.nextInt(HEIGHT);
            count ++;
            if(count > 1000) {
                loopFailed = true;
                break;
            }
        }
        if(loopFailed) {
            lockedDoor.x = newStart.x;
            lockedDoor.y = newStart.y;
            IsWin = true;
        }
        world[lockedDoor.x][lockedDoor.y] = Tileset.SAND;

    }
    private boolean isCorner(Position p) {
        Position lP = new Position(p.x - 1, p.y);
        Position rP = new Position(p.x + 1, p.y);
        Position uP = new Position(p.x, p.y + 1);
        Position dP = new Position(p.x, p.y - 1);

        if(PosValid(lP) && PosValid(uP)) {
            boolean lu = world[p.x][p.y].equals(Tileset.WALL)
                    && world[lP.x][lP.y].equals(Tileset.WALL)
                    && world[uP.x][uP.y].equals(Tileset.WALL);
            if(lu) {
                return true;
            }
        }
        if(PosValid(rP) && PosValid(uP)) {
            boolean ru = world[p.x][p.y].equals(Tileset.WALL)
                    && world[rP.x][rP.y].equals(Tileset.WALL)
                    && world[uP.x][uP.y].equals(Tileset.WALL);
            if(ru) {
                return true;
            }
        }
        if(PosValid(lP) && PosValid(dP)) {
            boolean ld = world[p.x][p.y].equals(Tileset.WALL)
                    && world[lP.x][lP.y].equals(Tileset.WALL)
                    && world[dP.x][dP.y].equals(Tileset.WALL);
            if(ld) {
                return true;
            }
        }
        if(PosValid(rP) && PosValid(dP)) {
            boolean rd = world[p.x][p.y].equals(Tileset.WALL)
                    && world[rP.x][rP.y].equals(Tileset.WALL)
                    && world[dP.x][dP.y].equals(Tileset.WALL);
            if(rd) {
                return true;
            }
        }
        return false;
    }

    private void inputAfterSeed (String input, int moveIndex, Position gameStart) {
        if (moveIndex < input.length()) {
            input = input.toLowerCase();
            Position moved;
            boolean fileSave = false;

            moved = new Position(gameStart.x, gameStart.y);
            Position test = new Position(0, 0);

            for(int i = moveIndex; i < input.length(); i++) {
                char item = input.charAt(i);
                playerShift(test, moved, item);
                switch (item) {
                    case ':':
                        break;
                    case 'q':
                        fileSave = true;
                        i = input.length();
                        break;
                }
            }

            if (fileSave) {
                saveMaze(moved);
            }
        }
    }
    private void playerShift(Position test, Position moved, char item) {
        switch (item) {
            case 'w':
                test.x = moved.x;
                test.y = moved.y + 1;
                helpPlayerShift(test, moved, 'w');
                break;
            case 's':
                test.x = moved.x;
                test.y = moved.y - 1;
                helpPlayerShift(test, moved, 's');
                break;
            case 'a':
                test.x = moved.x - 1;
                test.y = moved.y;
                helpPlayerShift(test, moved, 'a');
                break;
            case 'd':
                test.x = moved.x + 1;
                test.y = moved.y;
                helpPlayerShift(test, moved, 'd');
                break;
        }
    }
    private void helpPlayerShift(Position test, Position moved, char shift) {
        if(checkInMaze(test)) {
            //if player can move, change the present tile
            if(moved.x == newStart.x && moved.y == newStart.y) {
                world[moved.x][moved.y] = Tileset.WALL;
            } else if(moved.x != lockedDoor.x || moved.y != lockedDoor.y){
                world[moved.x][moved.y] = Tileset.GRASS;

            }

            //change player's position
            if(shift == 'w' || shift =='s') {
                moved.y = test.y;
            } else if(shift == 'a' || shift =='d'){
                moved.x = test.x;
            }

            //change new position's tile after moving
            if(moved.x == lockedDoor.x && moved.y == lockedDoor.y) {
                IsWin = true;
            } else {
                world[moved.x][moved.y] = Tileset.FLOWER;
            }
        }
    }
    private boolean checkInMaze(Position p) {
        try {
            return (world[p.x][p.y].equals(Tileset.GRASS)
                    || world[p.x][p.y].equals(Tileset.SAND));
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private void saveMaze(Position moved) {
        Position savedMoved = new Position(moved.x, moved.y);
        Position savedNewS = new Position(newStart.x, newStart.y);
        Position savedDoor = new Position(lockedDoor.x, lockedDoor.y);

        savedEnd.push(savedMoved);
        savedFiles.push(world);
        savedLockedDoor.push(savedDoor);
        savedNewStart.push(savedNewS);
        savedWin.push(IsWin);

        try {
            ServializeGame();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    private void clearStringGame() {
        world = null;
        RANDOM = null;
        newStart = null;
        lockedDoor = null;
        rightMostRoom = null;
        IsWin = false;
        playerTurn = false;
        gameOver = false;
        side = "right";
        swayRecord = new Stack<>();
    }

    /**
     * PART F playWithKeyboard
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        /*
          1 创建一个游戏窗口
          2 随机生成target string
          3 逐个字符地在游戏窗口上显示target string
          4 等待玩家输入足够多的字符
          5 如果玩家输入的string和target string匹配（如果target string更长不算匹配），则回到第二步
        */
        TETile[][] basic = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                basic[x][y] = Tileset.NOTHING;
            }
        }

        while(!gameOver) {
            //1 create the start window
            gameRenderer startWin = new gameRenderer();
            startWin.initialize(basic);
            startWin.renderUI();

            StdDraw.pause(3000);

            //2 if the player enter n,l,q
            String playerOrder = "";
            if(StdDraw.hasNextKeyTyped()) {
                playerOrder += StdDraw.nextKeyTyped();
            }

            // if one enter q, end the game
            if(playerOrder.equals("q")) {
                gameOver = true;
                break;
            } else { // if one enter n or l, create and show a maze
                Position gameStart = renderOriginalWorld(startWin, playerOrder);

                //3 let the player move in the maze
                Position moved = new Position(gameStart.x, gameStart.y);
                Position test = new Position(0, 0);

                playerTurn = true;
                char item;
                StdDraw.pause(3000);

                while(playerTurn) {
                    while(StdDraw.hasNextKeyTyped()) {
                        item = StdDraw.nextKeyTyped();
                        if (item == 'q') {
                            saveMaze(moved);
                            /*try {
                                ServializeGame();
                            } catch(IOException e) {
                                e.printStackTrace();
                            }*/
                            gameOver = true;
                            break;
                        } else {
                            playerShift(test, moved, item);
                            startWin.renderMaze(world, mouse(startWin), winTip());
                            StdDraw.pause(1000);
                        }
                    }
                    if(!gameOver) {
                        playerTurn = false;
                        StdDraw.pause(5000);
                        startWin.renderMaze(world, mouse(startWin), "Time is up!");
                    } else {
                        break;
                    }
                }
                // if the player use out time, then back to the start window
                world = null;
                StdDraw.pause(2000);
            }
        }

        //if the player enter q during their moving in the maze, then save and end the game
        gameRenderer gameIsOVer = new gameRenderer();
        gameIsOVer.initialize(basic);
        if(IsWin) {
            gameIsOVer.renderWin();
        } else {
            gameIsOVer.renderOver();
        }

    }

    /** clear useless input by the player*/
    private void clearKeyTyped() {
        while(StdDraw.hasNextKeyTyped()) {
            StdDraw.nextKeyTyped();
        }
    }
    /** if one point is under mouse press, the change the left tips on the window */
    private String mouse(gameRenderer startWin) {
        String ans = "Click the tile";

        int tipX;
        int tipY;
        if(StdDraw.isMousePressed()) {
            tipX = (int) (StdDraw.mouseX());
            tipY= (int) (StdDraw.mouseY());
            if(world[tipX][tipY].equals(Tileset.WALL)) {
                ans = "Wall";
            } else if(world[tipX][tipY].equals(Tileset.SAND)) {
                ans = "Locked door";
            } else if(world[tipX][tipY].equals(Tileset.FLOWER)) {
                ans = "You are here";
            } else if(world[tipX][tipY].equals(Tileset.GRASS)) {
                ans = "Floor";
            } else {
                ans = "Outside";
            }
            startWin.renderMaze(world, ans, winTip());
        }

        return ans;

    }
    /** if one has won, then show " you are win", otherwise "keep moving */
    private String winTip() {
        String rightTemp = "";
        if(IsWin) {
            rightTemp = rightTip[1];
        } else {
            rightTemp = rightTip[0];
        }
        return rightTemp;
    }
    /** if one enter n, then show a new maze; otherwise load and show the saved maze */
    private Position renderOriginalWorld(gameRenderer startWin,
                                         String playerOrder) {
        Position moveStart;

        switch (playerOrder.charAt(playerOrder.length() - 1)) {
            case 'l': {
                // 1.render the original window
                startWin.renderUISel("l");

                // 2.load the last saved maze / world
                try {
                    DeServializePerson();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                moveStart = savedEnd.peek();
                //maze and newStart
                world = savedFiles.peek();
                //win point
                lockedDoor = savedLockedDoor.peek();
                //move start
                newStart = savedNewStart.peek();
                IsWin = savedWin.peek();

                // 3.render the maze / world
                startWin.renderMaze(world, mouse(startWin), winTip());
                return moveStart;
            }
            default: {
                // 1.render the original window

                startWin.renderUISel("n");
                StdDraw.pause(500);
                startWin.renderSeed();


                // 2.get seed
                playerTurn = true;
                String seedStr = "";
                long seed;
                while (playerTurn) {
                    while (StdDraw.hasNextKeyTyped()) {
                        char temp;
                        temp = StdDraw.nextKeyTyped();
                        if (Character.isDigit(temp)) {
                            seedStr += temp;
                            startWin.renderSeed(seedStr);
                            StdDraw.pause(100);
                        } else if (temp == 's') {
                            playerTurn = false;
                            break;
                        } else {
                            StdDraw.nextKeyTyped();
                        }
                    }
                    StdDraw.pause(500);
                }
                clearKeyTyped();
                seed = Integer.parseInt(seedStr);


                // 3.create a maze / world
                initializeWorld();
                RANDOM = new Random(seed);
                //initialize variables
                swayRecord.push("");
                TETile[][] finalWorldFrame;
                initializeRightMost();
                //maze and newStart
                setWorld(randomStart(), Tileset.WALL, Tileset.GRASS);
                //win point
                chooseDoor();
                //move start
                moveStart = new Position(newStart.x, newStart.y);
                world[moveStart.x][moveStart.y] = Tileset.FLOWER;


                // 4.render the maze / world
                startWin.renderMaze(world, mouse(startWin), winTip());
                return moveStart;
            }

        }
    }
    /** save the game as a local file */
    private void ServializeGame() throws IOException {
        ObjectOutputStream oo = new ObjectOutputStream(
                new FileOutputStream(new File("game.txt")));

        Game saved = new Game();
        saved.savedFiles = this.savedFiles;
        saved.savedEnd = this.savedEnd;
        saved.savedNewStart = this.savedNewStart;
        saved.savedLockedDoor = this.savedLockedDoor;
        saved.savedWin = this.savedWin;

        oo.writeObject(this);
        oo.close();
    }
    /** read the game from a local file */
    public void DeServializePerson() throws IOException {
        ObjectInputStream oo = new ObjectInputStream(
                new FileInputStream(new File("game.txt")));

        Game load = new Game();

        try {
            load = (Game) oo.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.savedFiles = load.savedFiles;
        this.savedEnd = load.savedEnd;
        this.savedNewStart = load.savedNewStart;
        this.savedLockedDoor = load.savedLockedDoor;
        this.savedWin = load.savedWin;

        oo.close();
    }

}
