package hu.bme.aut.amorg.nyekilajos.kosherfood.test;

import hu.bme.aut.amorg.nyekilajos.kosherfood.R;
import hu.bme.aut.amorg.nyekilajos.kosherfood.activities.KosherSurfaceActivity;
import hu.bme.aut.amorg.nyekilajos.kosherfood.activities.MainActivity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

	private Solo solo;
	
	public MainActivityTest() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	
	
	public void testMainActivity() {
		Log.d("Robotium", "StartTest");
		solo.clickOnView(solo.getView(R.id.btn_start));
		Log.d("Robotium", "Test");
		solo.waitForActivity(KosherSurfaceActivity.class, 4000);
		solo.waitForLogMessage("KosherSurfaceActivity created");
		Log.d("Robotium", "EndTest");
		
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}
	
	
	
	

}
