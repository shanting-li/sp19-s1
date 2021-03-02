package byog.lab6;

public class TestMGame {
    public static void testGenRanString(int n) {
        MemoryGame test = new MemoryGame(40, 40,9);
        System.out.println(test.generateRandomString(n));
    }
    public static void testDrawFrame(String s) {
        MemoryGame test = new MemoryGame(40, 40, 8);
        test.drawFrame(s);
    }
    public static void testFlashSequence(String s) {
        MemoryGame test = new MemoryGame(40, 40, 8);
        test.flashSequence(s);
    }

    public static void main(String[] args) {
        /*for(int i = 0; i <10; i++) {
            testGenRanString(5);
        }*/
        testDrawFrame("n");
        //testFlashSequence("nobody");
    }

}
