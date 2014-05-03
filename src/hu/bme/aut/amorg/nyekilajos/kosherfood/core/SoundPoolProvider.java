package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.media.AudioManager;
import android.media.SoundPool;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SoundPoolProvider implements Provider<SoundPool> {

	@Inject
	private Settings settings;

	@Override
	public SoundPool get() {
		if (settings.isEnableSounds()) {
			//Log.d("Sound", "Enabled");
			return new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		}
		//Log.d("Sound", "Disabled");
		return new SoundPoolNull(0, AudioManager.STREAM_MUSIC, 0);
	}

}
