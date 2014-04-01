package hu.bme.aut.amorg.nyekilajos.kosherfood.core;
import roboguice.inject.ContextSingleton;

import com.google.inject.AbstractModule;

public class KosherFoodModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(KosherSurface.class).toProvider(KosherSurfaceProvider.class).in(
				ContextSingleton.class);
		bind(InitGameAsync.class).toProvider(InitGameAsyncProvider.class).in(
				ContextSingleton.class);
	}

}
