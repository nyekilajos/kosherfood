package hu.bme.aut.amorg.nyekilajos.kosherfood.activities;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.KosherSurface;
import roboguice.activity.RoboActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.inject.Inject;

public class KosherSurfaceActivity extends RoboActivity {

	@Inject
	private KosherSurface kosherSurface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("DI", "KosherSurfaceActivity created");
		kosherSurface.setOnTouchListener(kosherSurface);
		setContentView(this.kosherSurface);
	}

}
