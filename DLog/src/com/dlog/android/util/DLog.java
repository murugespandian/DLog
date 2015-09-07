package com.dlog.android.util;

import android.util.Log;

import com.dlog.android.constants.Constants.LogMode;


public final class DLog
{

	public static final int ASSERT=0x00000007;
	public static final int DEBUG=0x00000003;
	public static final int ERROR=0x00000006;
	public static final int INFO=0x00000004;
	public static final int VERBOSE=0x00000002;
	public static final int WARN=0x00000005;
	public static final int WTF=0x00000008;

	
	public static int d(String tag,String msg)
	{
	 
		if(dLog(DEBUG,tag, msg))
		{
			return Log.d(tag, msg);
		}
		return 0;
	}

	private static boolean dLog(int type,String tag, String msg) 
	{
		DLogUtil dLogUtil=DLogUtil.INSTANCE;
		if(dLogUtil.getLogMode()==LogMode.DISABLE)
		{
			return false;
		}
		dLogUtil.recordLog(type,tag, msg);
		return true;
	}
	
	public static int d(String tag,String msg,Throwable tr)
	{
		if(dLog(DEBUG,tag, msg+" : "+Log.getStackTraceString(tr)))
		{
			return Log.d(tag, msg,tr);
		}
		return 0;
	}
	
	public static int e(String tag,String msg)
	{
		
		if(dLog(ERROR,tag, msg))
		{
			return Log.e(tag, msg);
		}
		return 0;
	}
	
	public static int e(String tag,String msg,Throwable tr)
	{
		if(dLog(ERROR,tag, msg+" : "+getStackTraceString(tr)))
		{
			return Log.e(tag, msg,tr);
		}
		return 0;
	}
	
	public static String getStackTraceString(Throwable tr)
	{
		return Log.getStackTraceString(tr);
	}
	
	
	public static int i(String tag,String msg)
	{
		if(dLog(INFO,tag, msg))
		{
			return Log.i(tag, msg);
		}
		return 0;
	}
	
	public static int i(String tag,String msg,Throwable tr)
	{
		if(dLog(INFO,tag, msg+" : "+getStackTraceString(tr)))
		{
			return Log.i(tag, msg);
		}
		return 0;
	}
	
	public static boolean isLoggable(String tag,int level)
	{
		return Log.isLoggable(tag,level);
	}
	
	public static int println(int priority,String tag,String msg)
	{
		if(dLog(priority,tag,msg))
		{
			return Log.println(priority, tag, msg);
		}
		return 0;
	}
	
	public static int v(String tag,String msg)
	{
		if(dLog(VERBOSE,tag,msg))
		{
			return Log.v(tag, msg);
		}
		return 0;
	}
	
	public static int v(String tag,String msg,Throwable tr)
	{
		if(dLog(VERBOSE,tag,msg+" : "+getStackTraceString(tr)))
		{
			return Log.v(tag, msg);
		}
		return 0;
	}
	
	public static int w(String tag,String msg)
	{
		if(dLog(WARN,tag,msg))
		{
			return Log.w(tag, msg);
		}
		return 0;
	}
	
	public static int w(String tag,String msg,Throwable tr)
	{
		if(dLog(WARN,tag,msg+" : "+getStackTraceString(tr)))
		{
			return Log.w(tag, msg);
		}
		
		return 0;
	}
	public static int w(String tag,Throwable tr)
	{
		if(dLog(WARN,tag,getStackTraceString(tr)))
		{
			return Log.w(tag, tr);
		}
		return 0;
	}
	
	public static int wtf(String tag,String msg)
	{
		if(dLog(WTF,tag,msg))
		{
			return Log.wtf(tag, msg);
		}
		return 0;
	}
	
	public static int wtf(String tag,String msg,Throwable tr)
	{
		if(dLog(WTF,tag,msg+" : "+getStackTraceString(tr)))
		{
			return Log.wtf(tag, msg,tr);
		}
		return 0;
	}
	
	public static int wtf(String tag,Throwable tr)
	{
		if(dLog(WTF,tag,getStackTraceString(tr)))
		{
			return Log.wtf(tag, tr);
		}
		return 0;
	}
	
}
