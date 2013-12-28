package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import hu.bme.aut.amorg.nyekilajos.kosherfood.activities.KosherSurfaceActivity;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.Foods;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.FoodsDataSource;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.NotKosherPairs;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.NotKosherPairsDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class KosherGame {

	private KosherSurface kosherSurface;
	private KosherSurfaceActivity kosherSurfaceActivity;
	private List<Food> foods;
	private List<Plate> plates;
	private List<Integer> soundIDs;

	private SoundPool soundPool;

	private ScheduledExecutorService scheduledExec;
	private List<ScheduledTasks> scheduledTasks;
	private static long PERIODIC_DELAY = 20;

	private Bitmap background = null;

	private Food actualFood;
	private Plate actualPlate;

	public KosherGame(KosherSurface _surface,
			KosherSurfaceActivity _kosherSurfaceActivity) {
		kosherSurface = _surface;
		kosherSurfaceActivity = _kosherSurfaceActivity;
		foods = new ArrayList<Food>();
		plates = new ArrayList<Plate>();
		soundIDs = new ArrayList<Integer>();
		scheduledTasks = new ArrayList<ScheduledTasks>();

		new InitGameAsync(this).execute();

	}

	public void initGame() {
		initDrawableObjects();
		initSounds();
		initDatabase();
	}

	private void initDrawableObjects() {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inDither = false;
		opts.inPurgeable = true;
		opts.inTempStorage = new byte[32 * 1024];

		background = BitmapFactory
				.decodeResource(
						kosherSurface.getResources(),
						hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.background_pngpng,
						opts);

		synchronized (foods) {
			foods.add(new Food(
					1,
					"pig",
					300,
					300,
					BitmapFactory.decodeResource(
							kosherSurface.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.pig,
							opts), 50, 50));

			foods.add(new Food(
					2,
					"carrot",
					300,
					200,
					BitmapFactory.decodeResource(
							kosherSurface.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.carrot,
							opts), 50, 50));

			foods.add(new Food(
					3,
					"milk",
					500,
					200,
					BitmapFactory.decodeResource(
							kosherSurface.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.milk,
							opts), 50, 50));
		}

		Bitmap platePicture = BitmapFactory.decodeResource(
				kosherSurface.getResources(),
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.plate, opts);

		synchronized (plates) {
			plates.add(new Plate(11, -90, kosherSurface.getHeight() / 2,
					platePicture, 180, 160, this));
			plates.add(new Plate(12, kosherSurface.getWidth() / 2, -80,
					platePicture, 180, 160, this));
			plates.add(new Plate(13, kosherSurface.getWidth() + 90,
					kosherSurface.getHeight() / 2, platePicture, 180, 160, this));
			plates.add(new Plate(14, kosherSurface.getWidth() / 2,
					kosherSurface.getHeight() + 80, platePicture, 180, 160,
					this));
		}

	}

	private void initSounds() {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		soundIDs.add(soundPool.load(kosherSurfaceActivity,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.newplate, 0));
		soundIDs.add(soundPool.load(kosherSurfaceActivity,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s01, 0));
		soundIDs.add(soundPool.load(kosherSurfaceActivity,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s02, 0));
		soundIDs.add(soundPool.load(kosherSurfaceActivity,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s03, 0));
	}

	private void initDatabase() {
		FoodsDataSource foodsDataSource = new FoodsDataSource(
				kosherSurfaceActivity);
		foodsDataSource.open();
		foodsDataSource.truncateFoods();

		Foods food = new Foods();

		food.set_id(1);
		food.setName("pig");
		food.setIs_kosher(0);
		food.setInformation("Jews must not eat pork!");
		foodsDataSource.insert(food);

		food.set_id(2);
		food.setName("carrot");
		food.setIs_kosher(1);
		food.setInformation("Jews can eat carrots!");
		foodsDataSource.insert(food);

		food.set_id(3);
		food.setName("milk");
		food.setIs_kosher(1);
		food.setInformation("Jews can drink milk!");
		foodsDataSource.insert(food);

		foodsDataSource.close();

		NotKosherPairsDataSource notKosherPairsDataSource = new NotKosherPairsDataSource(
				kosherSurfaceActivity);
		notKosherPairsDataSource.open();
		notKosherPairsDataSource.truncateNotKosherPairs();

		NotKosherPairs notKosherPairs = new NotKosherPairs();

		notKosherPairs.setFood_first_id(2);
		notKosherPairs.setFood_second_id(3);
		notKosherPairs
				.setInformation("Jews must not eat carrot and drink milk together!");
		notKosherPairsDataSource.insert(notKosherPairs);

		notKosherPairsDataSource.close();
	}

	public void setProgressDialog(String message) {
		kosherSurfaceActivity.showProgressDialog(message);
	}

	public void dismissProgressDialog() {
		kosherSurfaceActivity.dismissProgressDialog();
	}

	public void startGame() {
		kosherSurface.startThread();
		scheduledExec = Executors.newScheduledThreadPool(4);
		scheduledTasks.add(new ScheduledTasks(
				ScheduledTasks.ACTION_INIT_PLATES, this));
		scheduledExec.scheduleWithFixedDelay(scheduledTasks.get(0), 0,
				PERIODIC_DELAY, TimeUnit.MILLISECONDS);
	}

	public void onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// Log.d("ONTOUCH", "ACTION_DOWN");
			actualFood = selectTouchableFood(event.getX(), event.getY());
			Plate plate = selectTouchablePlate(event.getX(), event.getY());
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
				actualPlate = selectPlate();
				if (actualPlate != null)
					PutFoodToPlate();
			}
			break;

		}
	}

	private Food selectTouchableFood(float x, float y) {
		synchronized (foods) {
			for (Food food : foods) {
				if (food.getRectF().contains(x, y))
					return food;
			}
		}

		return null;
	}

	private Plate selectTouchablePlate(float x, float y) {
		synchronized (plates) {
			for (Plate plate : plates) {
				if (plate.getRectF().contains(x, y))
					return plate;
			}
		}

		return null;
	}

	private Plate selectPlate() {
		synchronized (plates) {
			for (Plate plate : plates) {
				if (RectF.intersects(actualFood.getRectF(), plate.getRectF()))
					return plate;
			}
		}

		return null;
	}

	private void PutFoodToPlate() {
		actualPlate.addFoodToPlate(actualFood);
		soundPool.play(soundIDs.get(actualFood.getId()), 1, 1, 0, 0, 1);
		synchronized (foods) {
			foods.remove(actualFood);
		}
		actualFood = null;
	}

	private void RemoveFoodFromPlate(Plate plate) {
		List<Food> removed = plate.removeFoodsFromPlate();
		if (!removed.isEmpty() && removed != null)
			synchronized (foods) {
				for (Food food : removed) {
					food.initCoordinates();
					foods.add(food);
				}
			}
		soundPool.play(soundIDs.get(0), 1, 1, 0, 0, 1);
		plate.initCoordinates();
		synchronized (scheduledTasks) {

			boolean foundIdleThread = false;
			for (ScheduledTasks scheduled : scheduledTasks) {
				if (scheduled.IsIdle() && foundIdleThread == false) {
					Log.d("SCHEDULED", "IDLE");
					scheduled.NewPlateAction(plate);
					foundIdleThread = true;
					break;
				}
				Log.d("SCHEDULED", "NOT_IDLE");
			}
			if (foundIdleThread == false) {
				ScheduledTasks tempSch = new ScheduledTasks(
						ScheduledTasks.ACTION_NEW_PLATE, plate);
				scheduledTasks.add(tempSch);
				scheduledExec.scheduleWithFixedDelay(tempSch, 0,
						PERIODIC_DELAY, TimeUnit.MILLISECONDS);
				Log.d("SCHEDULED", "NEW");
			}
		}
	}

	public KosherSurfaceActivity getKosherSurfaceActivity() {
		return kosherSurfaceActivity;
	}

	public KosherSurface getKosherSurface() {
		return kosherSurface;
	}

	public List<Food> getFoods() {
		return foods;
	}

	public List<Plate> getPlates() {
		return plates;
	}

	public void doDraw(Canvas canvas) {
		if (canvas == null)
			return;
		if (background != null)
			canvas.drawBitmap(
					background,
					null,
					new Rect(0, 0, kosherSurface.getWidth(), kosherSurface
							.getHeight()), null);

		synchronized (plates) {
			for (Plate plate : plates) {
				plate.doDraw(canvas);
			}
		}

		synchronized (foods) {
			for (Food food : foods) {
				food.doDraw(canvas);
			}
		}

	}

	public void freeResources() {
		if (actualFood != null)
			actualFood.freeResources();
		if (foods != null)
			synchronized (foods) {
				for (Food food : foods) {
					food.freeResources();
				}
			}

		if (plates != null)
			synchronized (plates) {
				for (Plate plate : plates) {
					plate.freeResources();
				}
			}

		if (soundPool != null)
			soundPool.release();

		if (scheduledExec != null)
			scheduledExec.shutdownNow();

		background.recycle();
	}
}
