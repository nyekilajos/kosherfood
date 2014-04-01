package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class KosherSurfaceProvider implements Provider<KosherSurface> {

	@Inject
	Context context;

	@Override
	public KosherSurface get() {
		Log.d("DI", "KosherSurface provider function called.");
		if (context == null)
			Log.d("DI", "context not injected in KosherSurfaceProvider");
		return new KosherSurface(context);
	}

}
