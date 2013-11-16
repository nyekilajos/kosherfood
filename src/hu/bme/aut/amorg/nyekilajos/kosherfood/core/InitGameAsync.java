package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.os.AsyncTask;

public class InitGameAsync extends AsyncTask<Void, Void, Void> {

	private KosherGame kosherGame;

	public InitGameAsync(KosherGame kosherGame) {
		super();
		this.kosherGame = kosherGame;
	}

	@Override
	protected void onPreExecute() {
		kosherGame.setProgressDialog("Loading game... Please wait!");
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		kosherGame.initGame();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		kosherGame.dismissProgressDialog();
		kosherGame.startGame();
		super.onPostExecute(result);
	}

}
