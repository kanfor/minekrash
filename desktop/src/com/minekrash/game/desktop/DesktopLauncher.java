package com.minekrash.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.minekrash.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.vSyncEnabled = true;
		config.resizable = true;
		config.title = "MINE KRASH";
		config.foregroundFPS = 60;
		config.width = 480;
		config.height = 800;
		new LwjglApplication(new Main(), config);     
	}
}
