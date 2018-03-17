package ru.projectjanus.client.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.projectjanus.client.math.Rect;

/**
 * Created by raultaylor.
 */

public interface GuiVisualObject {

    void draw(SpriteBatch batch);
    Rect getRect();

}
