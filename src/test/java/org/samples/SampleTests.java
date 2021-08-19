package org.samples;


import com.spun.util.logger.SimpleLogger;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ImageWebReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UseReporter(ImageWebReporter.class)
public class SampleTests {
  /*
  2. New cells have color
   */

    @Test
    void testStaticSquare() {
        // setup board with cell of three niegbors

        // advance turn
    }

    @Test
    public void testBlinker() {
        Game game = new Game((x, y) -> (2 <= x && x <= 4 && y == 2) ? Cell.Red : Cell.Dead);
        AwtApprovals.verifySequence(2, (Integer n) -> {
            if (n == 0) {
                return game;
            }
            return game.advanceTurn();
        });
        // test color of cell

        assertEquals(Cell.Red, game.getCell(3, 2));
    }

    @Test
    public void testSquare() {
        Game game = createGame(_(1, 1, Cell.Red), _(1, 2, Cell.Blue), _(2, 1, Cell.Yellow), _(2, 2, Cell.Green));
        AwtApprovals.verifySequence(2, (Integer n) -> {
            if (n == 0) {
                return game;
            }
            return game.advanceTurn();
        });
    }

    private Game createGame(Applesauce... applesauce) {
        return new Game((x, y) -> {
            final Applesauce first = Query.first(applesauce, a -> a.x == x && a.y == y);
            return first == null ? Cell.Dead : first.color;
        });
    }

    private Applesauce _(int x, int y, Cell color) {
        return new Applesauce(x, y, color);
    }

    @Test
    void name() {
        Game game = new Game((x, y) -> {
            final Random random = new Random(y * x);
            return random.nextBoolean() ? Cell.Red : Cell.Dead;
        });
        AwtApprovals.verifySequence(6, (Integer n) -> {
            if (0 < n) {
                SimpleLogger.variable("n", n);
                game.advanceTurn();
            }
            return game;
        });
    }
}
