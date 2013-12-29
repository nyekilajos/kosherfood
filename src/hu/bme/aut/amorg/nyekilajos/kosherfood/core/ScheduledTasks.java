package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.util.Log;

public class ScheduledTasks implements Runnable {

	private int actionType;

	private KosherGame kosherGame;
	private Plate plate;

	public static final int ACTION_IDLE = 0;
	public static final int ACTION_INIT_PLATES = 1;
	public static final int ACTION_NEW_PLATE = 2;

	private int phase;

	/**
	 * This constructor is used with action ACTION_INIT_PLATES
	 * 
	 * @param _actionType
	 *            The values of this parameter is defined in static members of
	 *            this class.
	 * @param _kosherGame
	 */
	public ScheduledTasks(int _actionType, KosherGame _kosherGame) {
		actionType = _actionType;
		kosherGame = _kosherGame;
		phase = 0;
		Log.d("SCHEDULED", "CONSTRUCTOR KOSHERGAME");
	}

	public ScheduledTasks(int _actionType, Plate _plate) {
		actionType = _actionType;
		plate = _plate;
		Log.d("SCHEDULED", "CONSTRUCTOR PLATE");
	}

	public void NewPlateAction(Plate _plate) {
		actionType = ACTION_NEW_PLATE;
		phase = 0;
		plate = _plate;
		Log.d("SCHEDULED", "NEW PLATE ACTION");
	}

	public boolean IsIdle() {
		if (actionType == ACTION_IDLE)
			return true;
		return false;
	}

	@Override
	public void run() {
		switch (actionType) {

		case ACTION_INIT_PLATES:
			switch (phase) {
			case 0:
				plate = kosherGame.getPlates().get(0);
				if (plate.getX() < plate.getWidth() / 2)
					plate.setX(plate.getX() + 1);
				else
					phase++;
				break;
			case 1:
				plate = kosherGame.getPlates().get(1);
				if (plate.getY() < plate.getHeight() / 2)
					plate.setY(plate.getY() + 1);
				else
					phase++;
				break;
			case 2:
				plate = kosherGame.getPlates().get(2);
				if (plate.getX() > kosherGame.getKosherSurface().getWidth()
						- plate.getWidth() / 2)
					plate.setX(plate.getX() - 1);
				else
					phase++;
				break;
			case 3:
				plate = kosherGame.getPlates().get(3);
				if (plate.getY() > kosherGame.getKosherSurface().getHeight()
						- plate.getHeight() / 2)
					plate.setY(plate.getY() - 1);
				else {
					phase = 0;
					actionType = ACTION_IDLE;
				}
				break;
			}
			break;

		case ACTION_NEW_PLATE:
			switch (plate.getId()) {
			case 11:
				if (plate.getX() < plate.getWidth() / 2) {
					plate.setX(plate.getX() + 1);
					// Log.d("NEWPLATE", "11 moving");
				} else {
					actionType = ACTION_IDLE;
					Log.d("NEWPLATE", "11 finished");
				}
				break;
			case 12:
				if (plate.getY() < plate.getHeight() / 2) {
					plate.setY(plate.getY() + 1);
					// Log.d("NEWPLATE", "12 moving");
				} else {
					actionType = ACTION_IDLE;
					Log.d("NEWPLATE", "12 finished");
				}
				break;
			case 13:
				if (plate.getX() > kosherGame.getKosherSurface().getWidth()
						- plate.getWidth() / 2) {
					plate.setX(plate.getX() - 1);
					// Log.d("NEWPLATE", "13 moving");
				} else {
					actionType = ACTION_IDLE;
					Log.d("NEWPLATE", "13 finished");
				}
				break;
			case 14:
				if (plate.getY() > kosherGame.getKosherSurface().getHeight()
						- plate.getHeight() / 2) {
					plate.setY(plate.getY() - 1);
					// Log.d("NEWPLATE", "14 moving");
				} else {
					actionType = ACTION_IDLE;
					Log.d("NEWPLATE", "14 finished");
				}
				break;
			}
			break;
		}

	}

}
