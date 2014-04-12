package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

public abstract class ScheduledTask implements Runnable {

	@Override
	public void run() {
		
	}

	public abstract boolean isFinished();

}
