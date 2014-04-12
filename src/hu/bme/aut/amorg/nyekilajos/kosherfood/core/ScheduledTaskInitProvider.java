package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ScheduledTaskInitProvider implements Provider<ScheduledTaskInit>{

	@Inject
	private Settings settings;
	
	@Inject
	private KosherFoodModel kosherFoodModel;
	
	@Inject
	private SurfaceSize surfaceSize;
	
	@Override
	public ScheduledTaskInit get() {
		switch (settings.getPlayerNum()) {
		case 1:
			return new ScheduledTaskInitOnePlate();
		default:
			return new ScheduledTaskInitFourPlate(kosherFoodModel, surfaceSize);
		}
	}

}
