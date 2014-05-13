package hu.bme.aut.amorg.nyekilajos.kosherfood.core.scheduled;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.drawable.Plate;


public class ScheduledTaskNewPlate extends ScheduledTask implements Runnable {

	private Plate plate;

	private float destinationX, destinationY;

	private int directionX, directionY;

	private Object sync = new Object();
	
	public ScheduledTaskNewPlate()
	{
		//Log.d("Task Scheduler", "ScheduledTaskNewPlate created");
	}

	public ScheduledTaskNewPlate setPlate(Plate plate) {
		this.plate = plate;
		destinationX = plate.getX();
		destinationY = plate.getY();

		plate.initCoordinates();

		if (destinationX - plate.getX() < 0)
			directionX = -1;
		else if (destinationX - plate.getX() > 0)
			directionX = 1;
		else
			directionX = 0;

		if (destinationY - plate.getY() < 0)
			directionY = -1;
		else if (destinationY - plate.getY() > 0)
			directionY = 1;
		else
			directionY = 0;

		//Log.d("Task Scheduler", "Plate set");
		return this;
	}

	@Override
	public void run() {
		if (plate == null)
			throw new NullPointerException(
					"public void setPlate(Plate plate) function must be called before scheduling this task.");

		synchronized (sync) {
			if (directionX == 1) {
				if (destinationX > plate.getX()) {
					plate.moveRelative(1, 0);
				}
				else
					directionX = 0;
			} else {
				//Log.d("Task Scheduler", "destX,actualX: " + Float.toString(destinationX) + Float.toString(plate.getX()));
				if (destinationX < plate.getX()) {
					plate.moveRelative(-1, 0);
				} else
					directionX = 0;
			}
			if (directionY == 1) {
				if (destinationY > plate.getY()) {
					plate.moveRelative(0, 1);
				} else
					directionY = 0;
			} else {
				if (destinationY < plate.getY()) {
					plate.moveRelative(0, -1);
				} else
					directionY = 0;
			}
		}

	}

	@Override
	public boolean isFinished() {
		synchronized (sync) {
			if (directionX == 0 && directionY == 0) {
				return true;
			}
		}
		//Log.d("Task Scheduler", "ScheduledTask finished");
		return false;
	}

}
