package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import hu.bme.aut.amorg.nyekilajos.kosherfood.database.Foods;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.FoodsDataSource;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.NotKosherPairs;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.NotKosherPairsDataSource;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;

public class Plate extends Drawable {

	private int id;
	private List<Food> foodsInPlate;
	private KosherGame kosherGame;

	private KosherDbObj kosherDbObj;

	public Plate(int _id, float _init_x, float _init_y, Bitmap _picture,
			float _width, float _height, KosherGame _kosherGame) {
		id = _id;
		init_x = _init_x;
		init_y = _init_y;
		x = _init_x;
		y = _init_y;
		width = _width;
		height = _height;
		picture = _picture;
		kosherGame = _kosherGame;

		foodsInPlate = new ArrayList<Food>();

		kosherDbObj = new KosherDbObj(true, "");
	}

	/**
	 * Call only from background thread
	 * 
	 * IsKosherAsync (AsyncTask) calls it in doInBackground function.
	 **/
	public KosherDbObj searchDatabase() {
		FoodsDataSource foodsDataSource = new FoodsDataSource(
				kosherGame.getKosherSurfaceActivity());

		foodsDataSource.open();

		List<Foods> foodDbObjList = new ArrayList<Foods>();
		for (Food food : foodsInPlate) {
			foodDbObjList.add(foodsDataSource.getFood(food.getId()));
		}

		foodsDataSource.close();

		for (Foods foods : foodDbObjList) {
			if (foods.getIs_kosher() == 0)
				return new KosherDbObj(false, foods.getInformation());
		}

		NotKosherPairsDataSource notKosherPairsDataSource = new NotKosherPairsDataSource(
				kosherGame.getKosherSurfaceActivity());

		notKosherPairsDataSource.open();

		if (foodDbObjList.size() > 1) {
			for (int i = 1; i < foodDbObjList.size(); i++) {
				for (int j = 0; j < i; j++) {
					NotKosherPairs notKosherPairs = notKosherPairsDataSource
							.getNotKosherPairs(foodDbObjList.get(i).get_id(),
									foodDbObjList.get(j).get_id());
					if (notKosherPairs != null) {
						notKosherPairsDataSource.close();
						return new KosherDbObj(false,
								notKosherPairs.getInformation());
					}

				}
			}
		}

		notKosherPairsDataSource.close();
		return new KosherDbObj(true, foodDbObjList.get(0).getInformation());
	}

	/**
	 * IsKosherAsync (AsyncTask) calls it in onPostExecute function when
	 * searchDatabase function is ready.
	 * 
	 * This function is called in the original thread
	 **/
	public void postDbResultCallback(KosherDbObj _kosherDbObj) {
		kosherDbObj = _kosherDbObj;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addFoodToPlate(Food food) {
		foodsInPlate.add(food);
		IsKosherAsync isKosherAsync = new IsKosherAsync(this);
		isKosherAsync.execute();
	}

	public List<Food> removeFoodsFromPlate() {
		List<Food> removed = new ArrayList<Food>(foodsInPlate);
		foodsInPlate.clear();
		kosherDbObj.isKosher = true;
		kosherDbObj.information = "";
		return removed;
	}

	@Override
	public void doDraw(Canvas canvas) {
		super.doDraw(canvas);
		Paint paint = new Paint();
		if(kosherDbObj.isKosher)
			paint.setARGB(255,0,255,0);
		else
			paint.setARGB(255, 255, 0, 0);
		paint.setTextAlign(Align.CENTER);
		paint.setTextScaleX((float) 0.8);
		paint.setFakeBoldText(true);
		paint.setTextSize(11);
		canvas.drawText(kosherDbObj.information, x, y, paint);
	}

}
