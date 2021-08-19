package org.samples;

import com.spun.swing.Paintable;
import com.spun.util.Colors;
import org.lambda.actions.Action0;
import org.lambda.functions.Function2;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Game implements Paintable {
    private Function2<Integer, Integer, Cell> board;

    public Game(Function2<Integer, Integer, Cell> board) {
        this.board = board;
    }

    public Game advanceTurn() {
        final Function2<Integer, Integer, Cell> b = this.board;
        this.board = (x, y) -> {
            final Queryable<Cell> colours = Queryable.as(b.call(x - 1, y - 1), b.call(x, y - 1), b.call(x + 1, y - 1),
                    b.call(x - 1, y), b.call(x + 1, y),
                    b.call(x - 1, y + 1), b.call(x, y + 1), b.call(x + 1, y + 1)).where(c -> c.isAlive());
            final int count = colours.size();
            if (b.call(x,y).isAlive()) {
                    return count == 2 || count==3 ? b.call(x,y) : Cell.Dead;
            } else {
                return count == 3 ? getBirthColour(colours) : Cell.Dead;
            }
        };

        return this;
    }

    private Cell getBirthColour(Queryable<Cell> colours) {
        HashMap<Cell, Integer> counts = new HashMap<>();
        for (Cell colour : colours) {
            counts.putIfAbsent(colour, 0);
            counts.put(colour, counts.get(colour) + 1);
        }
        Map.Entry<Cell, Integer> max = Query.max(counts.entrySet(), e -> e.getValue());
        if (max.getValue() == 1){
            return Cell.Gray;
        }
        return max.getKey();
    }

    @Override
    public Dimension getSize() {
        return new Dimension(200, 200);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(Cell.Dead.color());
        graphics.fillRect(0, 0, 200, 200);
        int size = 10;
        for (int x = 0; x < 20; x++) {
            for (int y = 0; y < 20; y++) {
                if (board.call(x, y).isAlive()) {
                    graphics.setColor(board.call(x, y).color());
                    graphics.fillRect(size * x, size * y, size, size);
                } else {
                    graphics.setColor(Color.black);
                    graphics.drawRect(size * x, size * y, size, size);
                }
            }
        }
    }

    @Override
    public void registerRepaint(Action0 action0) {

    }

    public Cell getCell(int x, int y) {
        return this.board.call(x, y);
    }
}
