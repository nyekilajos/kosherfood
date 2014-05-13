package hu.bme.aut.amorg.nyekilajos.kosherfood.core.control;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.drawable.Food;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.drawable.Plate;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.KosherDbHelper;
import roboguice.RoboGuice;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class KosherFoodModelOnePlate extends KosherFoodModel {

	public KosherFoodModelOnePlate(Context context) {
		super(context);
	}

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
					100,
					50,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.pig,
							opts), 120, 120));

			foods.add(new Food(
					2,
					"carrot",
					300,
					50,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.bug,
							opts), 120, 120));

			foods.add(new Food(
					3,
					"milk",
					500,
					50,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.milk,
							opts), 120, 120));

			foods.add(new Food(
					4,
					"chicken",
					700,
					125,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.chicken,
							opts), 120, 120));

			foods.add(new Food(
					5,
					"egg",
					100,
					200,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.egg,
							opts), 120, 120));

			foods.add(new Food(
					6,
					"fish",
					300,
					200,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.fish,
							opts), 120, 120));

			foods.add(new Food(
					7,
					"goose",
					500,
					200,
					BitmapFactory.decodeResource(
							context.getResources(),
							hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.goose,
							opts), 120, 120));
		}

		Bitmap platePicture = BitmapFactory.decodeResource(
				context.getResources(),
				hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.plate, opts);

		synchronized (plates) {
			plates.add(new Plate(14, surfaceSize.getSurfaceWidth() / 2,
					surfaceSize.getSurfaceHeight() + 80, platePicture, 280,
					200, context));
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
	}

}
