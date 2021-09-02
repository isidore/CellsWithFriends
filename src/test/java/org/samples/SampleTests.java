package org.samples;


import com.spun.util.Colors;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.DelayedClipboardReporter;
import org.approvaltests.reporters.ImageWebReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;
import org.lambda.query.Query;

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
        CellsWithFriendsGame game = new CellsWithFriendsGame((x, y) -> (2 <= x && x <= 4 && y == 2) ? CellWithFriends.Red : CellWithFriends.Dead);
        verify(game, 2);
        // test color of cell

        assertEquals(CellWithFriends.Red, game.getCell(3, 2));
    }

    @Test
    public void testSquare() {
        CellsWithFriendsGame game = createGame(_(1, 1, CellWithFriends.Red), _(1, 2, CellWithFriends.Blue), _(2, 1, CellWithFriends.Yellow), _(2, 2, CellWithFriends.Green));
        verify(game, 2);
    }

    @Test
    public void testBeacon() {
        CellsWithFriendsGame game = createGame(
                _(1, 1, CellWithFriends.Green),
                _(1, 2, CellWithFriends.Green),
                _(2, 1, CellWithFriends.Green),
                _(2, 2, CellWithFriends.Green),
                _(3, 3, CellWithFriends.Gray),
                _(3, 4, CellWithFriends.Yellow),
                _(4, 3, CellWithFriends.Red),
                _(4, 4, CellWithFriends.Blue)
                );
        verify(game, 4);
    }

    private void verify(CellsWithFriendsGame game, int numberOfFrames) {
        verify(game, numberOfFrames, Duration.ofMillis(500));
    }

    private void verify(CellsWithFriendsGame game, int numberOfFrames, Duration duration) {
        AwtApprovals.verifySequence(numberOfFrames, duration, (Integer n) -> {
            if (n == 0) {
                return game;
            }
            return game.advanceTurn();
        });
    }

    private CellsWithFriendsGame createGame(StartingState... startingState) {
        return new CellsWithFriendsGame((x, y) -> {
            final StartingState first = Query.first(startingState, a -> a.x == x && a.y == y);
            return first == null ? CellWithFriends.Dead : first.color;
        });
    }

    private StartingState _(int x, int y, CellWithFriends color) {
        return new StartingState(x, y, color);
    }

    @Test
    void name() {
        CellsWithFriendsGame game = new CellsWithFriendsGame((x, y) -> {
            final Random random = new Random(y * x);
            return random.nextBoolean() ? CellWithFriends.Red : CellWithFriends.Dead;
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
        CellsWithFriendsGame game = createGame(
                _(1, 1, CellWithFriends.Green),
                _(2, 3, CellWithFriends.Green),
                _(3, 1, CellWithFriends.Blue)
        );
        game.NumberOfCells = 5;
        game.CellSizeInPixels =40;
        setCircleDrawer(game);
        verify(game, 3, Duration.ofMillis(1200));
    }

    private void setCircleDrawer(CellsWithFriendsGame game) {
        game.draw = (x, y, s, g) -> {
            int b = 4;
            g.fillOval(x * s + b, y * s + b, s - 2*b, s - 2*b);
            g.setColor(Colors.Grays.DarkSlateGray);
            g.drawOval(x * s + b, y * s + b, s - 2*b, s - 2*b);
        };
    }

    @Test
    public void testSample2() {
        CellsWithFriendsGame game = createGame(
                _(1, 1, CellWithFriends.Yellow),
                _(3, 3, CellWithFriends.Red),
                _(2, 1, CellWithFriends.Green)
        );
        game.NumberOfCells = 5;
        game.CellSizeInPixels =40;
        setCircleDrawer(game);
        verify(game, 3, Duration.ofMillis(1200));
    }

}
