package com.example._2048.util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.HashMap;

public class Colors {

    private static final HashMap<Integer, Paint> COLORS = new HashMap<>();

    public static void initColors() {
        COLORS.put(0, Color.LIGHTGREY);
        COLORS.put(2, Color.WHITE);
        COLORS.put(4, Color.LIGHTSALMON);
        COLORS.put(8, Color.ORANGERED);
        COLORS.put(16, Color.ORANGE);
        COLORS.put(32, Color.LIGHTBLUE);
        COLORS.put(64, Color.LIGHTSEAGREEN);
    }


    public static Color getColor(int val) {
        return (Color) COLORS.get(val);
    }

}
