package ru.projectjanus.client.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.projectjanus.client.math.Rect;

/**
 * Created by raultaylor.
 */

public class CheckBox extends Rect implements GuiVisualObject{

    public static final byte CIRCLE_TYPE = 1;
    public static final byte RECTANGLE_TYPE = 2;


    private CheckBoxSprite bodySprite;
    private CheckBoxSprite fillSprite;

    private final Color BODY_FILL_COLOR = new Color(0.8f,0.8f,0.8f,1f);
    private final Color BODY_BORDER_COLOR = new Color(0.4f,0.4f,0.4f,1f);
    private final Color FILL_COLOR = new Color(0.7f,0f,0f,1f);

    private byte bodyType;
    private boolean status = false;
    private int actualPointer = -1;

    private class CheckBoxSprite {
        private Vector2 pos;
        private float size;
        private TextureRegion myTexture;

        CheckBoxSprite(TextureRegion textureRegion){
            this.myTexture = textureRegion;
            this.pos = new Vector2();
        }

        CheckBoxSprite(Texture texture){
            this.myTexture = new TextureRegion(texture);
            this.pos = new Vector2();
        }

        float getHalfSize(){
            return size/2;
        }

        void setSize(float size){
            this.size = size;
        }

        void setPos(Vector2 pos){
            this.pos.set(pos);
        }

        public void draw(SpriteBatch batch) {
            batch.draw(myTexture, pos.x - getHalfSize(), pos.y - getHalfSize(), size, size);
        }
    }

    public CheckBox(byte bodyType){
        super();
        this.bodyType = bodyType;
        createSprite();
    }

    public void setSize(float size){
        this.setSize(size, size);
        fillSprite.setSize(size);
        bodySprite.setSize(size);

    }


    private void createSprite(){
        int size = 256;
        int border = (size / 100) * 8;
        int halfSize = size / 2;
        Pixmap pm = new Pixmap(size, size, Pixmap.Format.RGBA8888);
        switch (bodyType){
            case CIRCLE_TYPE:
                pm.setBlending(Pixmap.Blending.SourceOver);
                pm.setColor(0, 0, 0, 0);
                pm.fill();
                pm.setBlending(Pixmap.Blending.None);
                pm.setColor(BODY_BORDER_COLOR);
                pm.fillCircle(halfSize, halfSize, halfSize);
                pm.setColor(BODY_FILL_COLOR);
                pm.fillCircle(halfSize, halfSize, halfSize - border);

                bodySprite = new CheckBoxSprite(new Texture(pm));

                pm.setBlending(Pixmap.Blending.SourceOver);
                pm.setColor(0, 0, 0, 0);
                pm.fill();
                border = (size/100)*16;
                pm.setBlending(Pixmap.Blending.None);
                pm.setColor(FILL_COLOR);
                pm.fillCircle(halfSize, halfSize, halfSize - border);

                fillSprite = new CheckBoxSprite(new Texture(pm));

                pm.dispose();
                break;
            case RECTANGLE_TYPE:
                pm.setBlending(Pixmap.Blending.SourceOver);
                pm.setColor(0, 0, 0, 0);
                pm.fill();
                pm.setBlending(Pixmap.Blending.None);
                pm.setColor(BODY_BORDER_COLOR);
                pm.fillRectangle(0,0,size, size);
                pm.setColor(BODY_FILL_COLOR);
                pm.fillRectangle(border,border,size-2*border,size-2*border);

                bodySprite = new CheckBoxSprite(new Texture(pm));

                pm.setBlending(Pixmap.Blending.SourceOver);
                pm.setColor(0, 0, 0, 0);
                pm.fill();
                border = (size/100)*16;
                pm.setBlending(Pixmap.Blending.None);
                pm.setColor(FILL_COLOR);
                pm.fillRectangle(border,border,size-2*border,size-2*border);

                fillSprite = new CheckBoxSprite(new Texture(pm));

                pm.dispose();
                break;
        }
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public void setPos(Vector2 pos){
        this.pos.set(pos);
        updateSprite();
    }

    @Override
    public void setLeft(float left) {
        super.setLeft(left);
        updateSprite();
    }

    @Override
    public void setTop(float top) {
        super.setTop(top);
        updateSprite();
    }

    @Override
    public void setRight(float right) {
        super.setRight(right);
        updateSprite();
    }

    @Override
    public void setBottom(float bottom) {
        super.setBottom(bottom);
        updateSprite();
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        updateSprite();
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        updateSprite();
    }

    private void updateSprite(){
        bodySprite.setSize(this.getHeight());
        bodySprite.setPos(this.pos);
        fillSprite.setSize(this.getHeight());
        fillSprite.setPos(this.pos);
    }

    public boolean getStatus(){
        return status;
    }

    @Override
    public void draw(SpriteBatch batch) {
        bodySprite.draw(batch);
        if(status){
            fillSprite.draw(batch);
        }
    }

    public boolean touchDown(int x, int y, int pointer){
        if (actualPointer == -1){
            if(this.isMe(GuiHandler.getGuiPosition(x,y))){
                actualPointer = pointer;
                return true;
            }
        }

        return false;
    }

    public boolean touchUp(int x, int y, int pointer){
        if(actualPointer == pointer){
            if(this.isMe(GuiHandler.getGuiPosition(x,y))){
                status = !status;
            }
            actualPointer = -1;
            return true;
        }
        return false;
    }


    @Override
    public Rect getRect() {
        return this;
    }
}
