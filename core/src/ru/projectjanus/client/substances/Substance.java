package ru.projectjanus.client.substances;

import com.badlogic.gdx.math.Vector2;

import ru.projectjanus.client.math.Circle;
import ru.projectjanus.client.pool.Exterminable;
import ru.projectjanus.client.Linkable;

/**
 * Created by raultaylor.
 */
public class Substance extends Circle implements Exterminable, Linkable {
    protected int weight;
    protected float volume;
    private boolean isDestroyed = false;
    private String myType;

    public Substance() {
        super();
        pos.set(0, 0);
        weight = 1;
        volume = 1;
        size = (float) (1.0 / (2 * Math.PI));
    }

    @Override
    public String getNameType() {
        return myType;
    }

    @Override
    public boolean isDestroyed() {
        return isDestroyed;
    }

    @Override
    public void setDestroyed(boolean flag) {
        this.isDestroyed = flag;
    }

    public void set(Vector2 pos, int weight, float density, String myType) {
        this.pos.set(pos);
        this.volume = weight / density;
        this.weight = weight;
        this.myType = myType;
        calcSize();
    }

    protected void calcSize() {
        size = (float) (Math.exp(Math.log(volume) / 3));

    }

    public float getVolume() {
        return this.volume;
    }

    public int getWeight() {
        return this.weight;
    }
}
