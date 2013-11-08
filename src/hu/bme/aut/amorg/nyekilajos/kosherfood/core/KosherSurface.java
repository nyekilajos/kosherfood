package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class KosherSurface extends SurfaceView implements SurfaceHolder.Callback {

	private KosherGame kosherGame;
	private Settings settings;
	private GameThread gameThread;
	
	
	
	public KosherSurface(Context context, Settings settings) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	

}
