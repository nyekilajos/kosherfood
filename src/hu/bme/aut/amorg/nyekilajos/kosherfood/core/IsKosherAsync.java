package hu.bme.aut.amorg.nyekilajos.kosherfood.core;

import android.os.AsyncTask;

public class IsKosherAsync extends AsyncTask<Void, Void, Void>{

	Plate plate;
	KosherDbObj kosherDbObj;
	
	public IsKosherAsync(Plate _plate)
	{
		plate = _plate;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		plate.postDbResultCallback(kosherDbObj);
		super.onPostExecute(result);
	}

	@Override
	protected Void doInBackground(Void... params) {
		kosherDbObj = plate.searchDatabase();
		return null;
	}
	

}
