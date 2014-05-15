package com.ustc.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ustc.model.Board;

public class UserBoardTableDao {
	private static final String TAG = "UserBoardTableDao";
	public static final String TABLE_NAME = "userBoardTable";
	
	public static final String ID_COLUMN = "_id";
	public static final String USER_COLUMN = "user";
	public static final String BOARDTITLE_COLUMN = "boardTitle";
	private static DBHelper helper;
	
	public UserBoardTableDao(Context cxt){
		Log.v(TAG, "UserBoardTableDao");
		helper = new DBHelper(cxt);
	}
	
	public long insert(String user,String boardTitle){
		Log.v(TAG, "insert");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(USER_COLUMN, user);
		values.put(BOARDTITLE_COLUMN, boardTitle);
	
		long id = db.insert(TABLE_NAME, null, values);
		db.close();
		
		return id;
	}
	
	public int delete(String user,String boardTitle){
		Log.v(TAG, "delete");
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] boards = new String[]{user,boardTitle};
		int num = db.delete(TABLE_NAME, USER_COLUMN + "=? and " + BOARDTITLE_COLUMN + "=?", boards);
		db.close();
		return num;
	}
/*	
	public int update(String name, String title, String section,String sectionLink, String link){
		Log.v(TAG, "update");
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TITLE_COLUMN, title);
		values.put(SECTION_COLUMN, section);
		values.put(SECTIONLINK_COLUMN, sectionLink);
		values.put(LINK_COLUMN, link);
		int num = db.update(TABLE_NAME, values, NAME_COLUMN + "=?", new String[]{name});
		db.close();
		return num;
	}
*/	
	public ArrayList<Board> query(String user){
		Log.v(TAG, "query");
		ArrayList<Board> list = new ArrayList<Board>();
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, null, USER_COLUMN + "=?", new String[]{user}, null, null, null);
		while(cursor.moveToNext()){
			String board = cursor.getString(cursor.getColumnIndex(BOARDTITLE_COLUMN));
			//query BoardTable
			Cursor cursor2 = db.query(BoardTableDao.TABLE_NAME, null, BoardTableDao.TITLE_COLUMN + "=?", new String[]{board}, null, null, null);
			while(cursor2.moveToNext()){
				String name = cursor2.getString(cursor2.getColumnIndex(BoardTableDao.NAME_COLUMN));
				String title = cursor2.getString(cursor2.getColumnIndex(BoardTableDao.TITLE_COLUMN));
				String sec = cursor2.getString(cursor2.getColumnIndex(BoardTableDao.SECTION_COLUMN));
				String secLink = cursor2.getString(cursor2.getColumnIndex(BoardTableDao.SECTIONLINK_COLUMN));
				String link = cursor2.getString(cursor2.getColumnIndex(BoardTableDao.LINK_COLUMN));
				list.add(new Board(name,title,sec,secLink,link));
			}
			cursor2.close();
		}
		cursor.close();
		db.close();
		return list;
	}
	
	public boolean checkIsExist(String user,String boardTitle){
		Log.v(TAG, "query");
		boolean result = false;
		SQLiteDatabase db = helper.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME, null, USER_COLUMN + "=? and " + BOARDTITLE_COLUMN + "=?", new String[]{user,boardTitle}, null, null, null);
		while(cursor.moveToNext()){
			result = true;
		}
		cursor.close();
		db.close();
		return result;
	}
	
	/*
	public ArrayList<Board> fetchAll(){
		Log.v(TAG, "fetchAll");
		ArrayList<Board> list = new ArrayList<Board>();
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			String n = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
			String title = cursor.getString(cursor.getColumnIndex(TITLE_COLUMN));
			String sec = cursor.getString(cursor.getColumnIndex(SECTION_COLUMN));
			String secLink = cursor.getString(cursor.getColumnIndex(SECTIONLINK_COLUMN));
			String link = cursor.getString(cursor.getColumnIndex(LINK_COLUMN));
			Board u = new Board(n,title,sec,secLink,link);
			list.add(u);
		}
		cursor.close();
		db.close();
		return list;
	}
	*/
}
