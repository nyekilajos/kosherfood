package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import com.google.inject.Singleton;

@Singleton
public class Settings {

	private int playerNum;
	private String language;
	private boolean enableSounds;

	public static final String PREFERENCES_FILE_NAME = "KosherSettings";
	public static final String PLAYER_NUM_FIELD = "PlayerNum";
	public static final String SOUND_ENABLED_FIELD = "SoundEnabled";

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isEnableSounds() {
		return enableSounds;
	}

	public void setEnableSounds(boolean enableSounds) {
		this.enableSounds = enableSounds;
	}

}
