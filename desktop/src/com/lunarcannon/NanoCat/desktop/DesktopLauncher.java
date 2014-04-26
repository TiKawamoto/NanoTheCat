package com.lunarcannon.NanoCat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lunarcannon.NanoCat.NanoCat;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new NanoCat(), config);
		
		config.width = 960;
		config.height = 540;
	}
}
