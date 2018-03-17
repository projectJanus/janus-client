package ru.projectjanus.client.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.projectjanus.client.Movable;
import ru.projectjanus.client.math.Rect;

/**
 * Created by raultaylor.
 */

public class MyTouchPad extends Rect implements GuiVisualObject {


    public static final byte ONBOARD_STYLE = 1;
    public static final byte INSIDE_STYLE = 2;
    public static final byte OUTSIDE_STYLE = 3;

    //NUMBERS OF FIX DIRECTIONS
    public static final byte FIX_4_POSITIONS = 1;
    public static final byte FIX_8_POSITIONS = 2;
    public static final byte NO_FIX_POSITIONS = 3;
    public static final byte FIX_360_POSITIONS = 4;
    public static final byte FIX_180_POSITIONS = 5;
    public static final byte FIX_90_POSITIONS = 6;

    // ONLY FOR LINEAR FORCE CONTROL
    public static final byte STEP_5_FORCE_CONTROL = 1;
    public static final byte STEP_10_FORCE_CONTROL = 2;
    public static final byte STEP_25_FORCE_CONTROL = 3;
    public static final byte NO_STEP_FORCE_CONTROL = 4;

    public static final byte LINEAR_FORCE_CONTROL = 1;
    public static final byte PROGRESSIVE_FORCE_CONTROL = 2;
    public static final byte OFF_FORCE_CONTROL = 3;

    public static final byte RESET_DIRECTION = 1;
    public static final byte SAFE_DIRECTION = 2;

    public static final byte FIX_ON_SCREEN = 1;
    public static final byte ON_CLICK_SCREEN = 2;


    //Default parameters:

    private final float FULL_SIZE = 0.15f;
    private final float DEAD_ZONE = 0.2f;
    private final float SIZE_CENTER = 0.5f;
    private final Color zoneBorderColor = new Color(1f, 1f, 1f, 0.15f);
    private final Color zoneFillColor = new Color(1f, 1f, 1f, 0.15f);
    private final Color centerBorderColor = new Color(1f, 0f, 0f, 0.55f);
    private final Color centerFillColor = new Color(1f, 0f, 0f, 0.25f);

    private Movable link = null;

    public TouchSprite center;
    public TouchSprite zoneTouch;

    private float deadZone;
    private float sizeCenter;
    private byte forceControle;
    private byte onScreen;
    private byte fixPosition;
    private byte safeDirection;
    private byte stepForce;

    private boolean hide = true;
    private byte currentStyle;
    private float maxOffsetCenter;

    private int actualPointer;
    private int[] pointers = new int[2];

    private Vector2[] pointerDirection = new Vector2[2];
    private Vector2 currentDirection = new Vector2();

    private float force;
    private Vector2 currentPosCenter = new Vector2();


    private class TouchSprite {
        private TextureRegion myTexture;
        private float size;
        private Vector2 pos;


        TouchSprite(TextureRegion texture) {
            this.myTexture = texture;
            this.pos = new Vector2();

        }

        TouchSprite(Texture texture) {
            this.myTexture = new TextureRegion(texture);
            this.pos = new Vector2();
        }

        public void setPos(Vector2 pos) {
            this.pos.set(pos);
        }

        public void setSize(float size) {
            this.size = size;
        }

        public Vector2 getPos() {
            return pos;
        }

        private float getHalfSize() {
            return size / 2;
        }

        public void draw(SpriteBatch batch) {
            batch.draw(myTexture, pos.x - getHalfSize(), pos.y - getHalfSize(), size, size);
        }

        public void setMyTexture(TextureRegion texture) {
            this.myTexture = texture;
        }
    }


    private void init() {
        deadZone = DEAD_ZONE;
        sizeCenter = SIZE_CENTER;
        forceControle = LINEAR_FORCE_CONTROL;
        fixPosition = FIX_90_POSITIONS;
        onScreen = ON_CLICK_SCREEN;
        safeDirection = SAFE_DIRECTION;
        stepForce = STEP_10_FORCE_CONTROL;
        pointers[0] = -1;
        pointers[1] = -1;
        actualPointer = -1;
        pointerDirection[0] = new Vector2();
        pointerDirection[1] = new Vector2();
        this.setSize(FULL_SIZE);
    }

    public MyTouchPad(TextureRegion zoneTexture, TextureRegion centerTexture, byte styleType) {
        center = new TouchSprite(centerTexture);
        zoneTouch = new TouchSprite(zoneTexture);
        setStyle(styleType);
        init();
    }


    public MyTouchPad(byte styleType) {
        center = new TouchSprite(generateTexture(centerBorderColor, centerFillColor, 512));
        zoneTouch = new TouchSprite(generateTexture(zoneBorderColor, zoneFillColor, 256));
        setStyle(styleType);
        init();
    }

    public void setPositonsType(byte positonsType) {
        this.fixPosition = positonsType;
    }


    public void setLink(Movable link) {
        this.link = link;
    }

    private void controleLink() {
        if (link != null) {
            link.move(getDirection());
        }
    }


    private Texture generateTexture(Color borderColor, Color fillColor, int size) {

        int border = (size / 100) * 2;
        int halfSize = size / 2;
        Pixmap pm = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        pm.setBlending(Pixmap.Blending.SourceOver);
        pm.setColor(0, 0, 0, 0);
        pm.fill();
        pm.setBlending(Pixmap.Blending.None);
        pm.setColor(borderColor);
        pm.fillCircle(halfSize, halfSize, halfSize);
        pm.setColor(fillColor);
        pm.fillCircle(halfSize, halfSize, halfSize - border);
        Texture tex = new Texture(pm);
        pm.dispose();
        return tex;
    }


    public void setSize(float size) {
        center.setSize(size * SIZE_CENTER);
        zoneTouch.setSize(size);
        super.setSize(size, size);
        setStyle(currentStyle);
    }

    public void touchDown(int x, int y, int pointer) {
        if (actualPointer == -1) {
            actualPointer = 0;
            hide = false;
            pointers[actualPointer] = pointer;
            switch (safeDirection) {
                case SAFE_DIRECTION:
                    if (pointerDirection[actualPointer].set(GuiHandler.getGuiPosition(x, y)).len() > maxOffsetCenter) {
                        center.pos.set(pointerDirection[actualPointer]);
                        this.pos.set(pointerDirection[actualPointer]).nor();
                        this.pos.scl(pointerDirection[actualPointer].len() - maxOffsetCenter);
                        pointerDirection[actualPointer].nor();
                        zoneTouch.pos.set(this.pos);
                        force = 1f;
                        break;
                    }
                case RESET_DIRECTION:
                    this.pos.set(GuiHandler.getGuiPosition(x, y));
                    zoneTouch.pos.set(this.pos);
                    center.pos.set(this.pos);
                    pointerDirection[actualPointer].set(0, 0);
                    force = 0f;
            }
            controleLink();
        } else if (actualPointer == 0) {
            actualPointer = 1;
            pointers[actualPointer] = pointer;
            pointerDirection[actualPointer].set(GuiHandler.getGuiPosition(x, y));
            pointerDirection[actualPointer].sub(this.pos);
            setForce();
            setDirection();
            dragCenter();
        }


    }

    public void touchUp(int x, int y, int pointer) {
        if (pointers[0] == pointer) {
            if (actualPointer == 0) {
                pointers[0] = -1;
                switch (safeDirection) {
                    case RESET_DIRECTION:
                        pointerDirection[actualPointer].set(0, 0);
                        force = 0f;
                        break;
                    case SAFE_DIRECTION:
                        break;
                }
                actualPointer = -1;
                hide = true;
                controleLink();
            }
            if (actualPointer == 1) {
                actualPointer = 0;
                pointerDirection[0].set(pointerDirection[1]);
                pointers[0] = pointers[1];
                pointers[1] = -1;
                pointerDirection[1].set(0, 0);
                setForce();
                setDirection();
                dragCenter();
                controleLink();
            }
        }
        if(pointers[1] == pointer){
            pointerDirection[1].set(0,0);
            actualPointer = 0;
            setForce();
            setDirection();
            dragCenter();
            controleLink();
        }
    }

    public void setStyle(byte styleType) {
        currentStyle = styleType;
        switch (styleType) {
            case ONBOARD_STYLE:
                maxOffsetCenter = this.getHalfHeight();
                break;
            case INSIDE_STYLE:
                maxOffsetCenter = this.getHalfHeight() - center.getHalfSize();
                break;
            case OUTSIDE_STYLE:
                maxOffsetCenter = this.getHalfHeight() + center.getHalfSize();
        }
    }


    private void setForce() {
        switch (forceControle) {
            case OFF_FORCE_CONTROL:
                if (pointerDirection[actualPointer].len() > deadZone * this.getHalfHeight()) {
                    force = 1f;
                    pointerDirection[actualPointer].nor();
                } else {
                    force = 0;
                    pointerDirection[actualPointer].set(0, 0);
                }
                break;
            case LINEAR_FORCE_CONTROL:
                if (pointerDirection[actualPointer].len() > deadZone * this.getHalfHeight()) {
                    if (pointerDirection[actualPointer].len() < maxOffsetCenter) {
                        force = (pointerDirection[actualPointer].len() - deadZone * this.getHalfHeight()) / (maxOffsetCenter - deadZone * this.getHalfHeight());
                        switch (stepForce) {
                            case NO_STEP_FORCE_CONTROL:
                                break;
                            case STEP_5_FORCE_CONTROL:
                                force = (((int) (force * 100)) / 5) * 0.05f;
                                break;
                            case STEP_10_FORCE_CONTROL:
                                force = (((int) (force * 100)) / 10) * 0.10f;
                                break;
                            case STEP_25_FORCE_CONTROL:
                                force = (((int) (force * 100)) / 25) * 0.25f;
                                break;
                        }
                        //System.out.println("Force: " + force);
                    } else {
                        force = 1f;
                        pointerDirection[actualPointer].nor();
                    }
                } else {
                    force = 0;
                    pointerDirection[actualPointer].set(0, 0);
                }
                break;
            case PROGRESSIVE_FORCE_CONTROL:
                if (pointerDirection[actualPointer].len() < maxOffsetCenter) {
                    force = (float) Math.pow((pointerDirection[actualPointer].len() / maxOffsetCenter), 2);
                } else {
                    force = 1f;
                    pointerDirection[actualPointer].nor();
                }
                break;
        }
    }

    private void setDirection() {
        int angle = (int) pointerDirection[actualPointer].angle();
        switch (fixPosition) {
            case FIX_4_POSITIONS:
                angle = (((angle / 45 + 1) % 8) / 2) * 90;
                pointerDirection[actualPointer].setAngle(angle);
                break;
            case FIX_8_POSITIONS:
                angle = (((int) (angle / 22.5f + 1) % 16) / 2) * 45;
                pointerDirection[actualPointer].setAngle(angle);
                break;
            case FIX_180_POSITIONS:
                angle = (((angle + 1) % 360) / 2) * 2;
                pointerDirection[actualPointer].setAngle(angle);
                break;
            case FIX_90_POSITIONS:
                angle = (((angle / 2 + 1) % 180) / 2) * 4;
                pointerDirection[actualPointer].setAngle(angle);
                break;
            case FIX_360_POSITIONS:
                pointerDirection[actualPointer].setAngle(angle);
                break;
            case NO_FIX_POSITIONS:
                break;
        }

    }

    private void dragCenter() {
        if (force < 1f) {
            currentPosCenter.set(this.pos).add(pointerDirection[actualPointer]);
            center.pos.set(this.currentPosCenter);
        } else {
            currentPosCenter.set(this.pos).mulAdd(pointerDirection[actualPointer], maxOffsetCenter);
            center.pos.set(this.currentPosCenter);
        }

    }

    public void touchDragged(int x, int y, int pointer) {
        for (int i = 0; i < pointers.length; i++) {
            if (pointers[i] == pointer) {
                pointerDirection[actualPointer].set(GuiHandler.getGuiPosition(x, y));
                pointerDirection[actualPointer].sub(this.pos);
                if (i == actualPointer) {
                    setForce();
                    setDirection();
                    dragCenter();
                    controleLink();
                }
            }
        }

//            if (pointerDirection.len() > DEAD_ZONE * this.getHalfHeight()) {
//                pointerDirection.nor();
//                currentPosCenter.set(this.pos).mulAdd(pointerDirection, maxOffsetCenter);
//                center.pos.set(this.currentPosCenter);
//            } else {
//                pointerDirection.set(0, 0);
//                center.pos.set(this.pos);
//            }

    }

    public Vector2 getDirection() {
        if(actualPointer == 1){
            currentDirection.set(pointerDirection[1]);
        } else {
            currentDirection.set(pointerDirection[0]);
        }
        return currentDirection.nor().scl(force);
    }


    @Override
    public void draw(SpriteBatch batch) {
        if (!hide) {
            zoneTouch.draw(batch);
            //System.out.println("draw TouchPad_Zone: "+ zone.pos + " size: " + zone.getHeight()+ " : " + zone.getWidth());
            center.draw(batch);
            //System.out.println("draw TouchPad_Center: "+ center.pos + " size: " + center.getHeight()+ " : " + center.getWidth());
        }
    }


    @Override
    public Rect getRect() {
        return this;
    }
}
