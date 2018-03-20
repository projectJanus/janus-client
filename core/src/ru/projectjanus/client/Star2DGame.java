package ru.projectjanus.client;

import com.badlogic.gdx.Game;

public class Star2DGame extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen(this));
    }
}
