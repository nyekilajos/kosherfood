package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import android.content.Context;
import android.database.Cursor;

import com.google.inject.Inject;

public class FoodsDataSource extends AbstractKosherDataSource {

	private final static String[] allColumns = { KosherDbHelper.COLUMN_ID,
			KosherDbHelper.COLUMN_NAME, KosherDbHelper.COLUMN_IS_KOSHER,
			KosherDbHelper.COLUMN_INFORMATION };

	@Inject
	public FoodsDataSource(Context context) {
		super(context);
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
