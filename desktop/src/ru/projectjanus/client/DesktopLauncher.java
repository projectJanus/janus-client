package ru.projectjanus.client;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.projectjanus.client.Star2DGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		float aspect = 480f/854f;
		float aspect = 4f/3f;
		config.height = 500;
		config.width = (int) (config.height * aspect);
		new LwjglApplication(new Star2DGame(), config);
	}
}
