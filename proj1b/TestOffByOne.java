import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/
    @Test
    public void testEqualChars(){
        CharacterComparator test = new OffByOne();
        assertTrue(test.equalChars('a','b'));
        assertTrue(test.equalChars('r','q'));
        assertFalse(test.equalChars('a','e'));
        assertFalse(test.equalChars('z','a'));
        assertFalse(test.equalChars('a','a'));

    }
}
