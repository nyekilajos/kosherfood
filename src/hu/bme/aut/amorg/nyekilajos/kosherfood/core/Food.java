package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.graphics.Bitmap;

public class Food extends Drawable{

	private int id;
	private String name;

	public Food(int _id, String _name, float init_x, float init_y, Bitmap _picture) {
		id = _id;
		name = _name;
		x = init_x;
		y = init_y;
		picture = _picture;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
