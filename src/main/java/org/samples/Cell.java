package org.samples;

import java.awt.*;

public enum Cell {
    Dead(Color.cyan), Red(Color.red), Green(Color.green), Yellow(Color.yellow), Gray(Color.gray);
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
