package hu.bme.aut.amorg.nyekilajos.kosherfood.core.scheduled;

import java.util.concurrent.ScheduledFuture;

public class ScheduledItem {

	private ScheduledTask scheduledTask;
	private ScheduledFuture<ScheduledTask> scheduledFuture;

	public ScheduledItem(ScheduledTask scheduledTask,
			ScheduledFuture<ScheduledTask> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
		this.scheduledTask = scheduledTask;
	}

	public ScheduledTask getScheduledTask() {
		return scheduledTask;
	}

	public ScheduledFuture<ScheduledTask> getScheduledFuture() {
		return scheduledFuture;
	}	
}
