package hu.bme.aut.amorg.nyekilajos.kosherfood.components;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

public class Plate implements ComponentInterface {

	Bitmap picture;
	RectF ComponentRectF;

	private static final float TOUCH_TO_DISPLAY_RATIO = (float) 0.8;
	
	
	@Override
	public void setPicture(Bitmap bitmap) {
		picture = bitmap;
	}

	@Override
	public Bitmap getPicture() {
		return picture;
	}

	@Override
	public Position getPosition() {
		return new Position(ComponentRectF.centerX(), ComponentRectF.centerY());
	}

	@Override
	public void setPosition(Position position) {
		float width = ComponentRectF.width();
		float height = ComponentRectF.height();
		ComponentRectF.left = position.getX() - width/2;
		ComponentRectF.top = position.getY() - height/2;
		ComponentRectF.right = position.getX() + width/2;
		ComponentRectF.bottom = position.getY() + height/2;
	}

	@Override
	public void moveRelative(float x, float y) {
		setPosition(getPosition().move(x, y));
	}

	@Override
	public RectF getDisplayRectF() {
		return ComponentRectF;
	}

	@Override
	public Rect getDisplayRect() {
		return new Rect((int)ComponentRectF.left, (int)ComponentRectF.top, (int)ComponentRectF.right, (int)ComponentRectF.bottom);		
	}

	@Override
	public RectF getTouchRectF() {
		return new RectF(ComponentRectF.left*TOUCH_TO_DISPLAY_RATIO, 
				ComponentRectF.top*TOUCH_TO_DISPLAY_RATIO,
				ComponentRectF.right*TOUCH_TO_DISPLAY_RATIO,
				ComponentRectF.bottom*TOUCH_TO_DISPLAY_RATIO);
	}

	@Override
	public Rect getTouchRect() {
		return new Rect((int)(ComponentRectF.left*TOUCH_TO_DISPLAY_RATIO), 
				(int)(ComponentRectF.top*TOUCH_TO_DISPLAY_RATIO),
				(int)(ComponentRectF.right*TOUCH_TO_DISPLAY_RATIO),
				(int)(ComponentRectF.bottom*TOUCH_TO_DISPLAY_RATIO));
	}

}
