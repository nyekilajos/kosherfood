package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import roboguice.RoboGuice;
import roboguice.inject.ContextSingleton;
import roboguice.util.RoboAsyncTask;
import android.app.ProgressDialog;
import android.content.Context;

import com.google.inject.Inject;

@ContextSingleton
public class InitGameAsync extends RoboAsyncTask<Void> {

	@Inject
	private KosherFoodModel kosherFoodModel;

	@Inject
	private KosherController kosherController;

	private ProgressDialog progressDialog;

	@Inject
	public InitGameAsync(Context context) {
		super(context);
		//Log.d("DI", "InitGameAsync creation started...");
		RoboGuice.getInjector(context).injectMembers(this);
		progressDialog = new ProgressDialog(context);
		//Log.d("DI", "InitGameAsync created");

	}

	@Override
	public void onPreExecute() throws Exception {
		if (progressDialog != null) {
			progressDialog.setCancelable(false);
			progressDialog.setMessage("Loading game... Please wait!");
			progressDialog.show();
		}
		super.onPreExecute();
	}

	@Override
	public Void call() throws Exception {
		kosherFoodModel.initGame();
		return null;
	}

	@Override
	public void onFinally() throws RuntimeException {
		if (progressDialog != null)
			progressDialog.dismiss();
		kosherController.startGame();
		super.onFinally();
	}
}
