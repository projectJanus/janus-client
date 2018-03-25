package ru.projectjanus.client;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.projectjanus.client.math.Rect;

/**
 * Created by raultaylor.
 */
public interface iVisualObject {
    void draw(SpriteBatch batch);

    Rect getRect();
}
