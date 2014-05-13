package hu.bme.aut.amorg.nyekilajos.kosherfood.core.guice;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.Settings;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.control.KosherFoodModel;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.control.KosherFoodModelFourPlate;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.control.KosherFoodModelOnePlate;
import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class KosherFoodModelProvider implements Provider<KosherFoodModel>{

	@Inject
	private Settings settings;
	
	@Inject
	private Context context;
	
	@Override
	public KosherFoodModel get() {
		switch (settings.getPlayerNum()) {
		case 1:
			return new KosherFoodModelOnePlate(context);
		default:
			return new KosherFoodModelFourPlate(context);
		}
	}

}
