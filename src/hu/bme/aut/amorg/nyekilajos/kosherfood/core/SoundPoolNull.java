package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import java.io.FileDescriptor;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.SoundPool;

public class SoundPoolNull extends SoundPool{

	public SoundPoolNull(int maxStreams, int streamType, int srcQuality) {
		super(maxStreams, streamType, srcQuality);
	}

	@Override
	public int load(AssetFileDescriptor afd, int priority) {
		return 0;
	}

	@Override
	public int load(Context context, int resId, int priority) {
		return 0;
	}

	@Override
	public int load(FileDescriptor fd, long offset, long length, int priority) {
		return 0;
	}

	@Override
	public int load(String path, int priority) {
		
		return 0;
	}

	@Override
	public void setOnLoadCompleteListener(OnLoadCompleteListener listener) {
	}

}
