package hu.bme.aut.amorg.nyekilajos.kosherfood.activities;

import com.google.inject.Inject;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.KosherSurface;
import roboguice.activity.RoboActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

public class KosherSurfaceActivity extends RoboActivity {

	@Inject
	private KosherSurface kosherSurface;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("DI", "KosherSurfaceActivity created");
		kosherSurface.setOnTouchListener(kosherSurface);
		setContentView(this.kosherSurface);
	}

	public void showProgressDialog(String message) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(message);
		progressDialog.show();
	}

	public void dismissProgressDialog() {
		if (progressDialog != null)
			progressDialog.dismiss();
	}

}
