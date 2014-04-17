package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import android.content.Context;
import android.database.Cursor;

import com.google.inject.Inject;

public class NotKosherPairsDataSource extends AbstractKosherDataSource {

	private final static String[] allColumns = {
			KosherDbHelper.COLUMN_FOOD_ID_FIRST,
			KosherDbHelper.COLUMN_FOOD_ID_SECOND,
			KosherDbHelper.COLUMN_INFORMATION };

	@Inject
	public NotKosherPairsDataSource(Context context) {
		super(context);
	}

	/**
	 * This function executes a query that look up for appropriate records in
	 * the given order and in the reverse order as well.
	 * 
	 * @param food_first_id
	 * @param food_second_id
	 * @return
	 */

	public NotKosherPairs getNotKosherPairs(int food_first_id,
			int food_second_id) {
		Cursor cursor = database.query(KosherDbHelper.NOT_KOSHER_PAIRS_TABLE,
				allColumns, KosherDbHelper.COLUMN_FOOD_ID_FIRST + " = "
						+ food_first_id + " AND "
						+ KosherDbHelper.COLUMN_FOOD_ID_SECOND + " = "
						+ food_second_id, null, null, null, null);

		// Because the sequence of the food is not important, the query must be
		// executed in reverse order.
		if (cursor == null || cursor.getCount() == 0) {
			cursor = database.query(KosherDbHelper.NOT_KOSHER_PAIRS_TABLE,
					allColumns, KosherDbHelper.COLUMN_FOOD_ID_FIRST + " = "
							+ food_second_id + " AND "
							+ KosherDbHelper.COLUMN_FOOD_ID_SECOND + " = "
							+ food_first_id, null, null, null, null);

			// If there is still no result, then it is not in the database
			if (cursor == null || cursor.getCount() == 0) {
				cursor.close();
				return null;
			}
		}

		// If found appropriate record, it is processed
		cursor.moveToFirst();
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
