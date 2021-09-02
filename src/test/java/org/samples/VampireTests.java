package org.samples;

import org.approvaltests.Approvals;
import org.approvaltests.StoryBoard;
import org.junit.jupiter.api.Test;

public class VampireTests {
    @Test
    void testTurnsToVampire() {
        final StoryBoard storyBoard = new StoryBoard();
        final VampireGame vampireGame = new VampireGame(
                new VampireCell(2, 2, false, 99),
                new VampireCell(2, 3, false, 99),
                new VampireCell(3, 2, false, 99),
                new VampireCell(3, 3, false, 99));
        storyBoard.add(vampireGame);
        vampireGame.advanceTurn();
        storyBoard.add(vampireGame);
        Approvals.verify(storyBoard);
    }
}
