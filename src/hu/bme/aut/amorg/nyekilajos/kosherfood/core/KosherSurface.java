package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.control.InitGameAsync;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.control.KosherController;
import roboguice.RoboGuice;
import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.google.inject.Inject;

@ContextSingleton
public class KosherSurface extends SurfaceView implements
		SurfaceHolder.Callback, OnTouchListener {

	@Inject
	private KosherController kosherController;

	@Inject
	private InitGameAsync initGameAsync;

	@Inject
	private SurfaceSize surfaceSize;

	private SurfaceHolder holder;

	@Inject
	public KosherSurface(Context context) {
		super(context);
		//Log.d("DI", "KosherSurface creation started...");
		surfaceSize = RoboGuice.getInjector(context).getInstance(SurfaceSize.class);
		kosherController = RoboGuice.getInjector(context).getInstance(KosherController.class);
		initGameAsync = RoboGuice.getInjector(context).getInstance(InitGameAsync.class);
		holder = getHolder();
		holder.addCallback(this);
		//Log.d("DI", "KosherSurface created!");
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		surfaceSize.setSurfaceWidth(this.getWidth());
		surfaceSize.setSurfaceHeight(this.getHeight());
		kosherController.setHolder(holder);
		initGameAsync.execute();
	}

	// TODO Is it working really?
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		kosherController.destroyGame();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		kosherController.onTouch(v, event);
		return true;
	}

}
