package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

	private KosherGame kosherGame;
	private SurfaceHolder holder;
	private boolean running;

	public GameThread(SurfaceHolder _holder, KosherGame _kosherGame) {
		holder = _holder;
		kosherGame = _kosherGame;
		running = true;
	}

	@Override
	public void run() {
		while (running) {
			Canvas canvas = null;

			try {
				canvas = this.holder.lockCanvas(null);
				synchronized (this.holder) {
					kosherGame.doDraw(canvas);
				}
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
