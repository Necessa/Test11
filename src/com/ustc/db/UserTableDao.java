package com.ustc.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ustc.model.User;

public class UserTableDao {
	private static final String TAG = "UserTableDao";
	
	public static final String TABLE_NAME = "user";
	public static final String ID_COLUMN = "_id";
	public static final String USER_COLUMN = "user";
	public static final String PASSWORD_COLUMN = "password";
	
	private static DBHelper helper;
	
	public UserTableDao(Context cxt){
		Log.v(TAG, "UserTableDao");
		helper = new DBHelper(cxt);
	}
	
	public long insert(String user, String pw){
		Log.v(TAG, "insert");
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(USER_COLUMN, user);
		values.put(PASSWORD_COLUMN, pw);
	
		long id = db.insert(TABLE_NAME, null, values);
		db.close();
		
		return id;
	}
	
	public int delete(String username){
		Log.v(TAG, "delete");
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] users = new String[]{username};
		int num = db.delete(TABLE_NAME, USER_COLUMN + "=?", users);
		db.close();
		return num;
	}
	
	public int update(String user, String pw){
		Log.v(TAG, "update");
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PASSWORD_COLUMN, pw);
		int num = db.update(TABLE_NAME, values, USER_COLUMN + "=?", new String[]{user});
		db.close();
		return num;
	}
	
	public ArrayList<User> query(String user){
		Log.v(TAG, "query");
		ArrayList<User> list = new ArrayList<User>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, USER_COLUMN + "=?", new String[]{user}, null, null, null);
		while(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
			String username = cursor.getString(cursor.getColumnIndex(USER_COLUMN));
			String pw = cursor.getString(cursor.getColumnIndex(PASSWORD_COLUMN));
			User u = new User(username,pw);
			list.add(u);
		}
		cursor.close();
		db.close();
		return list;
	}
	
	public ArrayList<User> fetchAll(){
		Log.v(TAG, "fetchAll");
		ArrayList<User> list = new ArrayList<User>();
		SQLiteDatabase db = helper.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
			String username = cursor.getString(cursor.getColumnIndex(USER_COLUMN));
			String pw = cursor.getString(cursor.getColumnIndex(PASSWORD_COLUMN));
			User u = new User(username,pw);
			list.add(u);
		}
		cursor.close();
		db.close();
		return list;
	}
}
