package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import com.google.inject.Inject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class KosherDbHelper extends SQLiteOpenHelper {

	public static final String FOODS_TABLE = "foods";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_IS_KOSHER = "is_kosher";
	public static final String COLUMN_INFORMATION = "information";

	public static final String NOT_KOSHER_PAIRS_TABLE = "not_kosher_pairs";
	public static final String COLUMN_FOOD_ID_FIRST = "food_id_first";
	public static final String COLUMN_FOOD_ID_SECOND = "food_id_second";

	private static final String DATABASE_NAME = "kosher.db";
	private static final int DATATBASE_VERSION = 1;

	private static final String CREATE_FOODS = "create table " + FOODS_TABLE
			+ "(" + COLUMN_ID + " integer primary key not null, " + COLUMN_NAME
			+ " text not null, " + COLUMN_IS_KOSHER + " integer not null, "
			+ COLUMN_INFORMATION + " text not null);";

	private static final String CREATE_NOT_KOSHER_PAIRS = "create table "
			+ NOT_KOSHER_PAIRS_TABLE + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_FOOD_ID_FIRST
			+ " integer not null, " + COLUMN_FOOD_ID_SECOND
			+ " integer not null, " + COLUMN_INFORMATION + " text not null);";

	@Inject
	public KosherDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATATBASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_FOODS);
		db.execSQL(CREATE_NOT_KOSHER_PAIRS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(HighScoresDbHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + FOODS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + NOT_KOSHER_PAIRS_TABLE);
		onCreate(db);

	}

}
