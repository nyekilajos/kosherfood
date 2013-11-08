package hu.bme.aut.amorg.nyekilajos.kosherfood.activities;

import hu.bme.aut.amorg.nyekilajos.kosherfood.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn_start = (Button)findViewById(R.id.btn_start);
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent openSurface = new Intent();
				openSurface.setClass(getApplicationContext(), KosherSurfaceActivity.class);
				startActivity(openSurface);
				
			}
		});
		
		Button btn_options = (Button)findViewById(R.id.btn_options);
		btn_options.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent openOptions = new Intent();
				openOptions.setClass(getApplicationContext(), OptionsActivity.class);
				startActivity(openOptions);
				
			}
		});
		
		Button btn_exit = (Button)findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}


}
