package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import java.util.ArrayList;
import java.util.List;

public class KosherGame {
	
	private Settings settings;
	private List<Food> foods;
	private List<Plate> plates;
	
	public KosherGame(Settings _settings)
	{
		settings = _settings;
		
		foods = new ArrayList<Food>();
		plates =new ArrayList<Plate>();
	}

}
