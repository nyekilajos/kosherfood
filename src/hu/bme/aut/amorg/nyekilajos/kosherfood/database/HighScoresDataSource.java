package hu.bme.aut.amorg.nyekilajos.kosherfood.database;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

public class HighScoresDataSource implements DataSourceInterface {

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
	
	public void insert(int _id, String name, int score)
	{
		
	}
	
	public void delete(int _id)
	{
		
	}
	
	public List<HighScores> getHighScoresList(int elementNum)
	{
		return new ArrayList<HighScores>();
		
	}
	
	

}
