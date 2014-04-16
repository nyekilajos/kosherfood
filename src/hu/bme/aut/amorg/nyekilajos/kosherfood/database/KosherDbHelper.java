package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import com.google.inject.Inject;

@ContextSingleton
public class KosherDbHelper extends SQLiteOpenHelper {

	public static final String FOODS_TABLE = "foods";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_IS_KOSHER = "is_kosher";
	public static final String COLUMN_INFORMATION = "information";

	public static final String NOT_KOSHER_PAIRS_TABLE = "not_kosher_pairs";
	public static final String COLUMN_FOOD_ID_FIRST = "food_id_first";
	public static final String COLUMN_FOOD_ID_SECOND = "food_id_second";

	protected static final String DATABASE_NAME = "kosher.db";
	protected static final int DATATBASE_VERSION = 1;

	protected static final String DATABASE_PATH = Environment
			.getDataDirectory()
			+ "/data/hu.bme.aut.amorg.nyekilajos.kosherfood/databases/";

	private SQLiteDatabase database;

	private Context context;

	protected static final String CREATE_FOODS = "create table " + FOODS_TABLE
			+ "(" + COLUMN_ID + " integer primary key not null, " + COLUMN_NAME
			+ " text not null, " + COLUMN_IS_KOSHER + " integer not null, "
			+ COLUMN_INFORMATION + " text not null);";

	protected static final String CREATE_NOT_KOSHER_PAIRS = "create table "
			+ NOT_KOSHER_PAIRS_TABLE + "(" + COLUMN_ID
			+ " integer primary key autoincrement, " + COLUMN_FOOD_ID_FIRST
			+ " integer not null, " + COLUMN_FOOD_ID_SECOND
			+ " integer not null, " + COLUMN_INFORMATION + " text not null);";

	@Inject
	public KosherDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATATBASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	public SQLiteDatabase getDatabase() throws IOException {
		try {
			database = SQLiteDatabase.openDatabase(DATABASE_PATH
					+ DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			this.getReadableDatabase();
			createDatabaseFromAssets();
			database = SQLiteDatabase.openDatabase(DATABASE_PATH
					+ DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
		}

		return database;
	}
	
	public void initDatabase() throws IOException
	{
		try {
			database = SQLiteDatabase.openDatabase(DATABASE_PATH
					+ DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			this.getReadableDatabase();
			createDatabaseFromAssets();
			database = SQLiteDatabase.openDatabase(DATABASE_PATH
					+ DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
		}

		database.close();
	}

	private void createDatabaseFromAssets() throws IOException {

		InputStream is = context.getAssets().open(DATABASE_NAME);

		String outFileName = DATABASE_PATH + DATABASE_NAME;

		OutputStream os = new FileOutputStream(outFileName);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}

		os.flush();
		os.close();
		is.close();
	}

	@Override
	public synchronized void close() {
		if (database != null)
			database.close();
		super.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
