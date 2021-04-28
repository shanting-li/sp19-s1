package hw3.hash;
import java.awt.Color;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;


public class SimpleOomage implements Oomage {
    protected int red;
    protected int green;
    protected int blue;

    private static final double WIDTH = 0.01;
    private static final boolean USE_PERFECT_HASH = true;

    @Override
    public boolean equals(Object o) {
        //1 如果地址相同返回true
        if (this == o) {
            return true;
        }

        //2 如果o为null返回false
        if (o == null) {
            return false;
        }

        //3 如果类型不同getClass，返回false
        if (this.getClass() != o.getClass()) {
            return false;
        }

        //4 cast o 的类型为SimpleOomage，比较指定影响因素，若相同则返回true
        SimpleOomage com = (SimpleOomage) o;
        return this.red == com.red && this.green == com.green && this.blue == com.blue;
    }

    /* Uncomment this method after you've written
       equals and failed the testHashCodeAndEqualsConsistency
       test.*/
    @Override
    public int hashCode() {
        if (!USE_PERFECT_HASH) {
            return red + green + blue;
        } else {
            int  ans = 0;
            int[] color = new int[]{this.red, this.green, this.blue};
            for (int i = 0; i < color.length; i ++) {
                ans *= 53;
                //ans += color[i] - 1;
                ans += color[i] / 5;
        }
            return ans;
        }

    }

    public SimpleOomage(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException();
        }
        if ((r % 5 != 0) || (g % 5 != 0) || (b % 5 != 0)) {
            throw new IllegalArgumentException("red/green/blue values must all be multiples of 5!");
        }
        red = r;
        green = g;
        blue = b;
    }

    @Override
    public void draw(double x, double y, double scalingFactor) {
        StdDraw.setPenColor(new Color(red, green, blue));
        StdDraw.filledSquare(x, y, WIDTH * scalingFactor);
    }

    public static SimpleOomage randomSimpleOomage() {
        int red = StdRandom.uniform(0, 51) * 5;
        int green = StdRandom.uniform(0, 51) * 5;
        int blue = StdRandom.uniform(0, 51) * 5;
        return new SimpleOomage(red, green, blue);
    }

    public static void main(String[] args) {
        System.out.println("Drawing 4 random simple Oomages.");
        randomSimpleOomage().draw(0.25, 0.25, 1);
        randomSimpleOomage().draw(0.75, 0.75, 1);
        randomSimpleOomage().draw(0.25, 0.75, 1);
        randomSimpleOomage().draw(0.75, 0.25, 1);

        /*SimpleOomage s1 = new SimpleOomage(0, 0, 155);
        SimpleOomage s2 = new SimpleOomage(0, 5, 0);
        System.out.println(s1.hashCode());
        System.out.println(s2.hashCode());*/
    }

    public String toString() {
        return "R: " + red + ", G: " + green + ", B: " + blue;
    }
} 
