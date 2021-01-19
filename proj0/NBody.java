public class NBody {
    /** read radius in a certain txt file*/
    public static double readRadius(String file){
        In in = new In(file);
        int num = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    /** read data of planets in a certain txt file*/
    public static Planet[] readPlanets(String file){
        In in = new In(file);

        int num = in.readInt();
        in.readDouble();
        Planet[] allPlanet = new Planet[num];
        int count = 0;

        while(count < num){
            double xP = in.readDouble();
            double yP = in.readDouble();
            double vX = in.readDouble();
            double vY = in.readDouble();
            double ma = in.readDouble();
            String pic = in.readString();
            allPlanet[count] = new Planet(xP,yP,vX,vY,ma,pic);
            count ++;
        }
        return allPlanet;
    }

    public static void main (String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] aP = readPlanets(filename);
        double r = readRadius(filename);

        StdDraw.setScale(-r, r);

        StdDraw.enableDoubleBuffering();
        double t = 0.0;
        while(t < T){
            Double[] xForces = new Double[aP.length];
            Double[] yForces = new Double[aP.length];
            for(int i = 0; i < aP.length; i++){
                xForces[i] = aP[i].calcNetForceExertedByX(aP);
                yForces[i] = aP[i].calcNetForceExertedByY(aP);
            }
            for(int i = 0; i < aP.length; i++ ){
                aP[i].update(t,xForces[i],yForces[i]);
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet s : aP) {
                s.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t += dt;

        }
        StdOut.printf("%d\n", aP.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < aP.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    aP[i].xxPos, aP[i].yyPos,aP[i].xxVel,
                    aP[i].yyVel, aP[i].mass, aP[i].imgFileName);
        }
    }
}
