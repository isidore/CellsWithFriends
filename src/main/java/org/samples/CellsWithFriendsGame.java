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

public class CellsWithFriendsGame extends Game<CellWithFriends> {

    public CellsWithFriendsGame(Function2<Integer, Integer, CellWithFriends> board) {
        super(board);
    }
    public CellsWithFriendsGame advanceTurn() {
        final Function2<Integer, Integer, CellWithFriends> b = this.board;
        this.board = (x, y) -> {
            final Queryable<CellWithFriends> colours = Queryable.as(b.call(x - 1, y - 1), b.call(x, y - 1), b.call(x + 1, y - 1),
                    b.call(x - 1, y), b.call(x + 1, y),
                    b.call(x - 1, y + 1), b.call(x, y + 1), b.call(x + 1, y + 1)).where(c -> c.isAlive());
            final int count = colours.size();
            if (b.call(x, y).isAlive()) {
                return count == 2 || count == 3 ? b.call(x, y) : CellWithFriends.Dead;
            } else {
                return count == 3 ? getBirthColour(colours) : CellWithFriends.Dead;
            }
        };

        return this;
    }
    private CellWithFriends getBirthColour(Queryable<CellWithFriends> colours) {
        HashMap<CellWithFriends, Integer> counts = new HashMap<>();
        for (CellWithFriends colour : colours) {
            counts.putIfAbsent(colour, 0);
            counts.put(colour, counts.get(colour) + 1);
        }
        Map.Entry<CellWithFriends, Integer> max = Query.max(counts.entrySet(), e -> e.getValue());
        if (max.getValue() == 1) {
            return CellWithFriends.Gray;
        }
        return max.getKey();
    }
}
