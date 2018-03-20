package ru.projectjanus.client.visual;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.projectjanus.client.pool.Pool;

/**
 * Created by raultaylor.
 */

public class VisualPool extends Pool<VisualObject> {

    public void drawAllActiveOjects(SpriteBatch batch){
        for(VisualObject object: this.activeObjects){
            object.draw(batch);
        }
    }

    @Override
    protected VisualObject newObject() {
        return new VisualObject();
    }
}
