package org.samples;

import com.spun.swing.Paintable;
import org.lambda.actions.Action0;
import org.lambda.actions.Action4;
import org.lambda.functions.Function2;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Game<C extends Cell> implements Paintable {
    public int NumberOfCells = 20;
    public int CellSizeInPixels = 10;
    public Action4<Integer, Integer, Integer, Graphics> draw = (x1, y1, size, g) -> g.fillRect(size * x1, size * y1, size, size);
    protected Function2<Integer, Integer, C> board;

    public Game(Function2<Integer, Integer, C> board) {
        this.board = board;
    }




    @Override
    public Dimension getSize() {
        return new Dimension(NumberOfCells * CellSizeInPixels, NumberOfCells * CellSizeInPixels);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(CellWithFriends.Dead.color());
        final Dimension s = getSize();
        graphics.fillRect(0, 0, s.width, s.height);
        for (int x = 0; x < NumberOfCells; x++) {
            for (int y = 0; y < NumberOfCells; y++) {
                final C cell = board.call(x, y);
                if (cell.isAlive()) {
                    drawCell(graphics, x, y, cell);
                }
                graphics.setColor(Color.black);
                graphics.drawRect(CellSizeInPixels * x, CellSizeInPixels * y, CellSizeInPixels, CellSizeInPixels);

            }
        }
    }

    private void drawCell(Graphics graphics, int x, int y, C cell) {
        graphics.setColor(cell.color());
        draw.call(x, y, CellSizeInPixels, graphics);
    }

    @Override
    public void registerRepaint(Action0 action0) {

    }

    public C getCell(int x, int y) {
        return this.board.call(x, y);
    }
}
