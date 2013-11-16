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

	public KosherDbObj getKosherDbObj() {
		return new KosherDbObj(true, "Here comes the info about this!");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Food> getFoodsInPlate() {
		return foodsInPlate;
	}

	public void setFoodsInPlate(List<Food> foodsInPlate) {
		this.foodsInPlate = foodsInPlate;
	}

	
}
