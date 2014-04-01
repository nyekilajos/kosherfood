package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import roboguice.RoboGuice;
import roboguice.util.RoboAsyncTask;
import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;

public class InitGameAsync extends RoboAsyncTask<Void> {

	@Inject
	private KosherFoodModel kosherFoodModel;
	
	@Inject
	private KosherController kosherController;

	@Inject
	protected InitGameAsync(Context context) {
		super(context);
		Log.d("DI", "InitGameAsync creation started...");
		RoboGuice.getInjector(context).injectMembers(this);
		Log.d("DI", "InitGameAsync created");

	}

	@Override
	protected void onPreExecute() throws Exception {
		kosherFoodModel.setProgressDialog("Loading game... Please wait!");
		super.onPreExecute();
	}

	@Override
	public Void call() throws Exception {
		kosherFoodModel.initGame();
		return null;
	}

	@Override
	protected void onFinally() throws RuntimeException {
		kosherFoodModel.dismissProgressDialog();
		kosherController.startGame();
		super.onFinally();
	}
}
