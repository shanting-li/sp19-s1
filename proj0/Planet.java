/**import java.math;*/
public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    /**The name of the file that corresponds to the planet image, eg,jupiter.gif)*/
    public String imgFileName;
    private double g = 6.67e-11;

    /**constructor 1*/
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img; /*"./images/" + img;*/
    }

    /**constructor 2, return a copy of p*/
    public Planet(Planet p){
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /**return the distance between this planet and t*/
    public double calcDistance(Planet t){
        double ans = Math.sqrt(Math.pow((t.xxPos-xxPos),2) +
                Math.pow((t.yyPos-yyPos),2));
        return ans;
    }

    /**return the force on this caused by t*/
    public  double calcForceExertedBy(Planet t){
        double r = this.calcDistance(t);
        double f = g * mass * t.mass / Math.pow(r,2);
        return f;
    }

    /**return the x-force on this caused by t*/
    public double calcForceExertedByX(Planet t){
        double f = calcForceExertedBy(t);
        double r = this.calcDistance(t);
        double dx = t.xxPos - xxPos;
        double fx = f * dx / r;
        return fx;
    }

    /**return the y-force on this caused by t*/
    public double calcForceExertedByY(Planet t){
        double f = calcForceExertedBy(t);
        double r = this.calcDistance(t);
        double dy = t.yyPos - yyPos;
        double fy = f * dy / r;
        return fy;
    }

    /**return the x-net-force on this caused by an array of planets*/
    public double calcNetForceExertedByX(Planet[] p){
        double ans = 0.0;
        for(Planet s : p){
            if (this.equals(s)){
                continue;
            }else{
                ans += calcForceExertedByX(s);
            }
        }
        return ans;
    }

    /**return the y-net-force on this caused by an array of planets*/
    public double calcNetForceExertedByY(Planet[] p){
        double ans = 0.0;
        for(Planet s : p){
            if (this.equals(s)){
                continue;
            }else{
                ans += calcForceExertedByY(s);
            }
        }
        return ans;
    }

    /**update velocity and position*/
    public void update(double dt, double fX, double fY){
        /** ax,ay*/
        double ax = fX / mass;
        double ay = fY / mass;

        /** update v*/
        xxVel = xxVel + dt * ax;
        yyVel = yyVel + dt * ay;

        /** update px py*/
        xxPos = xxPos + dt * xxVel;
        yyPos = yyPos + dt * yyVel;
    }

    public void draw(){
        String file = "images/" + imgFileName;
        StdDraw.picture(xxPos, yyPos, file);
    }

}
