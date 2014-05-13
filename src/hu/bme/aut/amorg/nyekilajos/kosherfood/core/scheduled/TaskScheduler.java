package hu.bme.aut.amorg.nyekilajos.kosherfood.core.scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class TaskScheduler implements Runnable {

	private List<ScheduledItem> scheduledTasks;

	
	private ScheduledExecutorService scheduledExec;
	private final int THREAD_NUMBER = 4;
	private final int PERIODIC_DELAY = 20;
	private final int PERIODIC_DELAY_THREAD_GC = 1000;

	public TaskScheduler() {
		//Log.d("DI", "TaskScheduler creation started...");
		scheduledExec = Executors.newScheduledThreadPool(THREAD_NUMBER);
		scheduledExec.scheduleWithFixedDelay(this, 4000, PERIODIC_DELAY_THREAD_GC,
				TimeUnit.MILLISECONDS);
		scheduledTasks = new ArrayList<ScheduledItem>();
		//Log.d("DI", "TaskScheduler created!");
	}

	@SuppressWarnings("unchecked")
	public void add(ScheduledTask scheduledTask) {
		synchronized (scheduledTasks) {
			scheduledTasks.add(new ScheduledItem(scheduledTask, (ScheduledFuture<ScheduledTask>) scheduledExec
					.scheduleAtFixedRate(scheduledTask, 0, PERIODIC_DELAY,
							TimeUnit.MILLISECONDS)));
			//Log.d("Task Scheduler", "Task added!");
		}

	}

	@Override
	public void run() {
		//Log.d("Task Scheduler", "Finished task detection...");
		synchronized (scheduledTasks) {
			//Log.d("Task Scheduler", "Num of tasks: " + Integer.toString(scheduledTasks.size()));
			for (ScheduledItem scheduledItem : scheduledTasks) {
				if (scheduledItem.getScheduledTask().isFinished())
				{
					scheduledItem.getScheduledFuture().cancel(true);
					scheduledTasks.remove(scheduledItem);
					//Log.d("Task Scheduler", "Task deleted");
				}
			}
				
			/*for (ScheduledFuture<ScheduledTask> scheduledItem : scheduledTasks) {
				try {
					if (scheduledItem.get().isFinished()) {
						scheduledItem.cancel(false);
						scheduledTasks.remove(scheduledItem);
						Log.d("Task Scheduler", "Task deleted");
					}
				} catch (InterruptedException e) {
					Log.d("Task Scheduler", "InterruptedException thrown");
					e.printStackTrace();
				} catch (ExecutionException e) {
					Log.d("Task Scheduler", "ExecutionException thrown");
					e.printStackTrace();
				} catch (Exception e) {
					Log.d("Task Scheduler", "Exception thrown");
					e.printStackTrace();
				}
			}*/
		}
		//Log.d("Task Scheduler", "Finished task detection completed!");

	}

	public void shutDown() {
		scheduledExec.shutdownNow();
	}

}
