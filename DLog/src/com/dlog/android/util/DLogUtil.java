package com.dlog.android.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.dlog.android.constants.Constants.LogMode;
import com.dlog.android.database.DBContract.Column;
import com.dlog.android.database.DBContract.Table;
import com.dlog.android.database.DLogDatabase;

public enum DLogUtil 
{
	INSTANCE;
	LogMode mode=LogMode.DISABLE;
	Context context;
	ContentValues contentValues=new ContentValues();
	
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy hh:mm",Locale.getDefault());
	Date date=new Date();
	
	
	public void initializeLog(Context context,LogMode mode)
	{
		this.context=context;
		this.mode=mode;
		context.getContentResolver().query(Uri.EMPTY, null, null, null, null);
	}
	
	public void recordLog(int type,String tag,String msg)
	{
		if(mode==LogMode.DISABLE || mode==LogMode.NORMAL)
		{
			return;
		}
		
		contentValues.clear();
		date.setTime(System.currentTimeMillis());
		contentValues.put(Column.MESSAGE,msg);
		contentValues.put(Column.TIME,simpleDateFormat.format(date));
		contentValues.put(Column.TAG,msg);
		contentValues.put(Column.TYPE, type);
		new DLogDatabase(context).getWritableDatabase().insert(Table.DLog,null,contentValues);
	}
	
	
	public void deleteLogs()
	{
		DLogDatabase.dLogDatabase.dLogDatabase.getWritableDatabase().delete(Table.DLog,null,null);
	}
	
	
	public Cursor getCursor(String selection,String[] selectionArgs)
	{
		return DLogDatabase.dLogDatabase.getReadableDatabase().query(Table.DLog, null, selection, selectionArgs, null, null,Column.ID+" ASC ");
	}
	
	
	public boolean isLogAvailable()
	{
		Cursor cursor=null;
		try
		{
			cursor=getCursor(null,null);
			return cursor!=null && cursor.moveToFirst();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(cursor!=null)
			{
				cursor.close();
			}
		}
		
		return false;
	}
	
	public LogMode getLogMode()
	{
		return mode;
	}
	
	public String getLogsByTag(String tag)
	{
		if(tag==null)
		{
			return null;
		}
		String selection=Column.TAG+" =?";
		String[]selectionArgs={tag}; 
		return getSavedLogs(selection, selectionArgs);
		
	}

	private String getSavedLogs(String selection, String[] selectionArgs) 
	{
		String logs=null;
		Cursor cursor=null;
		try
		{
			cursor=getCursor(selection, selectionArgs);
			logs= convertCursorToString(cursor);
		}
		catch(Exception exception)
		{
			
		}
		finally
		{
			if(cursor!=null)
			{
				cursor.close();
			}
		}
		return logs;
	}
	
	public String getLogsByType(int type)
	{
		String selection=Column.TYPE+" =?";
		String[]selectionArgs={String.valueOf(type)}; 
		
		return getSavedLogs(selection, selectionArgs);
	}
	
	public String getAllLogs()
	{
		return getSavedLogs(null, null);
	}

	public String getLogsByDays(int days)
	{
		String selection=Column.TIME+" >?";
		String[]selectionArgs={String.valueOf(days)}; 
		
		Calendar calendar=Calendar.getInstance();
		int now=calendar.get(Calendar.DATE);
		calendar.add(Calendar.DATE,-days);
		now=now-days;
		return getSavedLogs(selection, selectionArgs);
	}
	
	public String convertCursorToString(Cursor cursor)
	{
		StringBuilder stringBuilder=new StringBuilder();
		
		if(cursor==null|| !cursor.moveToFirst())
		{
			return null;
		}
		
		for(;cursor.moveToNext();)
		{
			stringBuilder.append(cursor.getString(cursor.getColumnIndex(Column.TIME)));
			stringBuilder.append(cursor.getString(cursor.getColumnIndex(Column.TAG)));
			stringBuilder.append(cursor.getString(cursor.getColumnIndex(Column.MESSAGE)));
		}
		
		return stringBuilder.toString();
	}
	

	public File writeLogsToFile(String filePath,String logs) 
	{
		if(filePath==null)
		{
			throw new IllegalArgumentException("File path is empty.Please give valid File Path");
		}
		BufferedWriter bufferedWriter=null;
		File file=null;
		try
		{
			file=new File(filePath);
			if(!file.exists())
			{
				file.createNewFile();
			}
			FileWriter fileWriter=new FileWriter(file.getAbsolutePath());
			bufferedWriter=new BufferedWriter(fileWriter);
			bufferedWriter.write(logs);
		}
		catch(Exception exception)
		{
			exception.printStackTrace( );
		}
		finally
		{
			if(bufferedWriter!=null)
			{
				try
				{
					bufferedWriter.close();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
				bufferedWriter=null;
			}
		}
		
		return file;
	}
	
	public File getLogs(String fileLocation)
	{
		return writeLogsToFile(fileLocation, getAllLogs());
	}
	
	
	public File getLogsByTag(String fileLocation,String tag)
	{
		return writeLogsToFile(fileLocation, getLogsByTag(tag));
	}
	
	public File getLogsByType(String fileLocation,int type)
	{
		return writeLogsToFile(fileLocation, getLogsByType(type));
	}
	
	public File getLogsByDays(String fileLocation,int days)
	{
		return writeLogsToFile(fileLocation, getLogsByDays(days));
	}
}
