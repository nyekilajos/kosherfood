package hu.bme.aut.amorg.nyekilajos.kosherfood.activities;

import hu.bme.aut.amorg.nyekilajos.kosherfood.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class HighScoresActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.high_scores, menu);
		return true;
	}

}
