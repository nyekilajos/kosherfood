package hu.bme.aut.amorg.nyekilajos.kosherfood.activities;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.KosherSurface;
import android.app.Activity;
import android.os.Bundle;

public class KosherSurfaceActivity extends Activity {
	
	private KosherSurface kosherSurface;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		kosherSurface = new KosherSurface(this, null);
		setContentView(this.kosherSurface);
	}


}
