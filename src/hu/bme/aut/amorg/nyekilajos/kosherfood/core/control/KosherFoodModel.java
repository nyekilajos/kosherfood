package hu.bme.aut.amorg.nyekilajos.kosherfood.core.control;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.SurfaceSize;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.drawable.Food;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.drawable.Plate;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.SoundPool;

import com.google.inject.Inject;

public abstract class KosherFoodModel {

	protected Context context;

	@Inject
	protected SurfaceSize surfaceSize;

	@Inject
	protected SoundPool soundPool;

	protected List<Integer> soundIDs;
	protected List<Food> foods;
	protected List<Plate> plates;

	protected Bitmap background = null;

	@Inject
	public KosherFoodModel(Context context) {
		//Log.d("DI", "KosherFoodModel creation started...");
		this.context = context;
		RoboGuice.getInjector(context).injectMembers(this);
		soundIDs = new ArrayList<Integer>();
		foods = new ArrayList<Food>();
		plates = new ArrayList<Plate>();
		//Log.d("DI", "KosherFoodModel created!");
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
	public abstract void initGame();

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
		if (!plate.isKosher())
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
