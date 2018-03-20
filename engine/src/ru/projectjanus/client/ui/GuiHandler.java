package ru.projectjanus.client.ui;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;

import ru.projectjanus.client.math.MatrixUtils;
import ru.projectjanus.client.math.Rect;

/**
 * Created by raultaylor.
 */

public class GuiHandler {

    private static Matrix3 scrToGuiBoundsMatrix;
    private static Vector2 temp;
    private static Rect screenBounds;

    static {
        scrToGuiBoundsMatrix = new Matrix3();
        temp = new Vector2();
        screenBounds = new Rect();
    }

    public static void updateMatrix(int width, int height){
        screenBounds.setSize(width, height);
        screenBounds.setBottom(0);
        screenBounds.setLeft(0);

        Rect guiBounds = new Rect();
        guiBounds.setSize(1f*width/height,1f);
        MatrixUtils.calcTransitionMatrix(scrToGuiBoundsMatrix, screenBounds, guiBounds);
        System.out.println(scrToGuiBoundsMatrix);
    }

    public static Vector2 getGuiPosition(int width, int height){
        temp.set(width, screenBounds.getHeight()-height).mul(scrToGuiBoundsMatrix);
        return temp;
    }

}
