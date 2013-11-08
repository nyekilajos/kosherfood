package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	private KosherSurface kosherSurface;
	private SurfaceHolder holder;
	private Canvas canvas;
	
	public GameThread(KosherSurface _kosherSurface, SurfaceHolder _holder, Canvas _canvas)
	{
		kosherSurface = _kosherSurface;
		holder = _holder;
		canvas = _canvas;
	}
	

}
