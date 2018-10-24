public class CalcDistance {

    public static void main (String[] args){
        Point a = new Point(0.0, 0.0);
        Point b = new Point(3.0, 4.0);

        System.out.println("Distance from A to B = " + distance(a, b) + " (by Function)");
        System.out.println("Distance from A to B = " + distance(b, a) + " (by Function)");

        System.out.println("Distance from A to B = " + a.distance(b) + " (by Method)");
        System.out.println("Distance from A to B = " + b.distance(a) + " (by Method)");
    }

    public static double distance(Point p1, Point p2){
        return Math.sqrt((p2.x-p1.x)*(p2.x-p1.x) + (p2.y-p1.y)*(p2.y-p1.y));
    }

}
