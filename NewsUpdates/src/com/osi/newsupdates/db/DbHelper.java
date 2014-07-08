package com.osi.newsupdates.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbHelper {

	//db
	public static final String DATABASE_NAME = "newsupdatebase.db";
	public static final int DATABASE_VERSION = 1;

	//Tables
	public static final String TABLE_CHANNEL = "_channels";
	public static final String TABLE_CATEGORY = "_category";
	public static final String TABLE_CACHE = "_cache";
	
	//Fields
	public static final String ID = "id";
	public static final String CHANNELNAME = "channelName";
	public static final String IMAGEURL = "imageurl";
	public static final String CATEGORYNAME= "categoryName";
	public static final String CATEGORYURL = "categoryurl";
	public static final String COUNT = "count";
	public static final String CHANNELID = "channelId";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static final String LINK = "link";
	

	//constants
	public static final String CREATE_TABLE = "create table ";
	public static final String TEXT_NOTNULL = " TEXT NOT NULL ";
	public static final String TEXT = " TEXT ";
	public static final String INTEGER = " INTEGER ";
	public static final String INTEGER_AUTOINCREMENT = " INTEGER  primary key autoincrement";


	private static final String ChannelQuery = CREATE_TABLE + TABLE_CHANNEL+"(" + 
			ID + INTEGER_AUTOINCREMENT + "," + 
			CHANNELNAME + TEXT_NOTNULL + "," + 
			IMAGEURL + TEXT + ");";

	
	private static final String CategoryQuery = CREATE_TABLE + TABLE_CATEGORY+ "(" + 
			ID + INTEGER_AUTOINCREMENT + "," + 
			CHANNELID + INTEGER + "," + 
			CATEGORYNAME + TEXT_NOTNULL + "," + 
			CATEGORYURL + TEXT_NOTNULL + "," + 
			COUNT + INTEGER  + ");";

	private static final String CacheQuery = CREATE_TABLE + TABLE_CACHE + "(" + 
			ID + INTEGER_AUTOINCREMENT + "," + 
			CHANNELNAME + TEXT_NOTNULL + "," + 
			CATEGORYNAME + TEXT_NOTNULL + "," +
			TITLE + TEXT_NOTNULL + "," +
			DESCRIPTION + TEXT_NOTNULL + "," +
			LINK + TEXT_NOTNULL + ");";

	

	private SQLiteDatabase db;

	public  DbHelper(Context ct){
		try {   
			db = ct.openOrCreateDatabase(DATABASE_NAME, DATABASE_VERSION, null);
			db.execSQL(ChannelQuery);
			db.execSQL(CategoryQuery);
			db.execSQL(CacheQuery);
			
		} catch (Exception e) {
			e.printStackTrace();				
		} finally{
			db.close();
		}
	}
}
