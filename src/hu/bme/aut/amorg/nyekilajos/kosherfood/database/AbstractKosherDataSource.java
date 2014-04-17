package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import java.io.IOException;

import roboguice.RoboGuice;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;

public abstract class AbstractKosherDataSource {

	@Inject
	protected KosherDbHelper kosherDbHelper;

	protected SQLiteDatabase database;

	public AbstractKosherDataSource(Context context) {
		RoboGuice.getInjector(context).injectMembers(this);
	}

	public void open() {
		try {
			database = kosherDbHelper.getDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		kosherDbHelper.close();
	}
}
