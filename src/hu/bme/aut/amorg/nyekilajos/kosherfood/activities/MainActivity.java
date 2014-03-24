package hu.bme.aut.amorg.nyekilajos.kosherfood.activities;

import hu.bme.aut.amorg.nyekilajos.kosherfood.R;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends RoboActivity {

	@InjectView(R.id.btn_start)
	private Button btn_start;

	@InjectView(R.id.btn_options)
	private Button btn_options;

	@InjectView(R.id.btn_help)
	private Button btn_help;

	@InjectView(R.id.btn_high_scores)
	private Button btn_high_scores;

	@InjectView(R.id.btn_exit)
	private Button btn_exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openSurface = new Intent();
				openSurface.setClass(getApplicationContext(),
						KosherSurfaceActivity.class);
				startActivity(openSurface);

			}
		});

		btn_high_scores.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openHighScores = new Intent();
				openHighScores.setClass(getApplicationContext(),
						HighScoresActivity.class);
				startActivity(openHighScores);

			}
		});

		btn_options.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openOptions = new Intent();
				openOptions.setClass(getApplicationContext(),
						OptionsActivity.class);
				startActivity(openOptions);

			}
		});

		btn_help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openHelp = new Intent();
				openHelp.setClass(getApplicationContext(), HelpActivity.class);
				startActivity(openHelp);

			}
		});

		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
	}

}
