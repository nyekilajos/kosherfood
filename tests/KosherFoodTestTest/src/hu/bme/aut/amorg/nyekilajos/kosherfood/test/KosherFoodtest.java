package hu.bme.aut.amorg.nyekilajos.kosherfood.test;

import com.robotium.solo.Solo;

import hu.bme.aut.amorg.nyekilajos.kosherfood.activities.KosherSurfaceActivity;
import hu.bme.aut.amorg.nyekilajos.kosherfood.core.KosherSurface;
import android.test.ActivityInstrumentationTestCase2;

public class KosherFoodtest extends  ActivityInstrumentationTestCase2<KosherSurfaceActivity>{

	private Solo solo;
	
	public KosherFoodtest() {
		super(KosherSurfaceActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		super.setUp();
	}
	
	public void testKosher()
	{
		KosherSurface kosherSurface = (KosherSurface) solo.getView(KosherSurface.class, 0);
		solo.drag(300, 80, 200, kosherSurface.getHeight() / 2, 100);
		assertNotNull(kosherSurface);
		if(kosherSurface.getHeight() == 0)
			throw new RuntimeException();
	
		
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}
	
	

}
