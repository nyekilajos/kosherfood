package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NotKosherPairsDataSource implements DataSourceInterface {

	private KosherDbHelper kosherDbHelper;
	private SQLiteDatabase database;

	private final static String[] allColumns = {
			KosherDbHelper.COLUMN_FOOD_ID_FIRST,
			KosherDbHelper.COLUMN_FOOD_ID_SECOND,
			KosherDbHelper.COLUMN_INFORMATION };

	public NotKosherPairsDataSource(Context context) {
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

	public NotKosherPairs insert(NotKosherPairs notKosherPairs) {
		ContentValues values = new ContentValues();
		values.put(KosherDbHelper.COLUMN_FOOD_ID_SECOND,
				notKosherPairs.getFood_first_id());
		values.put(KosherDbHelper.COLUMN_FOOD_ID_SECOND,
				notKosherPairs.getFood_second_id());
		values.put(KosherDbHelper.COLUMN_INFORMATION,
				notKosherPairs.getInformation());
		database.insert(KosherDbHelper.NOT_KOSHER_PAIRS_TABLE, null, values);
		return notKosherPairs;
	}

	public void delete(NotKosherPairs notKosherPairs) {
		database.delete(
				KosherDbHelper.NOT_KOSHER_PAIRS_TABLE,
				KosherDbHelper.COLUMN_FOOD_ID_FIRST + "="
						+ notKosherPairs.getFood_first_id() + "AND"
						+ KosherDbHelper.COLUMN_FOOD_ID_SECOND + "="
						+ notKosherPairs.getFood_second_id(), null);
	}

	public NotKosherPairs getNotKosherPairs(int food_first_id,
			int food_second_id) {
		Cursor cursor = database.query(KosherDbHelper.NOT_KOSHER_PAIRS_TABLE,
				allColumns, KosherDbHelper.COLUMN_FOOD_ID_FIRST + "="
						+ food_first_id + "AND"
						+ KosherDbHelper.COLUMN_FOOD_ID_SECOND + "="
						+ food_second_id, null, null, null, null);

		NotKosherPairs notKosherPairs = new NotKosherPairs();
		notKosherPairs.setFood_first_id(cursor.getInt(cursor
				.getColumnIndex(KosherDbHelper.COLUMN_FOOD_ID_FIRST)));
		notKosherPairs.setFood_second_id(cursor.getInt(cursor
				.getColumnIndex(KosherDbHelper.COLUMN_FOOD_ID_SECOND)));
		notKosherPairs.setInformation(cursor.getString(cursor
				.getColumnIndex(KosherDbHelper.COLUMN_INFORMATION)));
		cursor.close();
		return notKosherPairs;
	}

}
