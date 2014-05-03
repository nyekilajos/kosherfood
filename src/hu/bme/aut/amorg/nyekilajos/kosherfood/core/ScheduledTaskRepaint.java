package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import roboguice.RoboGuice;
import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.google.inject.Inject;

@ContextSingleton
public class ScheduledTaskRepaint extends ScheduledTask implements Runnable{

	@Inject
	private KosherFoodModel kosherFoodModel;
	
	private SurfaceHolder holder;

	private boolean running = false;

	@Inject
	public ScheduledTaskRepaint(Context context) {
		//Log.d("DI", "ScheduledTaskRepaint creation started...");
		RoboGuice.getInjector(context).injectMembers(this);
		//Log.d("DI", "ScheduledTaskRepaint created!");
	}

	public ScheduledTaskRepaint setHolder(SurfaceHolder _holder) {
		holder = _holder;
		running = true;
		return this;
	}

	@Override
	public void run() {
		while (running) {
			Canvas canvas = null;

			try {
				canvas = this.holder.lockCanvas(null);
				synchronized (this.holder) {
					kosherFoodModel.doDraw(canvas);
				}

			} finally {

				if (canvas != null) {
					this.holder.unlockCanvasAndPost(canvas);

				}
			}
		}

	}
	
	@Override
	public boolean isFinished() {
		return false;
	}

}
