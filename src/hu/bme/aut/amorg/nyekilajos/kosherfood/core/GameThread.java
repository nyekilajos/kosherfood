package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import roboguice.RoboGuice;
import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.google.inject.Inject;

@ContextSingleton
public class GameThread extends Thread {

	@Inject
	private KosherFoodModel kosherFoodModel;
	
	private SurfaceHolder holder;

	private boolean running = false;

	@Inject
	public GameThread(Context context) {
		Log.d("DI", "GameThread creation started...");
		RoboGuice.getInjector(context).injectMembers(this);
		Log.d("DI", "Gamethread created!");
	}

	public void setHolder(SurfaceHolder _holder) {
		holder = _holder;
		running = true;
	}

	@Override
	public void run() {
		while (running) {
			Canvas canvas = null;

			try {
				canvas = this.holder.lockCanvas(null);
				synchronized (this.holder) {
					kosherFoodModel.doDraw(canvas);
					Thread.sleep(10);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				if (canvas != null) {
					this.holder.unlockCanvasAndPost(canvas);

				}
			}
		}

	}

	public void stopRunning() {
		running = false;
	}

}
