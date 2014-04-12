package hu.bme.aut.amorg.nyekilajos.kosherfood.activities;

import hu.bme.aut.amorg.nyekilajos.kosherfood.R;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.Settings;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import com.google.inject.Inject;

public class OptionsActivity extends RoboActivity {

	@InjectView(R.id.cb_enable_sounds)
	private CheckBox cb_enable_sounds;
	
	@InjectView(R.id.rg_plate_number)
	private RadioGroup rg_plate_number;
	
	@Inject
	private Settings settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);

	}

	@Override
	protected void onStop() {
		settings.setEnableSounds(cb_enable_sounds.isChecked());
		switch (rg_plate_number.getCheckedRadioButtonId()) {
		case R.id.rb_one_plate:
			settings.setPlayerNum(1);
			break;

		default:
			settings.setPlayerNum(4);
			break;
		}
		super.onStop();
	}

}
