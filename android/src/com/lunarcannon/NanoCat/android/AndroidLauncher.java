package com.lunarcannon.NanoCat.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.lunarcannon.NanoCat.NanoCat;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new NanoCat(), config);
		
		config.useImmersiveMode = true;
		config.useGLSurfaceView20API18 = false;
	}
}
