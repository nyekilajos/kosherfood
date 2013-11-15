package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class Plate extends Drawable {

	private int id;
	private List<Food> foodsInPlate;

	public Plate(int _id, float init_x, float init_y, Bitmap _picture) {
		id = _id;
		x = init_x;
		y = init_y;
		picture = _picture;

		foodsInPlate = new ArrayList<Food>();
	}

	public KosherDbObj getKosherDbObj() {
		return new KosherDbObj(true, "Here comes the info about this!");
	}

}
