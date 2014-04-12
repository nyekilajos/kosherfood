package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import com.google.inject.Inject;

public class ScheduledTaskInitFourPlate extends ScheduledTaskInit implements
		Runnable {

	private KosherFoodModel kosherFoodModel;

	private SurfaceSize surfaceSize;

	private Plate plate;

	private int phase;

	private boolean isFinished;

	@Inject
	public ScheduledTaskInitFourPlate(KosherFoodModel kosherFoodModel,
			SurfaceSize surfaceSize) {
		this.kosherFoodModel = kosherFoodModel;
		this.surfaceSize = surfaceSize;
		isFinished = false;
		phase = 0;
	}

	@Override
	public void run() {
		switch (phase) {
		case 0:
			plate = kosherFoodModel.getPlates().get(0);
			if (plate.getX() < plate.getWidth() / 2)
				plate.setX(plate.getX() + 4);
			else
				phase++;
			break;
		case 1:
			plate = kosherFoodModel.getPlates().get(1);
			if (plate.getY() < plate.getHeight() / 2)
				plate.setY(plate.getY() + 4);
			else
				phase++;
			break;
		case 2:
			plate = kosherFoodModel.getPlates().get(2);
			if (plate.getX() > surfaceSize.getSurfaceWidth() - plate.getWidth()
					/ 2)
				plate.setX(plate.getX() - 4);
			else
				phase++;
			break;
		case 3:
			plate = kosherFoodModel.getPlates().get(3);
			if (plate.getY() > surfaceSize.getSurfaceHeight()
					- plate.getHeight() / 2)
				plate.setY(plate.getY() - 4);
			else {
				phase = 0;
				isFinished = true;
			}
			break;
		}
		super.run();
	}

	@Override
	public boolean isFinished() {
		return isFinished;
	}

}
