package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import hu.bme.aut.amorg.nyekilajos.kosherfood.activities.KosherSurfaceActivity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class KosherSurface extends SurfaceView implements
		SurfaceHolder.Callback, OnTouchListener {

	private KosherGame kosherGame;
	private GameThread gameThread;
	private KosherSurfaceActivity kosherSurfaceActivity;

	private SurfaceHolder holder;

	public KosherSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		kosherSurfaceActivity = (KosherSurfaceActivity) context;
		holder = getHolder();
		holder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		kosherGame = new KosherGame(this, kosherSurfaceActivity);
		gameThread = new GameThread(holder, kosherGame);
	}

	public void startThread() {
		gameThread.start();
	}

	// TODO Is it working really?
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;

		gameThread.stopRunning();
		while (retry) {
			try {
				gameThread.join();
				retry = false;
			} catch (InterruptedException e) {

			} finally {
				kosherGame.freeResources();
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		kosherGame.onTouch(v, event);
		return true;
	}

}
