package ru.projectjanus.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.projectjanus.client.math.Rect;
import ru.projectjanus.client.ui.GuiHandler;

public class Base2DScreen implements Screen, InputProcessor {
    protected Game game;
    protected Rect worldBounds; // границы проекции мировых координат
    protected Matrix4 worldToGl;
    protected Matrix3 screenToWorld;
    protected Camera camera = new Camera();
    private Rect glBounds; // дефолтные границы проекции мир - gl
    private Rect screenBounds; // границы области рисования в пикселях
    private final Vector2 touch = new Vector2();

    public Base2DScreen(Game game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode=" + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode=" + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character=" + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        //System.out.println("touchDown X=" + touch.x + " Y=" + touch.y);
        // touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        //System.out.println("touchUp X=" + touch.x + " Y=" + touch.y);
        // touchUp(touch, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        //System.out.println("touchDragged X=" + touch.x + " Y=" + touch.y);
        //touchDragged(touch, pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0, 0, 1f, 1f);
        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
//        if (batch != null) {
//            throw new RuntimeException("batch != null, повторная установка screen без dispose");
//        }
//        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //System.out.println("GDX deltaTime: "+delta*1000);

        camera.render();
    }

    @Override
    public void resize(int width, int height) {
        camera.setVisualZone(width, height);
        GuiHandler.updateMatrix(width, height);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        camera.dispose();
        camera = null;
    }

    protected void touchDown(Vector2 touch, int pointer) {

    }

    protected void touchDragged(Vector2 touch, int pointer) {

    }

    protected void touchUp(Vector2 touch, int pointer) {

    }
}
