package hu.bme.aut.amorg.nyekilajos.kosherfood.core.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Drawable {

	protected Bitmap picture;
	protected float x;
	protected float y;
	protected float init_x;
	protected float init_y;
	protected float width;
	protected float height;

	public void initCoordinates()
	{
		x = init_x;
		y = init_y;
	}
	
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
		if (picture != null)
			canvas.drawBitmap(picture, null, getRectF(), null);
	}

	public void freeResources() {
		if (picture != null)
			picture.recycle();
	}
	
	public void moveRelative(float dx, float dy)
	{
		synchronized (this) {
			this.x+=dx;
		}
		synchronized (this) {
			this.y+=dy;	
		}			
	}

}
