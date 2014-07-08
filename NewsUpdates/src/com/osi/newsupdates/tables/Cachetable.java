package com.osi.newsupdates.tables;

import java.util.ArrayList;

import com.osi.newsupdate.models.Cache;
import com.osi.newsupdate.models.Category;
import com.osi.newsupdate.models.Newsitems;
import com.osi.newsupdates.db.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Cachetable {





	private static final String DATABASE_TABLE = "_cache";




	private SQLiteDatabase db;

	public Cachetable(Context ct)
	{
		db =    ct.openOrCreateDatabase(DbHelper.DATABASE_NAME, DbHelper.DATABASE_VERSION, null);
	}


	public void close() {
		db.close();
	}


	public void delete()
	{
		db.execSQL("DELETE FROM "+DATABASE_TABLE);

	}


	public void insertcache(String channelname,String categoryname,String title,String description,
			String link)
	{

		ContentValues initialValues = new ContentValues();
		initialValues.put(DbHelper.CHANNELNAME, channelname);
		initialValues.put(DbHelper.CATEGORYNAME, categoryname);
		initialValues.put(DbHelper.TITLE, title);
		initialValues.put(DbHelper.DESCRIPTION, description);
		initialValues.put(DbHelper.LINK, link);


		db.insert(DbHelper.TABLE_CACHE, null, initialValues);

	}

	public void insertcache(String channelname,String categoryname,ArrayList<Newsitems> narray)
	{

		int size=0;
		//Log.d("CacheSize", "CacheSize"+narray.size());

		if(narray.size()>=15)
		{

			size=15;

		}else{

			size = narray.size();

		}


		for(int i=0;i<size;i++)
		{

			Newsitems ni = (Newsitems)narray.get(i);

			ContentValues initialValues = new ContentValues();
			initialValues.put(DbHelper.CHANNELNAME, channelname);
			initialValues.put(DbHelper.CATEGORYNAME, categoryname);
			initialValues.put(DbHelper.TITLE, ni.getTitle());
			initialValues.put(DbHelper.DESCRIPTION, ni.getDescripetion());
			initialValues.put(DbHelper.LINK, ni.getNewslink());


			db.insert(DbHelper.TABLE_CACHE, null, initialValues);



		}



	}


	public Cache getnameandcatdetails()
	{

		Cache ca = new Cache();

		try{
			Cursor c =
					db.query(DbHelper.TABLE_CACHE, new String[] {DbHelper.CHANNELNAME,
							DbHelper.CATEGORYNAME,DbHelper.TITLE,DbHelper.DESCRIPTION,DbHelper.LINK}, null, null, null, null, null);  

			if(c.moveToFirst())
			{




				ca.setChannelname(c.getString(0));
				ca.setCategoryname(c.getString(1));






			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return ca;

	}


	public ArrayList<Cache> getalldetails()
	{

		ArrayList<Cache> cache = new ArrayList<Cache>();


		try{
			Cursor c =
					db.query(DbHelper.TABLE_CACHE, new String[] {DbHelper.CHANNELNAME,
							DbHelper.CATEGORYNAME,DbHelper.TITLE,DbHelper.DESCRIPTION,DbHelper.LINK}, null, null, null, null, null);  

			if(c.moveToFirst())
			{

				do
				{
					Cache ca = new Cache();

					ca.setChannelname(c.getString(0));
					ca.setCategoryname(c.getString(1));
					ca.setTitle(c.getString(2));
					ca.setDescription(c.getString(3));
					ca.setLink(c.getString(4));

					cache.add(ca);

				}while(c.moveToNext());

			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return cache;
	}


	public ArrayList<Newsitems> getdetails()
	{

		ArrayList<Newsitems> cache = new ArrayList<Newsitems>();

		try{
			Cursor c =
					db.query(DbHelper.TABLE_CACHE, new String[] {DbHelper.TITLE,DbHelper.DESCRIPTION,
							DbHelper.LINK }, null, null, null, null, null);  

			if(c.moveToFirst())
			{

				do
				{
					Newsitems ni = new Newsitems();

					ni.setTitle(c.getString(c.getColumnIndex(DbHelper.TITLE)));
					ni.setDescripetion(c.getString(c.getColumnIndex(DbHelper.DESCRIPTION)));
					ni.setNewslink(c.getString(c.getColumnIndex(DbHelper.LINK)));



					cache.add(ni);

				}while(c.moveToNext());

			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return cache;
	}








}
