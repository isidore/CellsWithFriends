package org.samples;

import com.spun.util.Colors;

import java.awt.*;

public class CellWithFriends extends Cell {

    public static org.samples.CellWithFriends Dead = new org.samples.CellWithFriends(Colors.Grays.Gainsboro);
    public static org.samples.CellWithFriends Red = new org.samples.CellWithFriends(Color.red);
    public static org.samples.CellWithFriends Green = new org.samples.CellWithFriends(Color.green);
    public static org.samples.CellWithFriends Yellow = new org.samples.CellWithFriends(Color.yellow);
    public static org.samples.CellWithFriends Gray = new org.samples.CellWithFriends(Color.gray);
    public static org.samples.CellWithFriends Blue = new org.samples.CellWithFriends(Color.blue);

    private Color color;

    CellWithFriends(Color color) {
        this.color = color;
    }

    public boolean isAlive() {
        return this != Dead;
    }

    public Color color() {
        return this.color;
    }
}
