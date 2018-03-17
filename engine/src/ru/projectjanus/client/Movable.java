package ru.projectjanus.client;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by raultaylor.
 */

public interface Movable {

    void move(Vector2 dst);

    void stop();
}
