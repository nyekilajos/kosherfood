package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FoodsDataSource implements DataSourceInterface {

	private KosherDbHelper kosherDbHelper;
	private SQLiteDatabase database;

	private final static String[] allColumns = { KosherDbHelper.COLUMN_ID,
			KosherDbHelper.COLUMN_NAME, KosherDbHelper.COLUMN_IS_KOSHER,
			KosherDbHelper.COLUMN_INFORMATION };

	public FoodsDataSource(Context context) {
		kosherDbHelper = new KosherDbHelper(context);
	}

	@Override
	public void open() {
		database = kosherDbHelper.getWritableDatabase();

	}

	@Override
	public void close() {
		kosherDbHelper.close();

	}

	public Foods insert(Foods food) {
		ContentValues values = new ContentValues();
		values.put(KosherDbHelper.COLUMN_ID, food.get_id());
		values.put(KosherDbHelper.COLUMN_NAME, food.getName());
		values.put(KosherDbHelper.COLUMN_IS_KOSHER, food.getIs_kosher());
		values.put(KosherDbHelper.COLUMN_INFORMATION, food.getInformation());
		database.insert(KosherDbHelper.FOODS_TABLE, null, values);
		return food;

	}

	public void delete(Foods food) {
		int id = food.get_id();
		database.delete(KosherDbHelper.FOODS_TABLE, KosherDbHelper.COLUMN_ID
				+ "=" + id, null);
	}

	public Foods getFood(int _id) {
		Cursor cursor = database.query(KosherDbHelper.FOODS_TABLE, allColumns,
				KosherDbHelper.COLUMN_ID + "=" + _id, null, null, null, null);
		Foods food = new Foods();
		food.set_id(cursor.getInt(0));
		food.setName(cursor.getString(1));
		food.setIs_kosher(cursor.getInt(2));
		food.setInformation(cursor.getString(3));
		cursor.close();
		return food;
	}
}
