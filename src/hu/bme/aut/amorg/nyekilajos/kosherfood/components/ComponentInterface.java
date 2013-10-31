package hu.bme.aut.amorg.nyekilajos.kosherfood.components;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

public interface ComponentInterface {
	
	public void setPicture(Bitmap bitmap);
	public Bitmap getPicture();
	
	public Position getPosition();
	public void setPosition(Position position);
	
	public void moveRelative(float x, float y);
	
	public RectF getDisplayRectF();
	public Rect getDisplayRect();
	
	public RectF getTouchRectF();
	public Rect getTouchRect();

}
