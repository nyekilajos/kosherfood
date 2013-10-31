package hu.bme.aut.amorg.nyekilajos.kosherfood.components;

public class Position {
	private float x;
	private float y;

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
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

	public Position move(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

}
