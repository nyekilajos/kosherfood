package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import hu.bme.aut.amorg.nyekilajos.kosherfood.activities.KosherSurfaceActivity;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.Foods;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.FoodsDataSource;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.NotKosherPairs;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.NotKosherPairsDataSource;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;
import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class KosherFoodModel {

	private Context context;

	@Inject
	private SurfaceSize surfaceSize;

	private SoundPool soundPool;

	private List<Integer> soundIDs;
	private List<Food> foods;
	private List<Plate> plates;

	private Bitmap background = null;

	@Inject
	public KosherFoodModel(Context context) {
		Log.d("DI", "KosherFoodModel creation started...");
		this.context = context;
		RoboGuice.getInjector(context).injectMembers(this);
		soundIDs = new ArrayList<Integer>();
		foods = new ArrayList<Food>();
		plates = new ArrayList<Plate>();
		Log.d("DI", "KosherFoodModel created!");
	}

	public List<Food> getFoods() {
		return foods;
	}

	public List<Plate> getPlates() {
		return plates;
	}

	/**
	 * This method should be called from an AsyncTask
	 */
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

		background = BitmapFactory.decodeResource(context.getResources(),
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.background,
				opts);

		synchronized (foods) {
			foods.add(new Food(
					1,
					"pig",
					300,
					200,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.pig,
							opts), 100, 100));

			foods.add(new Food(
					2,
					"carrot",
					400,
					200,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.bug,
							opts), 100, 100));

			foods.add(new Food(
					3,
					"milk",
					500,
					200,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.milk,
							opts), 100, 100));

			foods.add(new Food(
					4,
					"chicken",
					600,
					200,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.chicken,
							opts), 100, 100));

			foods.add(new Food(
					5,
					"egg",
					300,
					300,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.egg,
							opts), 100, 100));

			foods.add(new Food(
					6,
					"fish",
					400,
					300,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.fish,
							opts), 100, 100));

			foods.add(new Food(
					7,
					"goose",
					500,
					300,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.goose,
							opts), 100, 100));
		}

		Bitmap platePicture = BitmapFactory.decodeResource(
				context.getResources(),
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.plate, opts);

		Matrix matrix = new Matrix();
		matrix.postRotate(90);

		synchronized (plates) {

			Bitmap rotatedBitmap = Bitmap.createBitmap(platePicture, 0, 0,
					platePicture.getWidth(), platePicture.getHeight(), matrix,
					true);
			plates.add(new Plate(11, -80, surfaceSize.getSurfaceHeight() / 2,
					rotatedBitmap, 160, 240, context));

			matrix.postRotate(90);
			rotatedBitmap = Bitmap.createBitmap(platePicture, 0, 0,
					platePicture.getWidth(), platePicture.getHeight(), matrix,
					true);
			plates.add(new Plate(12, surfaceSize.getSurfaceWidth() / 2, -80,
					rotatedBitmap, 240, 160, context));

			matrix.postRotate(90);
			rotatedBitmap = Bitmap.createBitmap(platePicture, 0, 0,
					platePicture.getWidth(), platePicture.getHeight(), matrix,
					true);
			plates.add(new Plate(13, surfaceSize.getSurfaceWidth() + 80,
					surfaceSize.getSurfaceHeight() / 2, rotatedBitmap, 160,
					240, context));

			matrix.postRotate(90);
			rotatedBitmap = Bitmap.createBitmap(platePicture, 0, 0,
					platePicture.getWidth(), platePicture.getHeight(), matrix,
					true);
			plates.add(new Plate(14, surfaceSize.getSurfaceWidth() / 2,
					surfaceSize.getSurfaceHeight() + 80, platePicture, 240,
					160, context));
		}

	}

	private void initSounds() {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.newplate, 0));
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s01, 0));
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s02, 0));
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s03, 0));
	}

	private void initDatabase() {
		FoodsDataSource foodsDataSource = RoboGuice.getInjector(context).getInstance(FoodsDataSource.class);
		foodsDataSource.open();
		foodsDataSource.truncateFoods();

		Foods food = new Foods();

		food.set_id(1);
		food.setName("pig");
		food.setIs_kosher(0);
		food.setInformation("Jews must not eat pork!");
		foodsDataSource.insert(food);

		food.set_id(2);
		food.setName("bug");
		food.setIs_kosher(0);
		food.setInformation("Jews must not eat bug!");
		foodsDataSource.insert(food);

		food.set_id(3);
		food.setName("milk");
		food.setIs_kosher(1);
		food.setInformation("Jews can drink milk!");
		foodsDataSource.insert(food);

		food.set_id(4);
		food.setName("chicken");
		food.setIs_kosher(1);
		food.setInformation("Jews can eat chicken!");
		foodsDataSource.insert(food);

		food.set_id(5);
		food.setName("egg");
		food.setIs_kosher(1);
		food.setInformation("Jews can eat egg!");
		foodsDataSource.insert(food);

		food.set_id(6);
		food.setName("fish");
		food.setIs_kosher(1);
		food.setInformation("Jews can eat fish!");
		foodsDataSource.insert(food);

		food.set_id(7);
		food.setName("goose");
		food.setIs_kosher(0);
		food.setInformation("Jews must not eat goose!");
		foodsDataSource.insert(food);

		foodsDataSource.close();

		NotKosherPairsDataSource notKosherPairsDataSource = RoboGuice.getInjector(context).getInstance(NotKosherPairsDataSource.class);
		notKosherPairsDataSource.open();
		notKosherPairsDataSource.truncateNotKosherPairs();

		NotKosherPairs notKosherPairs = new NotKosherPairs();

		notKosherPairs.setFood_first_id(3);
		notKosherPairs.setFood_second_id(4);
		notKosherPairs
				.setInformation("Jews must not eat chicken and drink milk together!");
		notKosherPairsDataSource.insert(notKosherPairs);

		notKosherPairs.setFood_first_id(3);
		notKosherPairs.setFood_second_id(5);
		notKosherPairs
				.setInformation("Jews must not eat egg and drink milk together!");
		notKosherPairsDataSource.insert(notKosherPairs);

		notKosherPairs.setFood_first_id(3);
		notKosherPairs.setFood_second_id(6);
		notKosherPairs
				.setInformation("Jews must not eat fish and drink milk together!");
		notKosherPairsDataSource.insert(notKosherPairs);

		notKosherPairsDataSource.close();
	}

	public void setProgressDialog(String message) {
		KosherSurfaceActivity activity = (KosherSurfaceActivity) context;
		activity.showProgressDialog(message);
	}

	public void dismissProgressDialog() {
		KosherSurfaceActivity activity = (KosherSurfaceActivity) context;
		activity.dismissProgressDialog();
	}

	public Food selectTouchableFood(float x, float y) {
		synchronized (foods) {
			for (Food food : foods) {
				if (food.getRectF().contains(x, y))
					return food;
			}
		}

		return null;
	}

	public Plate selectTouchablePlate(float x, float y) {
		synchronized (plates) {
			for (Plate plate : plates) {
				if (plate.getRectF().contains(x, y))
					return plate;
			}
		}

		return null;
	}

	public Plate selectPlate(Food actualFood) {
		synchronized (plates) {
			for (Plate plate : plates) {
				if (RectF.intersects(actualFood.getRectF(), plate.getRectF()))
					return plate;
			}
		}

		return null;
	}

	public void PutFoodToPlate(Food actualFood, Plate actualPlate) {
		actualPlate.addFoodToPlate(actualFood);
		if (actualFood.getId() < soundIDs.size())
			soundPool.play(soundIDs.get(actualFood.getId()), 1, 1, 0, 0, 1);
		synchronized (foods) {
			foods.remove(actualFood);
		}
		actualFood = null;
	}

	public void RemoveFoodFromPlate(Plate plate) {
		if(!plate.isKosher())
			soundPool.play(soundIDs.get(0), 1, 1, 0, 0, 1);
		List<Food> removed = plate.removeFoodsFromPlate();
		if (!removed.isEmpty() && removed != null)
			synchronized (foods) {
				for (Food food : removed) {
					food.initCoordinates();
					foods.add(food);
				}
			}
	}

	public void doDraw(Canvas canvas) {
		if (canvas == null)
			return;
		if (background != null)
			canvas.drawBitmap(
					background,
					null,
					new Rect(0, 0, surfaceSize.getSurfaceWidth(), surfaceSize
							.getSurfaceHeight()), null);

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

	public void destroyModel() {
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

		background.recycle();
	}

}
