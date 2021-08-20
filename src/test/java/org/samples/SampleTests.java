package org.samples;


import com.spun.util.Colors;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.ImageWebReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;

import java.awt.*;
import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UseReporter({ImageWebReporter.class, DelayedClipboardReporter.class})
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
        verify(game, 2);
        // test color of cell

        assertEquals(Cell.Red, game.getCell(3, 2));
    }

    @Test
    public void testSquare() {
        Game game = createGame(_(1, 1, Cell.Red), _(1, 2, Cell.Blue), _(2, 1, Cell.Yellow), _(2, 2, Cell.Green));
        verify(game, 2);
    }

    @Test
    public void testBeacon() {
        Game game = createGame(
                _(1, 1, Cell.Green),
                _(1, 2, Cell.Green),
                _(2, 1, Cell.Green),
                _(2, 2, Cell.Green),
                _(3, 3, Cell.Gray),
                _(3, 4, Cell.Yellow),
                _(4, 3, Cell.Red),
                _(4, 4, Cell.Blue)
                );
        verify(game, 4);
    }

    private void verify(Game game, int numberOfFrames) {
        verify(game, numberOfFrames, Duration.ofMillis(500));
    }

    private void verify(Game game, int numberOfFrames, Duration duration) {
        AwtApprovals.verifySequence(numberOfFrames, duration, (Integer n) -> {
            if (n == 0) {
                return game;
            }
            return game.advanceTurn();
        });
    }

    private Game createGame(StartingState... startingState) {
        return new Game((x, y) -> {
            final StartingState first = Query.first(startingState, a -> a.x == x && a.y == y);
            return first == null ? Cell.Dead : first.color;
        });
    }

    private StartingState _(int x, int y, Cell color) {
        return new StartingState(x, y, color);
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
    @Test
    public void testSample1() {
        Game game = createGame(
                _(1, 1, Cell.Green),
                _(2, 3, Cell.Green),
                _(3, 1, Cell.Blue)
        );
        game.NumberOfCells = 5;
        game.CellSizeInPixels =40;
        setCircleDrawer(game);
        verify(game, 3, Duration.ofMillis(1200));
    }

    private void setCircleDrawer(Game game) {
        game.draw = (x, y, s, g) -> {
            int b = 4;
            g.fillOval(x * s + b, y * s + b, s - 2*b, s - 2*b);
            g.setColor(Colors.Grays.DarkSlateGray);
            g.drawOval(x * s + b, y * s + b, s - 2*b, s - 2*b);
        };
    }

    @Test
    public void testSample2() {
        Game game = createGame(
                _(1, 1, Cell.Yellow),
                _(3, 3, Cell.Red),
                _(2, 1, Cell.Green)
        );
        game.NumberOfCells = 5;
        game.CellSizeInPixels =40;
        setCircleDrawer(game);
        verify(game, 3, Duration.ofMillis(1200));
    }

}
