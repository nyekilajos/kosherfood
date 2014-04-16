package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import java.io.IOException;

import roboguice.RoboGuice;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;

public class FoodsDataSource implements DataSourceInterface {

	@Inject
	private KosherDbHelper kosherDbHelper;

	private SQLiteDatabase database;

	private final static String[] allColumns = { KosherDbHelper.COLUMN_ID,
			KosherDbHelper.COLUMN_NAME, KosherDbHelper.COLUMN_IS_KOSHER,
			KosherDbHelper.COLUMN_INFORMATION };

	@Inject
	public FoodsDataSource(Context context) {
		RoboGuice.getInjector(context).injectMembers(this);
	}

	@Override
	public void open() {
		try {
			database = kosherDbHelper.getDatabase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void close() {
		kosherDbHelper.close();

	}

	public Foods getFood(int _id) {
		Cursor cursor = database.query(KosherDbHelper.FOODS_TABLE, allColumns,
				KosherDbHelper.COLUMN_ID + "=" + _id, null, null, null, null);
		if (cursor == null)
			return null;
		cursor.moveToFirst();
		Foods food = new Foods();
		food.set_id(cursor.getInt(cursor
				.getColumnIndex(KosherDbHelper.COLUMN_ID)));
		food.setName(cursor.getString(cursor
				.getColumnIndex(KosherDbHelper.COLUMN_NAME)));
		food.setIs_kosher(cursor.getInt(cursor
				.getColumnIndex(KosherDbHelper.COLUMN_IS_KOSHER)));
		food.setInformation(cursor.getString(cursor
				.getColumnIndex(KosherDbHelper.COLUMN_INFORMATION)));
		cursor.close();
		return food;
	}

}
