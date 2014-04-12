package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.google.inject.Singleton;

import android.util.Log;

@Singleton
public class TaskScheduler implements Runnable {

	private List<ScheduledFuture<ScheduledTask>> scheduledTasks;

	private ScheduledExecutorService scheduledExec;
	private final int THREAD_NUMBER = 4;
	private final int PERIODIC_DELAY = 25;
	private final int PERIODIC_DELAY_THREAD_GC = 2000;

	public TaskScheduler() {
		scheduledExec = Executors.newScheduledThreadPool(THREAD_NUMBER);
		scheduledExec.scheduleWithFixedDelay(this, 0, PERIODIC_DELAY_THREAD_GC,
				TimeUnit.MILLISECONDS);
		scheduledTasks = new ArrayList<ScheduledFuture<ScheduledTask>>();
	}

	@SuppressWarnings("unchecked")
	public void add(ScheduledTask scheduledTask) {
		scheduledTasks.add((ScheduledFuture<ScheduledTask>) scheduledExec
				.scheduleWithFixedDelay(scheduledTask, 0, PERIODIC_DELAY,
						TimeUnit.MILLISECONDS));
	}

	@Override
	public void run() {
		for (ScheduledFuture<ScheduledTask> scheduledItem : scheduledTasks) {
			try {
				if (scheduledItem.get().isFinished()) {
					scheduledItem.cancel(true);
					scheduledTasks.remove(scheduledItem);
					Log.d("Task Scheduler", "Task deleted");

				}
			} catch (InterruptedException e) {

				e.printStackTrace();
			} catch (ExecutionException e) {

				e.printStackTrace();
			}

		}

	}

	public void shutDown() {
		scheduledExec.shutdownNow();
	}

}
