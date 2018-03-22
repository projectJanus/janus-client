package ru.projectjanus.client.substances;

import ru.projectjanus.client.pool.Pool;

/**
 * Created by raultaylor.
 */
public class SubstancePool extends Pool {
    @Override
    public Substance obtain() {
        return (Substance) super.obtain();
    }

    @Override
    protected Substance newObject() {
        return new Substance();
    }
}
