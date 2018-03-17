package ru.projectjanus.client.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.projectjanus.client.Sprite;
import ru.projectjanus.client.math.Rect;

/**
 * Created by raultaylor.
 */

public class ExampleGui extends Sprite implements  GuiVisualObject {

    public ExampleGui(TextureAtlas atlas) {
        super(atlas.findRegion("touchpad_center"));
        setSize(0.5f,0.5f);
        pos.set(0.5f,0.2f);
    }

    @Override
    public Rect getRect() {
        return this;
    }
}
