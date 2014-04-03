package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import hu.bme.aut.amorg.nyekilajos.kosherfood.database.Foods;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.FoodsDataSource;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.NotKosherPairs;
import hu.bme.aut.amorg.nyekilajos.kosherfood.database.NotKosherPairsDataSource;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import com.google.inject.Inject;

public class Plate extends Drawable {

	private int id;
	private List<Food> foodsInPlate;

	private KosherDbObj kosherDbObj;

	private Context context;

	@Inject
	private IsKosherAsync isKosherAsync;

	public Plate(int _id, float _init_x, float _init_y, Bitmap _picture,
			float _width, float _height, Context context) {
		Log.d("DI", "Plate creation started...");
		RoboGuice.getInjector(context).injectMembers(this);
		this.context = context;
		id = _id;
		init_x = _init_x;
		init_y = _init_y;
		x = _init_x;
		y = _init_y;
		width = _width;
		height = _height;
		picture = _picture;

		foodsInPlate = new ArrayList<Food>();

		kosherDbObj = new KosherDbObj(true, "");

		Log.d("DI", "Plate created");
	}

	/**
	 * Call only from background thread
	 * 
	 * IsKosherAsync (AsyncTask) calls it in doInBackground function.
	 **/
	public KosherDbObj searchDatabase() {
		FoodsDataSource foodsDataSource = RoboGuice.getInjector(context).getInstance(FoodsDataSource.class);

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

		NotKosherPairsDataSource notKosherPairsDataSource = RoboGuice.getInjector(context).getInstance(NotKosherPairsDataSource.class);

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
		if(foodDbObjList.size() != 0)
			return new KosherDbObj(true, foodDbObjList.get(0).getInformation());
		else
			return new KosherDbObj(true, "");
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
		isKosherAsync.setPlate(this);
		isKosherAsync.execute();
	}

	public List<Food> removeFoodsFromPlate() {
		List<Food> removed = new ArrayList<Food>(foodsInPlate);
		foodsInPlate.clear();
		kosherDbObj.isKosher = true;
		kosherDbObj.information = "";
		return removed;
	}

	public RectF getInnerRectF() {
		return new RectF((float) (x - width * 0.3),
				(float) (y - height * 0.08), (float) (x + width * 0.1),
				(float) (y + height * 0.2));
	}

	@Override
	public void doDraw(Canvas canvas) {
		super.doDraw(canvas);

		TextPaint paint = new TextPaint();
		if (kosherDbObj.isKosher)
			paint.setARGB(255, 0, 255, 0);
		else
			paint.setARGB(255, 255, 0, 0);

		// paint.setTextAlign(Align.CENTER);
		paint.setTextScaleX((float) 0.8);
		paint.setFakeBoldText(true);
		paint.setTextSize(11);

		if (this.getX() >= 0 && this.getY() >= 0) {
			StaticLayout staticLayout = new StaticLayout(
					kosherDbObj.information, paint, (int) this.getInnerRectF()
							.width(), Layout.Alignment.ALIGN_CENTER, 1, 1,
					false);

			canvas.save();
			switch (this.id) {
			case 11:
				canvas.rotate(90, this.x, this.y);
				break;

			case 12:
				canvas.rotate(180, this.x, this.y);
				break;

			case 13:
				canvas.rotate(270, this.x, this.y);
				break;

			case 14:
				canvas.rotate(0, this.x, this.y);
				break;
			}
			canvas.translate((float) (this.getInnerRectF().left),
					(float) (this.getInnerRectF().top));
			staticLayout.draw(canvas);
			canvas.restore();
		}
	}

	public boolean isKosher() {
		return kosherDbObj.isKosher;
	}

}
