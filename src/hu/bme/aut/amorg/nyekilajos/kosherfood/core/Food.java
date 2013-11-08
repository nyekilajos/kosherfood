package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

public class Food {

	private int id;
	private String name;
	private float x;
	private float y;

	public Food(int _id, String _name, float init_x, float init_y) {
		id = _id;
		name = _name;
		x = init_x;
		y = init_y;
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

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
