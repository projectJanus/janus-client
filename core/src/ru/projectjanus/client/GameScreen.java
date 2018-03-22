package ru.projectjanus.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.projectjanus.client.ui.CheckBox;
import ru.projectjanus.client.ui.MyTouchPad;
import ru.projectjanus.client.player.Player;
import ru.projectjanus.client.visual.VisualData;
import ru.projectjanus.client.world.World;

/**
 * Created by raultaylor.
 */
public class GameScreen extends Base2DScreen {
    private TextureAtlas atlas;
    private World world;
    private Player player;
    private VisualController visualController;
    private VisualData visualData;
    private MyTouchPad myTouchPad;
    private CheckBox checkBox1;
    private CheckBox checkBox2;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        this.init();
        super.show();
    }

    private void init() {
        atlas = new TextureAtlas("solarisAtlas.tpack");
        visualData = new VisualData();

        world = new World(visualData);
        player = new Player();
        player.set(new Vector2(0, 0), 10, 1.0f, "player");
        player.setSpeed(25f);

        world.addPlayer(player);

        visualController = new VisualController(visualData);

        myTouchPad = new MyTouchPad(MyTouchPad.OUTSIDE_STYLE);
        myTouchPad.setLink(player);

        checkBox1 = new CheckBox(CheckBox.CIRCLE_TYPE);
        checkBox1.setSize(0.05f);
        checkBox1.setTop(0.3f);
        checkBox2 = new CheckBox(CheckBox.RECTANGLE_TYPE);
        checkBox2.setSize(0.05f);
        checkBox2.setBottom(-0.3f);

        camera.addGuiVisualObject(myTouchPad);
        camera.addGuiVisualObject(checkBox1);
        camera.addGuiVisualObject(checkBox2);
    }

    @Override
    public void render(float delta) {

        world.updateWorld();
        player.update(delta);
        world.getNewData();

        //visualController.updateObjects();
        visualController.addOnCamera(camera);
        camera.setTarget(visualController.findAndGetPlayer());
        //System.out.println(visualController.findAndGetPlayer());
        super.render(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        myTouchPad.touchDown(screenX, screenY, pointer);
        checkBox2.touchDown(screenX, screenY, pointer);
        checkBox1.touchDown(screenX, screenY, pointer);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        myTouchPad.touchUp(screenX, screenY, pointer);
        checkBox2.touchUp(screenX, screenY, pointer);
        checkBox1.touchUp(screenX, screenY, pointer);
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        myTouchPad.touchDragged(screenX, screenY, pointer);
        return super.touchDragged(screenX, screenY, pointer);
    }
}
