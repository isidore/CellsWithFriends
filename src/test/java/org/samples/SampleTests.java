package org.samples;


import com.spun.swing.Paintable;
import com.spun.util.logger.SimpleLogger;
import org.approvaltests.Approvals;
import org.approvaltests.awt.AwtApprovals;
import org.approvaltests.reporters.ImageWebReporter;
import org.approvaltests.reporters.UseReporter;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
@UseReporter(ImageWebReporter.class)
public class SampleTests
{
  @Test
  public void testBlinker()
  {
    Game game = new Game((x,y) -> 2 <= x && x <= 4 && y == 2);
    AwtApprovals.verifySequence(2, (Integer n) -> {
      if (0 < n){
        SimpleLogger.variable("n",n);
        game.advanceTurn();}
      return game;
    });

    assertEquals(5, 5);
  }

  @Test
  void name() {
    Game game = new Game((x,y) -> {
      final Random random = new Random(y*x);
      return random.nextBoolean();
    });
    AwtApprovals.verifySequence(6, (Integer n) -> {
      if (0 < n){
        SimpleLogger.variable("n",n);
        game.advanceTurn();}
      return game;
    });
  }
}
