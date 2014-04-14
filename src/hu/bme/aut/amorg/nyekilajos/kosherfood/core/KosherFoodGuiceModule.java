package hu.bme.aut.amorg.nyekilajos.kosherfood.core;
import roboguice.inject.ContextSingleton;
import android.media.SoundPool;

import com.google.inject.AbstractModule;

public class KosherFoodGuiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(KosherFoodModel.class).toProvider(KosherFoodModelProvider.class).in(ContextSingleton.class);
		bind(ScheduledTaskInit.class).toProvider(ScheduledTaskInitProvider.class);
		bind(SoundPool.class).toProvider(SoundPoolProvider.class).in(ContextSingleton.class);
	}

}
