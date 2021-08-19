package org.samples;

import com.spun.util.Colors;

import java.awt.*;

public enum Cell {
    Dead(Colors.Grays.Gainsboro),
    Red(Color.red),
    Green(Color.green),
    Yellow(Color.yellow),
    Gray(Color.gray),
    Blue(Color.blue);

    private Color color;

    Cell(Color color) {
        this.color = color;
    }

    public boolean isAlive() {
        return this != Dead;
    }

    public Color color() {
        return this.color;
    }
}
