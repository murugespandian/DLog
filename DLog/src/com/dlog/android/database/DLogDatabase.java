package com.dlog.android.database;

import com.dlog.android.database.DBContract.Column;
import com.dlog.android.database.DBContract.Table;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DLogDatabase extends SQLiteOpenHelper 
{

	public static final String DB_NAME="dLog.db";
	public static final int DB_VERSION=1;
	public static DLogDatabase dLogDatabase;
	public DLogDatabase(Context context) 
	{
		super(context,DB_NAME,null,DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase) 
	{
		dLogDatabase=this;
		createTable(sqliteDatabase);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, int arg1, int arg2) 
	{
		sqliteDatabase.execSQL("DROP TABLE IF EXIST "+Table.DLog);
	}

	private void createTable(SQLiteDatabase sqliteDatabase)
	{
		sqliteDatabase.execSQL("CREATE TABLE "+Table.DLog+"  ( "
								+Column.ID+" INTEGER PRIMARY KEY , "
								+Column.TIME+" TEXT , "
								+Column.TAG+" TEXT , "
								+Column.TYPE+" TEXT , "
								+Column.MESSAGE+" TEXT );"
							   );
	}
	
	
}
