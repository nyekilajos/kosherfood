package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import hu.bme.aut.amorg.nyekilajos.kosherfood.database.Foods;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.FoodsDataSource;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.NotKosherPairs;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.NotKosherPairsDataSource;
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
		FoodsDataSource foodsDataSource = RoboGuice.getInjector(context)
				.getInstance(FoodsDataSource.class);
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

		NotKosherPairsDataSource notKosherPairsDataSource = RoboGuice
				.getInjector(context).getInstance(
						NotKosherPairsDataSource.class);
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

}
