package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class InitGameAsyncProvider implements Provider<InitGameAsync> {

	@Inject
	private Context context;

	@Override
	public InitGameAsync get() {
		Log.d("DI", "InitGameAsync provider function called");
		return new InitGameAsync(context);
	}

}
