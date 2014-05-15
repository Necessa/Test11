package com.ustc.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ustc.model.Board;
import com.ustc.model.BoardSection;

public class BoardTableDao {
	private static final String TAG = "BoardTable";
	public static final String TABLE_NAME = "boards";
	public static final String ID_COLUMN = "_id";
	public static final String NAME_COLUMN = "name";
	public static final String TITLE_COLUMN = "title";
	public static final String SECTION_COLUMN = "section";
	public static final String SECTIONLINK_COLUMN = "sectionLink";
	public static final String LINK_COLUMN = "link";
	
	private static DBHelper helper;
	
	public BoardTableDao(Context cxt){
		Log.v(TAG, "BoardTable");
		helper = new DBHelper(cxt);
	}
	
	public long insert(String name,String title, String section,String sectionLink, String link){
		Log.v(TAG, "insert");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(NAME_COLUMN, name);
		values.put(TITLE_COLUMN, title);
		values.put(SECTION_COLUMN, section);
		values.put(SECTIONLINK_COLUMN, sectionLink);
		values.put(LINK_COLUMN, link);
	
		long id = db.insert(TABLE_NAME, null, values);
		db.close();
		
		return id;
	}
	
	public int delete(String name){
		Log.v(TAG, "delete");
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] names = new String[]{name};
		int num = db.delete(TABLE_NAME, NAME_COLUMN + "=?", names);
		db.close();
		return num;
	}
	
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
	
	public ArrayList<Board> query(String name){
		Log.v(TAG, "query");
		ArrayList<Board> list = new ArrayList<Board>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, NAME_COLUMN + "=?", new String[]{name}, null, null, null);
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
	
	public ArrayList<String[]> queryBySec(String sec){
		Log.v(TAG, "queryBySec");
		ArrayList<String[]> list = new ArrayList<String[]>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, new String[]{NAME_COLUMN,TITLE_COLUMN,LINK_COLUMN}, SECTION_COLUMN + "=?", new String[]{sec}, null, null, null);
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
			String title = cursor.getString(cursor.getColumnIndex(TITLE_COLUMN));
			String link = cursor.getString(cursor.getColumnIndex(LINK_COLUMN));
			list.add(new String[]{name,title,link});
		}
		cursor.close();
		db.close();
		return list;
	}
	
	public ArrayList<String> fetchSections(){
		Log.v(TAG, "fetchSections");
		ArrayList<String> list = new ArrayList<String>();
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor cursor = db.query(true,TABLE_NAME, new String[]{SECTION_COLUMN}, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			list.add(cursor.getString(cursor.getColumnIndex(SECTION_COLUMN)));
		}
		cursor.close();
		db.close();
		return list;
	}
	
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
}
