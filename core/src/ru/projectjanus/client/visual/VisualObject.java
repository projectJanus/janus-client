package ru.projectjanus.client.visual;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.projectjanus.client.Linkable;
import ru.projectjanus.client.Sprite;
import ru.projectjanus.client.iVisualObject;
import ru.projectjanus.client.pool.Exterminable;

/**
 * Created by raultaylor.
 */
public class VisualObject extends Sprite implements Exterminable, iVisualObject {
    private boolean isDestroyed = true;
    private Linkable myLink;

    public VisualObject() {
        super();
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public void setDestroyed(boolean flag) {
        if (flag) {
            this.isDestroyed = flag;
            myLink = null;
        }
    }

    public void set(Linkable link, TextureAtlas atlas) {
        setDestroyed(false);
        myLink = link;
        regions[0] = atlas.findRegion(myLink.getNameType());
        update();
    }

    public void update() {
        if (myLink == null) {
            setDestroyed(true);
        } else {
            setSize(myLink.getSize() * 2, myLink.getSize() * 2);
            pos.set(myLink.getPos());
        }

    }

    public Linkable getLink() {
        return myLink;
    }
}
