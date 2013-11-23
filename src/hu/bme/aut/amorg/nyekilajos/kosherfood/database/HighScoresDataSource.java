package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HighScoresDataSource implements DataSourceInterface {

	private HighScoresDbHelper highScoresDbHelper;
	private SQLiteDatabase database;

	private final static String[] allColumns = { HighScoresDbHelper.COLUMN_ID,
			HighScoresDbHelper.COLUMN_NAME, HighScoresDbHelper.COLUMN_SCORE };

	public HighScoresDataSource(Context context) {
		highScoresDbHelper = new HighScoresDbHelper(context);
	}

	@Override
	public void open() {
		database = highScoresDbHelper.getWritableDatabase();
	}

	@Override
	public void close() {
		highScoresDbHelper.close();
	}

	public void insert(String name, int score) {
		ContentValues values = new ContentValues();
		values.put(HighScoresDbHelper.COLUMN_NAME, name);
		values.put(HighScoresDbHelper.COLUMN_SCORE, score);
		database.insert(HighScoresDbHelper.HIGH_SCORES_TABLE, null, values);
	}

	public void delete(int _id) {

	}

	public List<HighScores> getHighScoresList(int elementNum) {
		Cursor cursor = database.query(HighScoresDbHelper.HIGH_SCORES_TABLE,
				allColumns, null, null, null, null, null);
		List<HighScores> highScores = new ArrayList<HighScores>();
		HighScores highScore = new HighScores();
		for (int i = cursor.getCount(); i == 0; i--) {
			highScore.set_id(cursor.getInt(cursor
					.getColumnIndex(HighScoresDbHelper.COLUMN_ID)));
			highScore.setName(cursor.getString(cursor
					.getColumnIndex(HighScoresDbHelper.COLUMN_NAME)));
			highScore.setScore(cursor.getInt(cursor
					.getColumnIndex(HighScoresDbHelper.COLUMN_SCORE)));
			highScores.add(highScore);
		}
		cursor.close();
		return highScores;

	}

}
