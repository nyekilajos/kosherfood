package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import roboguice.RoboGuice;
import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.google.inject.Inject;

@ContextSingleton
public class KosherController {

	@Inject
	private GameThread gameThread;
	
	@Inject
	private KosherFoodModel kosherFoodModel;

	private Context context;
	
	private SurfaceHolder holder;

	private ScheduledExecutorService scheduledExec;
	private List<ScheduledTasks> scheduledTasksList;
	private static long PERIODIC_DELAY = 25;

	private Food actualFood;
	private Plate actualPlate;

	@Inject
	public KosherController(Context context) {
		Log.d("DI", "KosherController creation started...");
		if(context == null)
			Log.d("DI", "Context null");
		this.context = context;
		kosherFoodModel = RoboGuice.getInjector(context).getInstance(
				KosherFoodModel.class);
		gameThread= RoboGuice.getInjector(context).getInstance(GameThread.class);
		scheduledTasksList = new ArrayList<ScheduledTasks>();
		Log.d("DI", "KosherController created!");
	}

	public void startInit(SurfaceHolder _holder) {
		holder = _holder;
	}

	public void startGame() {
		gameThread.setHolder(holder);
		gameThread.start();
		scheduledExec = Executors.newScheduledThreadPool(4);
	
		ScheduledTasks scheduled = RoboGuice.getInjector(context).getInstance(ScheduledTasks.class).setAction(ScheduledTasks.ACTION_INIT_PLATES);
		scheduledTasksList.add(scheduled);
	
		scheduled = RoboGuice.getInjector(context).getInstance(ScheduledTasks.class).setAction(ScheduledTasks.ACTION_IDLE);
		scheduledTasksList.add(scheduled);
		scheduled = RoboGuice.getInjector(context).getInstance(ScheduledTasks.class).setAction(ScheduledTasks.ACTION_IDLE);
		scheduledTasksList.add(scheduled);
		scheduled = RoboGuice.getInjector(context).getInstance(ScheduledTasks.class).setAction(ScheduledTasks.ACTION_IDLE);
		scheduledTasksList.add(scheduled);
	
		
		/*
		scheduledTasks
				.add(new ScheduledTasks(ScheduledTasks.ACTION_INIT_PLATES));
		scheduledTasks.add(new ScheduledTasks(ScheduledTasks.ACTION_IDLE));
		scheduledTasks.add(new ScheduledTasks(ScheduledTasks.ACTION_IDLE));
		scheduledTasks.add(new ScheduledTasks(ScheduledTasks.ACTION_IDLE));*/
		for (ScheduledTasks scheduledItem : scheduledTasksList) {
			scheduledExec.scheduleWithFixedDelay(scheduledItem, 0, PERIODIC_DELAY,
					TimeUnit.MILLISECONDS);
		}

	}

	public void onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// Log.d("ONTOUCH", "ACTION_DOWN");
			actualFood = kosherFoodModel.selectTouchableFood(event.getX(),
					event.getY());
			Plate plate = kosherFoodModel.selectTouchablePlate(event.getX(),
					event.getY());
			if (plate != null)
				RemoveFoodFromPlate(plate);

		case MotionEvent.ACTION_MOVE:
			// Log.d("ONTOUCH", "ACTION_MOVE");
			if (actualFood != null) {
				actualFood.setX(event.getX());
				actualFood.setY(event.getY());
			}
			break;

		case MotionEvent.ACTION_UP:
			// Log.d("ONTOUCH", "ACTION_UP");
			if (actualFood != null) {
				actualPlate = kosherFoodModel.selectPlate(actualFood);
				if (actualPlate != null)
					kosherFoodModel.PutFoodToPlate(actualFood, actualPlate);
			}
			break;

		}
	}

	private void RemoveFoodFromPlate(Plate plate) {

		if (!plate.isKosher()) {
			plate.initCoordinates();

			boolean foundIdleThread = false;
			for (ScheduledTasks scheduled : scheduledTasksList) {
				if (scheduled.IsIdle() && foundIdleThread == false) {
					Log.d("SCHEDULED", "IDLE");
					scheduled.setAction(ScheduledTasks.ACTION_NEW_PLATE, plate);
					foundIdleThread = true;
					break;
				}
				Log.d("SCHEDULED", "NOT_IDLE");
			}
			if (foundIdleThread == false) {
				ScheduledTasks tempSch = RoboGuice.getInjector(context).getInstance(ScheduledTasks.class).setAction(ScheduledTasks.ACTION_NEW_PLATE, plate);
				scheduledTasksList.add(tempSch);
				scheduledExec.scheduleWithFixedDelay(tempSch, 0,
						PERIODIC_DELAY, TimeUnit.MILLISECONDS);
				Log.d("SCHEDULED", "NEW");
			}
		}
		kosherFoodModel.RemoveFoodFromPlate(plate);
	}

	public void destroyGame() {

		boolean retry = true;

		gameThread.stopRunning();
		while (retry) {
			try {
				gameThread.join();
				retry = false;
			} catch (InterruptedException e) {

			} finally {
				if (actualFood != null)
					actualFood.freeResources();

				kosherFoodModel.destroyModel();

				if (scheduledExec != null)
					scheduledExec.shutdownNow();
			}
		}

	}
}
