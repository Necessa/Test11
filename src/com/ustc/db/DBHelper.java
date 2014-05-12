package com.ustc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* SQLiteOpenHelper�����࣬ ���ڲ������ݿ�  
 * 
 * SQLiteOpenHelper ��һ�������࣬�����������ݿ�Ĵ����Ͱ汾���ṩ������Ĺ��ܣ�
 * 
 * ��һ��getReadableDatabase() �� getWriteableDatabase() ���Ի��SQLiteDatabase����ͨ���ö�����Զ����ݿ���в�����
 * 
 * �ڶ����ṩonCreate() ; onUpgrade() �����ص����������������ٴ�����ɾ�����ݿ�ʱ�������Լ��Ĳ�����
 * 
 */
public class DBHelper extends SQLiteOpenHelper {
	private static final String TAG = "DBHelper";// ���Ա�ǩ 
	//���ݿ����ƣ����԰���������ݱ�
	private static final String DATABASE_NAME = "bbsdb.db";
	
	public static final int DATABASE_VERSION = 1;

	/* ���췽�������ø���SQLiteOpenHelper�Ĺ��캯�� */
	/* ��1�������Ļ�������2:���ݿ�����(��.db��β) ; ��3���α깤��(Ĭ��Ϊnull) ; ��4������ʹ�����ݿ�ģ�Ͱ汾��֤��*/
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.v(TAG, "DBHelper");
	}

	/* ������Ҫ��SQLiteDatabase �Ķ�����������ݳ�ʼ�� */
	/* �÷���ʱ�ڵ�һ�δ�����ʱ��ִ�У�ʵ����ʱ��һ�εõ�SQLiteDatabase�����ʱ��Ż����������� */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v(TAG, "onCreate");
		// ������һ
		final String CREATE_TABLE_user = "CREATE TABLE if not exists " + UserTableDao.TABLE_NAME +  
                " (" + UserTableDao.ID_COLUMN + " integer primary key autoincrement, " +  
                UserTableDao.USER_COLUMN + " text not null, " +  
                UserTableDao.PASSWORD_COLUMN + " integer);"; 
		
		final String CREATE_TABLE_board = "CREATE TABLE if not exists " + BoardTableDao.TABLE_NAME +  
				" (" + BoardTableDao.ID_COLUMN + " integer primary key autoincrement, " +  
				BoardTableDao.NAME_COLUMN + " text not null, " +  
				BoardTableDao.SECTION_COLUMN + " text not null, " +  
				BoardTableDao.LINK_COLUMN + " integer);";
		
		db.execSQL(CREATE_TABLE_user);
		db.execSQL(CREATE_TABLE_board);
		

	}

	
	/* �����ݿ�Ӿɵ�ģ��ת��Ϊ�µ�ģ�� *//* ��1������   �� ��2���ɰ汾�� �� ��3���°汾�� */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO �������ݿ�汾�Ĳ���
		Log.v(TAG, "onUpgrade");
//		db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE_TABLE);
//		db.execSQL("DROP TABLE IF EXISTS " + BoardTable.TABLE_TABLE);
	}

	/* �����ݿ�ִ�еĺ��� */
	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO ÿ�γɹ������ݿ�����ȱ�ִ��
		Log.v(TAG, "onOpen");
		super.onOpen(db);
	}
	@Override
	public void close() {
		Log.v(TAG, "close");
		super.close();
	}
	
}