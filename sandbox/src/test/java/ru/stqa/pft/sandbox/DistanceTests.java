package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DistanceTests {

    @Test
    public void testArea(){
        Point p1 = new Point(1.0, 1.0);
        Point p2 = new Point(2.0, 2.0);

        Assert.assertEquals(p1.distance(p2), Math.sqrt(2.0));
    }

    @Test
    public void testArea2(){
        Point p1 = new Point(1.0, 1.0);
        Point p3 = new Point(4.0, 4.0);

        Assert.assertEquals(p1.distance(p3), Math.sqrt(18.0));
    }

    @Test
    public void testArea3(){
        Point p2 = new Point(2.0, 2.0);
        Point p3 = new Point(4.0, 4.0);

        Assert.assertEquals(p3.distance(p2), Math.sqrt(8.0));
    }


}
