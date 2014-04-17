package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import com.google.inject.Inject;

import android.content.Context;
import roboguice.util.RoboAsyncTask;

public class IsKosherAsync extends RoboAsyncTask<Void> {

	Plate plate;
	KosherDbObj kosherDbObj;

	@Inject
	public IsKosherAsync(Context context) {
		super(context);

		this.plate = null;
	}

	public IsKosherAsync setPlate(Plate _plate) {
		plate = _plate;
		return this;
	}

	@Override
	public Void call() throws Exception {
		kosherDbObj = plate.searchDatabase();
		return null;
	}

	@Override
	public void onFinally() throws RuntimeException {
		plate.postDbResultCallback(kosherDbObj);
		super.onFinally();
	}

}
