package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import android.database.sqlite.SQLiteDatabase;

public class FoodsDataSource implements DataSourceInterface {

	private KosherDbHelper kosherDbHelper;
	private SQLiteDatabase database;

	@Override
	public void open() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	public void insert(int _id, String name, int is_kosher, String information) {

	}

	public void delete(int _id) {

	}

	public Foods getFood(int _id) {
		return new Foods();
	}

}
