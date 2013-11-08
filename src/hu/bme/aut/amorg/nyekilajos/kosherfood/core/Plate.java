package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import java.util.ArrayList;
import java.util.List;

public class Plate {

	private int id;
	private float x;
	private float y;
	private List<Food> foodsInPlate;

	public Plate(int _id, float init_x, float init_y) {
		id = _id;
		x = init_x;
		y = init_y;

		foodsInPlate = new ArrayList<Food>();
	}

	public KosherDbObj getKosherDbObj() {
		return new KosherDbObj(true, "Here comes the info about this!");
	}

}
