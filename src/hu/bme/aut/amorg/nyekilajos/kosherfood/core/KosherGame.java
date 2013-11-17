package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import hu.bme.aut.amorg.nyekilajos.kosherfood.activities.KosherSurfaceActivity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class KosherGame {

	private KosherSurface kosherSurface;
	private KosherSurfaceActivity kosherSurfaceActivity;
	private List<Food> foods;
	private List<Plate> plates;

	private Bitmap background = null;

	private Food actualFood;

	public KosherGame(KosherSurface _surface,
			KosherSurfaceActivity _kosherSurfaceActivity) {
		kosherSurface = _surface;
		kosherSurfaceActivity = _kosherSurfaceActivity;
		foods = new ArrayList<Food>();
		plates = new ArrayList<Plate>();

		new InitGameAsync(this).execute();

	}

	public void initGame() {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inDither = false;
		opts.inPurgeable = true;
		opts.inTempStorage = new byte[32 * 1024];

		background = BitmapFactory
				.decodeResource(
						kosherSurface.getResources(),
						hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.background_pngpng,
						opts);

		foods.add(new Food(1, "pig", 100, 200, BitmapFactory.decodeResource(
				kosherSurface.getResources(),
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.pig, opts),
				50, 50));

		foods.add(new Food(
				2,
				"carrot",
				300,
				200,
				BitmapFactory.decodeResource(
						kosherSurface.getResources(),
						hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.carrot,
						opts), 50, 50));

		foods.add(new Food(3, "milk", 500, 200, BitmapFactory.decodeResource(
				kosherSurface.getResources(),
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.milk, opts),
				50, 50));

		Bitmap platePicture = BitmapFactory.decodeResource(
				kosherSurface.getResources(),
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.plate, opts);

		plates.add(new Plate(11, 90, 80, platePicture, 180, 160));
		plates.add(new Plate(12, kosherSurface.getWidth() - 90, 80,
				platePicture, 180, 160));
		plates.add(new Plate(13, 90, kosherSurface.getHeight() - 80,
				platePicture, 180, 160));
		plates.add(new Plate(14, kosherSurface.getWidth() - 90, kosherSurface
				.getHeight() - 80, platePicture, 180, 160));
	}

	public void startGame() {
		kosherSurface.startThread();
	}

	public void setProgressDialog(String message) {
		kosherSurfaceActivity.showProgressDialog(message);
	}

	public void dismissProgressDialog() {
		kosherSurfaceActivity.dismissProgressDialog();
	}

	public void onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.d("ONTOUCH", "ACTION_DOWN");
			actualFood = selectTouchableFood(event.getX(), event.getY());
			break;

		case MotionEvent.ACTION_MOVE:
			Log.d("ONTOUCH", "ACTION_MOVE");
			if (actualFood != null) {
				actualFood.setX(event.getX());
				actualFood.setY(event.getY());
			}
			break;

		case MotionEvent.ACTION_UP:
			Log.d("ONTOUCH", "ACTION_UP");
			break;

		}
	}

	public Food selectTouchableFood(float x, float y) {
		for (Food food : foods) {
			if (food.getRectF().contains(x, y))
				return food;
		}
		return null;

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

		for (Food food : foods) {
			food.doDraw(canvas);
		}

		for (Plate plate : plates) {
			plate.doDraw(canvas);
		}

	}

	public void freeResources() {
		if (actualFood != null)
			actualFood.freeResources();
		if (foods != null)
			for (Food food : foods) {
				food.freeResources();
			}
		if (plates != null)
			for (Plate plate : plates) {
				plate.freeResources();
			}
		background.recycle();
	}
}
