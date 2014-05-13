package hu.bme.aut.amorg.nyekilajos.kosherfood.core.drawable;

import android.graphics.Bitmap;

public class Food extends Drawable{

	private int id;
	private String name;

	public Food(int _id, String _name, float _init_x, float _init_y, Bitmap _picture, float _width, float _height) {
		id = _id;
		name = _name;
		x = _init_x;
		y = _init_y;
		init_x = _init_x;
		init_y = _init_y;
		width = _width;
		height = _height;
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
