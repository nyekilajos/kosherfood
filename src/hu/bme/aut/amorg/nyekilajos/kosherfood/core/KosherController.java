package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import roboguice.RoboGuice;
import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

import com.google.inject.Inject;

@ContextSingleton
public class KosherController {

	@Inject
	private KosherFoodModel kosherFoodModel;

	private Context context;

	private SurfaceHolder holder;

	@Inject
	private TaskScheduler taskScheduler;

	private Food actualFood;
	private Plate actualPlate;

	@Inject
	public KosherController(Context context) {
		//Log.d("DI", "KosherController creation started...");
		//if (context == null)
		//	Log.d("DI", "Context null");
		this.context = context;
		kosherFoodModel = RoboGuice.getInjector(context).getInstance(
				KosherFoodModel.class);
		taskScheduler = RoboGuice.getInjector(context).getInstance(
				TaskScheduler.class);
		//Log.d("DI", "KosherController created!");
	}

	public KosherController setHolder(SurfaceHolder _holder) {
		holder = _holder;
		return this;
	}

	public void startGame() {
		if (holder == null)
			throw new NullPointerException();

		taskScheduler.add(RoboGuice.getInjector(context)
				.getInstance(ScheduledTaskRepaint.class).setHolder(holder));
		taskScheduler.add(RoboGuice.getInjector(context).getInstance(
				ScheduledTaskInit.class));

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
			taskScheduler.add(RoboGuice.getInjector(context)
					.getInstance(ScheduledTaskNewPlate.class).setPlate(plate));
		}
		kosherFoodModel.RemoveFoodFromPlate(plate);
	}

	public void destroyGame() {

		if (actualFood != null)
			actualFood.freeResources();

		kosherFoodModel.destroyModel();

		taskScheduler.shutDown();

	}

}
