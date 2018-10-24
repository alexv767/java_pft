package ru.stqa.pft.sandbox;

public class Point{
    public double x;
    public double y;

    Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double distance(Point b){   // distance from point a ('this') to point 'b'
        return Math.sqrt((b.x-this.x)*(b.x-this.x) + (b.y-this.y)*(b.y-this.y));
    }
}
