package org.samples;


import com.spun.util.logger.SimpleLogger;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ImageWebReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

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
        AwtApprovals.verifySequence(game, 1, (Integer n) -> {
            return game.advanceTurn();
        });
        // test color of cell

        assertEquals(Cell.Red, game.getCell(3, 2));
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
