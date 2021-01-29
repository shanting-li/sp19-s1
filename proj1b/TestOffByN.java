import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    @Test
    public void testEqualChars(){
        CharacterComparator test0 = new OffByN(0);
        assertTrue(test0.equalChars('d', 'd'));
        assertTrue(test0.equalChars('e', 'e'));
        assertFalse(test0.equalChars('x','e'));

        CharacterComparator test1 = new OffByN(1);
        assertTrue(test1.equalChars('a','b'));
        assertTrue(test1.equalChars('r','q'));
        assertFalse(test1.equalChars('a','e'));
        assertFalse(test1.equalChars('z','a'));
        assertFalse(test1.equalChars('a','a'));

        CharacterComparator test5 = new OffByN(5);
        assertTrue(test5.equalChars('a','f'));
        assertTrue(test5.equalChars('f','a'));
        assertFalse(test5.equalChars('f','h'));
        assertFalse(test5.equalChars('z','a'));
        assertFalse(test5.equalChars('a','a'));
    }
}

