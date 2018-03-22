package ru.projectjanus.client.math;

import com.badlogic.gdx.math.Vector2;

/**
 * Прямоугольник
 */
public class Rect {
    public final Vector2 pos = new Vector2(); // позиция по центру
    protected float halfWidth; // половина ширины
    protected float halfHeight; // половина высоты

    public Rect() {

    }

    public Rect(Rect from) {
        this(from.pos.x, from.pos.y, from.getHalfWidth(), from.getHalfHeight());
    }

    public Rect(float x, float y, float halfWidth, float halfHeight) {
        pos.set(x, y);
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    @Override
    public String toString() {
        return "Rectangle: pos" + pos + " size(" + getWidth() + ", " + getHeight() + ")";
    }

    public float getWidth() {
        return halfWidth * 2f;
    }

    public float getHeight() {
        return halfHeight * 2f;
    }

    public void setHeight(float height) {
        this.halfHeight = height / 2f;
    }

    public void setWidth(float width) {
        this.halfWidth = width / 2f;
    }

    public boolean isInside(Rect src) {
        return (getRight() > src.getLeft()) &&
                (getLeft() < src.getRight()) &&
                (getBottom() < src.getTop()) &&
                (getTop() > src.getBottom());
    }

    public float getRight() {
        return pos.x + halfWidth;
    }

    public float getLeft() {
        return pos.x - halfWidth;
    }

    public float getBottom() {
        return pos.y - halfHeight;
    }

    public float getTop() {
        return pos.y + halfHeight;
    }

    public void setTop(float top) {
        pos.y = top - halfHeight;
    }

    public void setBottom(float bottom) {
        pos.y = bottom + halfHeight;
    }

    public void setLeft(float left) {
        pos.x = left + halfWidth;
    }

    public void setRight(float right) {
        pos.x = right - halfWidth;
    }

    public boolean isMe(Vector2 touch) {
        return touch.x >= getLeft() && touch.x <= getRight() && touch.y >= getBottom() && touch.y <= getTop();
    }

    public boolean isOutside(Rect other) {
        return getLeft() > other.getRight() || getRight() < other.getLeft() || getBottom() > other.getTop() || getTop() < other.getBottom();
    }

    public void set(Rect from) {
        pos.set(from.pos);
        halfWidth = from.halfWidth;
        halfHeight = from.halfHeight;
    }

    public void setSize(float width, float height) {
        this.halfWidth = width / 2f;
        this.halfHeight = height / 2f;
    }
}