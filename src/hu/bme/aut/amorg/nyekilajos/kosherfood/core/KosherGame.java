package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import java.util.ArrayList;
import java.util.List;

import android.R.drawable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

public class KosherGame {
	
	private Settings settings;
	private KosherSurface kosherSurface;
	private List<Food> foods;
	private List<Plate> plates;

	private Bitmap background;
	
	public KosherGame(Settings _settings, KosherSurface _surface)
	{
		settings = _settings;
		kosherSurface = _surface;
		foods = new ArrayList<Food>();
		plates =new ArrayList<Plate>();
		
		background = BitmapFactory.decodeResource(kosherSurface.getResources(), hu.bme.aut.amorg.nyekilajos.kosherfood.R.drawable.background);
	}
	
	public void doDraw(Canvas canvas)
	{
		canvas.drawBitmap(background, null, new RectF(0,kosherSurface.getWidth(),0,kosherSurface.getHeight()), null);
	
		for (Food food : foods) {
			food.doDraw(canvas);
		}
		
		for (Plate plate : plates) {
			plate.doDraw(canvas);
		}
		
	}

}
