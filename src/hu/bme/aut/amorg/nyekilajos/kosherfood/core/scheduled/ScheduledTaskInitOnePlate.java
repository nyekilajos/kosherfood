package hu.bme.aut.amorg.nyekilajos.kosherfood.core.scheduled;

import com.google.inject.Inject;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.SurfaceSize;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.control.KosherFoodModel;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.drawable.Plate;

public class ScheduledTaskInitOnePlate extends ScheduledTaskInit implements
		Runnable {

	private KosherFoodModel kosherFoodModel;

	private SurfaceSize surfaceSize;

	private Plate plate;

	private boolean isFinished;
	
	@Inject
	public ScheduledTaskInitOnePlate(KosherFoodModel kosherFoodModel, SurfaceSize surfaceSize)
	{
		this.kosherFoodModel = kosherFoodModel;
		this.surfaceSize = surfaceSize;
		isFinished = false;
	}
	
	
	@Override
	public void run() {
		plate = kosherFoodModel.getPlates().get(0);
		if (plate.getY() > surfaceSize.getSurfaceHeight()
				- plate.getHeight() / 2)
			plate.setY(plate.getY() - 4);
		else {
			isFinished = true;
		}
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

}
