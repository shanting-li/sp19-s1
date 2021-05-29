public class test {
    public static void main(String[] args) {
        //String[] x = new String[]{"apple", "pad", "back"};
        String[] x = new String[]{"maybe", "think", "mystery", "Mike",
                "seven", "help", "us","sos", "please"};

        String[] sorted = RadixSort.sort(x);
        for (String s : sorted) {
            System.out.print(s + " ");//Mike, help, maybe, please, seven, sos, think, us
        }

    }
}
