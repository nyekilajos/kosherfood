package hu.bme.aut.amorg.nyekilajos.kosherfood.activities;

import hu.bme.aut.amorg.nyekilajos.kosherfood.R;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.Settings;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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

		loadSettings();

	}

	@Override
	protected void onStop() {
		SharedPreferences sp = getSharedPreferences(
				Settings.PREFERENCES_FILE_NAME, MODE_PRIVATE);
		Editor editor = sp.edit();

		editor.putBoolean(Settings.SOUND_ENABLED_FIELD,
				cb_enable_sounds.isChecked());
		settings.setEnableSounds(cb_enable_sounds.isChecked());

		switch (rg_plate_number.getCheckedRadioButtonId()) {
		case R.id.rb_one_plate:
			settings.setPlayerNum(1);
			editor.putInt(Settings.PLAYER_NUM_FIELD, 1);
			break;

		default:
			settings.setPlayerNum(4);
			editor.putInt(Settings.PLAYER_NUM_FIELD, 4);
			break;
		}
		editor.commit();

		super.onStop();
	}

	private void loadSettings() {
		SharedPreferences sp = getSharedPreferences(
				Settings.PREFERENCES_FILE_NAME, MODE_PRIVATE);

		cb_enable_sounds.setChecked(sp.getBoolean(Settings.SOUND_ENABLED_FIELD,
				true));
		switch (sp.getInt(Settings.PLAYER_NUM_FIELD, 4)) {
		case 1:
			rg_plate_number.check(R.id.rb_one_plate);
			break;
		default:
			rg_plate_number.check(R.id.rb_four_plate);
			break;
		}
	}

}
