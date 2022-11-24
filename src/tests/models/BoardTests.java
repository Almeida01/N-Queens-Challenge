package tests.models;

import main.models.Board;
import main.services.Ilayout;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTests {
    @Test
    public void testConstructor() {
        Board b1 = new Board(1);
        Board b2 = new Board(4);

        assertEquals(" Q \nNUMBER OF COLLISIONS: 0\n", b1.toString());
        assertEquals(" -  Q  -  - \n -  -  -  Q \n Q  -  -  - \n -  -  Q  - \nNUMBER OF COLLISIONS: 0\n", b2.toString());
    }

    @Test
    public void testExactConstructor() {
        Board b1 = new Board(1, new int[]{0}, 0);
        Board b2 = new Board(4, new int[]{1,3,0,2},0);

        assertEquals(" Q \nNUMBER OF COLLISIONS: 0\n", b1.toString());
        assertEquals(" -  Q  -  - \n -  -  -  Q \n Q  -  -  - \n -  -  Q  - \nNUMBER OF COLLISIONS: 0\n", b2.toString());
    }

    @Test
    public void testCollisions() {
        Board b1 = new Board(30);
        Board b2 = new Board(100);
        Board b3 = new Board(1000);
        Board b4 = new Board(3781);
        Board b5 = new Board(3782);

        assertEquals(0, b1.getNumOfCollisions());
        assertEquals(0, b2.getNumOfCollisions());
        assertEquals(0, b3.getNumOfCollisions());
        assertEquals(0, b4.getNumOfCollisions());
        assertEquals(1261, b5.getNumOfCollisions());
    }

    @Test
    public void testChildren() {
        Board b1 = new Board(1);
        assertEquals(new ArrayList<>(), b1.children());

        Board b2 = new Board(8);
        List<Ilayout> expectedChildren = new ArrayList<>();
        expectedChildren.add(new Board(8,new int[]{1,7,5,3,0,2,4,6},1));
        expectedChildren.add(new Board(8,new int[]{1,3,7,5,0,2,4,6},2));
        expectedChildren.add(new Board(8,new int[]{1,3,5,7,2,0,4,6},2));
        expectedChildren.add(new Board(8,new int[]{1,3,5,7,4,2,0,6},1));
        assertEquals(expectedChildren.toString(), b2.children().toString());
    }

}