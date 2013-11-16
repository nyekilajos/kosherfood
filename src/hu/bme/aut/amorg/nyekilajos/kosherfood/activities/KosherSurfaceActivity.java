package hu.bme.aut.amorg.nyekilajos.kosherfood.activities;

import hu.bme.aut.amorg.nyekilajos.kosherfood.core.KosherSurface;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

public class KosherSurfaceActivity extends Activity {
	
	private KosherSurface kosherSurface;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		kosherSurface = new KosherSurface(this, null, 0);
		kosherSurface.setOnTouchListener(kosherSurface);
		setContentView(this.kosherSurface);
	}
	
	public void showProgressDialog(String message)
	{
		progressDialog= new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(message);
		progressDialog.show();
	}
	
	public void dismissProgressDialog()
	{
		if(progressDialog != null)
			progressDialog.dismiss();
	}


}
