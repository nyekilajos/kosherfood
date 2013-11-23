package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HighScoresDbHelper extends SQLiteOpenHelper {

	public static final String HIGH_SCORES_TABLE = "high_scores_table";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_SCORE = "score";

	private static final String DATABASE_NAME = "high_scores.db";
	private static final int DATATBASE_VERSION = 1;

	private static final String DATABASE_CREATE = "create table "
			+ HIGH_SCORES_TABLE 
			+ "(" 
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_NAME + " text not null, " 
			+ COLUMN_SCORE + " integer not null);";

	public HighScoresDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATATBASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(HighScoresDbHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + HIGH_SCORES_TABLE);
		onCreate(db);

	}

}
