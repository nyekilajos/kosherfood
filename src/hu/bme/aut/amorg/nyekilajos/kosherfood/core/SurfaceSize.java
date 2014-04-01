package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import roboguice.inject.ContextSingleton;
import android.util.Log;

@ContextSingleton
public class SurfaceSize {
	private int surfaceWidth;
	private int surfaceHeight;

	public SurfaceSize() {
		Log.d("DI", "SurfaceSize creation started...");
		Log.d("DI", "SurfaceSize created!");
	}

	public void setSurfaceWidth(int surfaceWidth) {
		this.surfaceWidth = surfaceWidth;
	}

	public void setSurfaceHeight(int surfaceHeight) {
		this.surfaceHeight = surfaceHeight;
	}

	public int getSurfaceWidth() {
		return surfaceWidth;
	}

	public int getSurfaceHeight() {
		return surfaceHeight;
	}
}
