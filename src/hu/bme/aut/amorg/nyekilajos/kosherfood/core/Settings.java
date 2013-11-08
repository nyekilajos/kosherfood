package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

public class Settings {
	private int maxGameDuration;
	private int playerNum;
	private String language;
	private boolean enableSounds;

	public Settings(int _maxGameDuration, int _playerNum, String _language,
			boolean _enableSounds) {
		maxGameDuration = _maxGameDuration;
		playerNum = _playerNum;
		language = _language;
		enableSounds = _enableSounds;
	}

	public int getMaxGameDuration() {
		return maxGameDuration;
	}

	public void setMaxGameDuration(int maxGameDuration) {
		this.maxGameDuration = maxGameDuration;
	}

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
