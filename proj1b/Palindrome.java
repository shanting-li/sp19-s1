public class Palindrome {
    /** return a Deque where the characters appear in the same order as in the String. */
    public Deque<Character> wordToDeque(String word) {
        //Create a deque containing character.
        Deque<Character> ans = new ArrayDeque<Character>();

        //Go through every character in word,
        //adding each to the last of the deque one by one.
        for (int i = 0; i < word.length(); i++){
            ans.addLast(word.charAt(i));
        }
        //Return the deque.
        return ans;
    }

    private String listToWord(Deque<Character> x) {
        String ans = "";
        for (int i = 0; i < x.size(); i++){
            ans += x.get(i);
        }
        return ans;
    }

    /** return true if the given word is a palindrome, and false otherwise */
    public boolean isPalindrome(String word) {
        //j基线条件：如果word.length<=1，return true
        //递归：removefirst = removelast
        if (word.length() <= 1) {
            return true;
        } else {
            Deque<Character> ans = wordToDeque(word);
            char F = ans.removeFirst();
            char L = ans.removeLast();
            word = listToWord(ans);
            return (F == L) & (new Palindrome().isPalindrome(word));
        }
    }

    /** “flake” is an off-by-1 palindrome,
     * even though ‘a’ is not one character off from itself.
     * */
    public boolean isPalindrome(String word, CharacterComparator cc){
        if (word.length() <= 1) {
            return true;
        } else {
            Deque<Character> ans = wordToDeque(word);
            char F = ans.removeFirst();
            char L = ans.removeLast();
            word = listToWord(ans);
            return (cc.equalChars(F,L)) & (new Palindrome().isPalindrome(word,cc));
        }
    }
}
