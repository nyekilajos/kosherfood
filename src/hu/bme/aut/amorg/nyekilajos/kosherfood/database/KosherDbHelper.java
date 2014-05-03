package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import roboguice.RoboGuice;
import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.google.inject.Inject;

@ContextSingleton
public class KosherDbHelper extends SQLiteOpenHelper {

	public static final String FOODS_TABLE = "foods";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_IS_KOSHER = "is_kosher";
	public static final String COLUMN_INFORMATION_HU = "information_hu";
	public static final String COLUMN_INFORMATION_EN = "information_en";

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
			if (database == null)
				Log.d("INITDB", "DB_NULL");
			else
				Log.d("INITDB", "DB_NOT_NULL");

		} catch (SQLiteException e) {
			Log.d("INITDB", "DB_NOT_EXISTS");
			this.getReadableDatabase();
			createDatabaseFromAssets();
			database = SQLiteDatabase.openDatabase(DATABASE_PATH
					+ DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
		}

		return database;
	}

	public void initDatabase() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected() == true) {
			Log.d("INITDB", "ONLINE");
			try {
				downloadDatabaseFromNetwork();
			} catch (IOException e) {
				Log.e(e.getMessage(), e.getStackTrace().toString());
			} catch (URISyntaxException e) {
				Log.e(e.getMessage(), e.getStackTrace().toString());
			}
		} else {
			Log.d("INITDB", "OFFLINE");
			try {
				getDatabase();
			} catch (IOException e) {
				Log.e(e.getMessage(), e.getStackTrace().toString());
			}
		}
		this.close();
	}

	private void downloadDatabaseFromNetwork() throws IOException,
			URISyntaxException {
		// Log.d("DB", "Download database from network");

		this.getReadableDatabase();

		HttpGet httpGet = RoboGuice.getInjector(context).getInstance(
				HttpGet.class);
		httpGet.setURI(new URI(
				"https://www.dropbox.com/s/gva9gwq4by775rn/kosher.db?dl=1"));

		HttpClient httpClient = RoboGuice.getInjector(context).getInstance(
				DefaultHttpClient.class);

		HttpResponse httpResponse = httpClient.execute(httpGet);

		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			InputStream is = httpResponse.getEntity().getContent();
			createDatabase(is);
			writeDatabaseToFile(is);
			is.close();
		}
	}

	private void createDatabaseFromAssets() throws IOException {
		// Log.d("DB", "Get database from file");
		InputStream is = context.getAssets().open(DATABASE_NAME);
		createDatabase(is);
		is.close();
	}

	private void createDatabase(InputStream is) throws IOException {

		String outFileName = DATABASE_PATH + DATABASE_NAME;

		OutputStream os = new FileOutputStream(outFileName);

		copyData(is, os);

		os.flush();
		os.close();
	}

	private void writeDatabaseToFile(InputStream is) {
		// TODO
	}

	private void copyData(InputStream is, OutputStream os) throws IOException {
		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) > 0) {
			os.write(buffer, 0, length);
		}
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
