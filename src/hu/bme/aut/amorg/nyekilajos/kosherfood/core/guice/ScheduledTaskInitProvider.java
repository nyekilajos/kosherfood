package hu.bme.aut.amorg.nyekilajos.kosherfood.core.guice;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.Settings;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.SurfaceSize;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.control.KosherFoodModel;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.scheduled.ScheduledTaskInit;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.scheduled.ScheduledTaskInitFourPlate;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.scheduled.ScheduledTaskInitOnePlate;

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
