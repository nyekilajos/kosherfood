package hu.bme.aut.amorg.nyekilajos.kosherfood.core.control;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.drawable.Food;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.drawable.Plate;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.KosherDbHelper;
import roboguice.RoboGuice;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class KosherFoodModelFourPlate extends KosherFoodModel {

	public KosherFoodModelFourPlate(Context context) {
		super(context);
	}

	/**
	 * This method should be called from an AsyncTask
	 */
	@Override
	public void initGame() {
		initDrawableObjects();
		initSounds();

		RoboGuice.getInjector(context).getInstance(KosherDbHelper.class)
				.initDatabase();
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
					"bug",
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
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.newplate, 0));
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s01, 0));
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s02, 0));
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s03, 0));
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s04, 0));
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s05, 0));
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s06, 0));
		soundIDs.add(soundPool.load(context,
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.raw.s07, 0));
	}
}
