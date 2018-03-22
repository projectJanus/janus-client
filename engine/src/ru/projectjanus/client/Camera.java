package ru.projectjanus.client;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

import ru.projectjanus.client.math.MatrixUtils;
import ru.projectjanus.client.math.Rect;
import ru.projectjanus.client.ui.GuiVisualObject;

/**
 * Created by raultaylor.
 */
public class Camera {
    private final float SIZE_TARGET = 0.12f;
    private final Rect GL_RECT = new Rect(0, 0, 1f, 1f);
    private float scale;
    private Rect visualZone;
    private Rect currentZone;
    private Matrix4 baseMatrix;
    private Matrix4 currentMatrix;
    private Rect target;
    private List<iVisualObject> objectArrayList;
    private List<GuiVisualObject> guiVisualObjects;
    private SpriteBatch mainBatch;
    private SpriteBatch guiBatch;
    private long deltaTimer;

    public Camera() {

        target = new Rect();
        this.baseMatrix = new Matrix4();
        this.currentMatrix = new Matrix4();
        this.scale = 1f;
        this.visualZone = new Rect();
        this.currentZone = new Rect();
        mainBatch = new SpriteBatch();
        guiBatch = new SpriteBatch();
    }

    public void addGuiVisualObject(GuiVisualObject object) {
        if (guiVisualObjects == null) {
            guiVisualObjects = new ArrayList<GuiVisualObject>();
        }
        guiVisualObjects.add(object);
    }

    public void addVisualObject(iVisualObject object) {
        if (objectArrayList == null) {
            objectArrayList = new ArrayList<iVisualObject>();
        }
        objectArrayList.add(object);
    }

    public void cleanVisualObjects() {
        if (objectArrayList == null) {
            objectArrayList = new ArrayList<iVisualObject>();
        }
        objectArrayList.clear();
    }

    public void dispose() {
        mainBatch.dispose();
    }

    public void render() {

        deltaTimer = System.currentTimeMillis();

        update();

        if (objectArrayList != null && !objectArrayList.isEmpty()) {
            mainBatch.setProjectionMatrix(currentMatrix);

            //System.out.println(currentZone);

            //System.out.println("Counts VO: " + objectArrayList.size());
            mainBatch.begin();
            int i = 0;
            for (iVisualObject object : objectArrayList) {
                if (!currentZone.isOutside(object.getRect())) {
                    object.draw(mainBatch);
                    i++;
                }
            }
            mainBatch.end();
            //System.out.println("Draw object: " + i);
        }

        if (guiVisualObjects != null && !guiVisualObjects.isEmpty()) {

            guiBatch.begin();

            for (GuiVisualObject object : guiVisualObjects) {
                //if(!object.getRect().isOutside(currentZone)){
                object.draw(guiBatch);
                //}
            }
            guiBatch.end();

        }

        //System.out.println("Camera Render() time: " + (System.currentTimeMillis()-deltaTimer));

    }

    public void setVisualZone(int width, int height) {
        float aspect = (float) width / (float) height;
        visualZone.setHeight(1f);
        visualZone.setWidth(aspect * 1f);

        MatrixUtils.calcTransitionMatrix(baseMatrix, visualZone, GL_RECT);

        update();
    }

    public void update() {
        updateScale();
        updateCurrentMatrix();
        updateCurrentZone();
        mainBatch.setProjectionMatrix(baseMatrix);
        guiBatch.setProjectionMatrix(baseMatrix);
    }

    private void updateScale() {
        scale = 1 / (target.getHeight() / SIZE_TARGET);
    }

    private void updateCurrentMatrix() {
        currentMatrix.idt().set(baseMatrix);
        currentMatrix.scale(scale, scale, 0f).translate(-target.pos.x, -target.pos.y, 0f);
    }

    private void updateCurrentZone() {
        currentZone.set(visualZone);
        currentZone.pos.set(target.pos);
        currentZone.setSize(visualZone.getWidth() / scale, visualZone.getHeight() / scale);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setTarget(iVisualObject target) {
        if (target == null) {
            this.target.pos.set(0, 0);
        } else {
            this.target = target.getRect();
        }
    }

    public void setVisualObjects(List<iVisualObject> objectArrayList) {
        this.objectArrayList = objectArrayList;
    }
}
