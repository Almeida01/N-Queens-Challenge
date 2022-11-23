package tests.services;

import main.models.Board;
import main.services.BestFirst;
import main.services.Ilayout;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BestFirstTests {

    @Test
    public void testSolve() {
        BestFirst bf = new BestFirst();

        assertEquals(0, bf.solve(new Board(1)).getG());
        assertEquals(0, bf.solve(new Board(80)).getG());
        assertEquals(0, bf.solve(new Board(512)).getG());
    }
}