package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import java.util.Locale;

import android.content.Context;
import android.database.Cursor;

import com.google.inject.Inject;

public class FoodsDataSource extends AbstractKosherDataSource {

	private final static String[] allColumns = { KosherDbHelper.COLUMN_ID,
			KosherDbHelper.COLUMN_NAME, KosherDbHelper.COLUMN_IS_KOSHER,
			KosherDbHelper.COLUMN_INFORMATION_HU,
			KosherDbHelper.COLUMN_INFORMATION_EN };

	private String currentLocale;

	@Inject
	public FoodsDataSource(Context context) {
		super(context);
		currentLocale = Locale.getDefault().getDisplayLanguage();
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

		if (currentLocale.equals("magyar"))
			food.setInformation(cursor.getString(cursor
					.getColumnIndex(KosherDbHelper.COLUMN_INFORMATION_HU)));
		else
			food.setInformation(cursor.getString(cursor
					.getColumnIndex(KosherDbHelper.COLUMN_INFORMATION_EN)));

		cursor.close();
		return food;
	}

}
