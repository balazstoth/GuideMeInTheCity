package hu.uniobuda.nik.guideme.Models;

/**
 * Created by tothb on 2017. 03. 21..
 */

public class Coordinate
{
    double x;
    double y;

    public double getX() {
        return x;
    }
    public void setX(double x){  this.x = x;};
    public double getY() {
        return y;
    }
    public void setY(double y){  this.y = y;};

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "X: " + x + ", Y: " + y;
    }
}
