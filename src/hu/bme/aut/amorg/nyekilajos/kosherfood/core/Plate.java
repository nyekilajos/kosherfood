package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class Plate extends Drawable {

	private int id;
	private List<Food> foodsInPlate;

	public Plate(int _id, float init_x, float init_y, Bitmap _picture, float _width, float _height) {
		id = _id;
		x = init_x;
		y = init_y;
		width = _width;
		height = _height;
		picture = _picture;

		foodsInPlate = new ArrayList<Food>();
	}

	/**Call only from background thread
	 * 
	 * IsKosherAsync (AsyncTask) calls it in doInBackground function.**/
	public KosherDbObj searchDatabase()
	{
		return null;
		
	}
	
	/**IsKosherAsync (AsyncTask) calls it in onPostExecute function when searchDatabase function is ready.
	 * 
	 * This function is called in the original thread**/
	public void postDbResultCallback(KosherDbObj kosherDbObj)
	{
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void addFoodToPlate(Food food)
	{
		foodsInPlate.add(food);
		IsKosherAsync isKosherAsync = new IsKosherAsync(this);
		isKosherAsync.execute();
	}
	
	public List<Food> removeFoodsFromPlate()
	{
		List<Food> removed = new ArrayList<Food>(foodsInPlate);
		foodsInPlate.clear();
		return removed;
	}

	
}
