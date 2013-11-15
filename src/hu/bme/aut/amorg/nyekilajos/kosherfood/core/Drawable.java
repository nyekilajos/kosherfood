package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Drawable {

	protected Bitmap picture;
	protected float x;
	protected float y;
	protected float width;
	protected float height;

	public Bitmap getPiture() {
		return picture;
	}

	public void setPiture(Bitmap piture) {
		this.picture = piture;
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

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public RectF getRectF() {
		return new RectF(x - width / 2, y - height / 2, x + width / 2, y
				+ height / 2);
	}

	public Rect getRect() {
		return new Rect((int) (x - width / 2), (int) (y - height / 2),
				(int) (x + width / 2), (int) (y + height / 2));
	}

	public void doDraw(Canvas canvas) {
		canvas.drawBitmap(picture, null, getRectF(), null);
	}

}
