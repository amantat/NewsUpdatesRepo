package com.osi.newsupdates.tables;

import java.util.ArrayList;

import com.osi.newsupdate.models.Category;
import com.osi.newsupdates.db.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Categorytable {




	private SQLiteDatabase db;

	public Categorytable(Context ct)
	{
		db =    ct.openOrCreateDatabase(DbHelper.DATABASE_NAME, DbHelper.DATABASE_VERSION, null);
	}


	public void close() {
		db.close();
	}

	public void insertcategory(String categoryname,String categoryurl,int channelid)
	{

		ContentValues initialValues = new ContentValues();
		initialValues.put(DbHelper.CATEGORYNAME, categoryname);
		initialValues.put(DbHelper.CATEGORYURL, categoryurl);
		initialValues.put(DbHelper.CHANNELID, channelid);

		db.insert(DbHelper.TABLE_CATEGORY, null, initialValues);

	}


	public void deletechannel(int id)
	{

		db.delete(DbHelper.TABLE_CATEGORY, "id=" + id, null);

	}


	public void updatecategory(int id,String categoryname,String categoryurl,int channelid)
	{

		ContentValues initialValues = new ContentValues();
		initialValues.put(DbHelper.CHANNELID, channelid);
		initialValues.put(DbHelper.CATEGORYNAME, categoryname);
		initialValues.put(DbHelper.CATEGORYURL, categoryurl);


		db.update(DbHelper.TABLE_CATEGORY, initialValues, "id=" +id, null);

	}


	public ArrayList<Category> getdetailsfrmchnlid(int channelid)
	{

		ArrayList<Category> categories = new ArrayList<Category>();

		try{
			Cursor c =
					db.query(DbHelper.TABLE_CATEGORY, new String[] {DbHelper.ID,
							DbHelper.CATEGORYNAME,DbHelper.CATEGORYURL}, DbHelper.CHANNELID+"="+channelid, null, null, null, null);  

			if(c.moveToFirst())
			{

				do
				{
					Category ca = new Category();

					ca.setId(c.getInt(c.getColumnIndex(DbHelper.ID)));
					ca.setCategoryname(c.getString(c.getColumnIndex(DbHelper.CATEGORYNAME)));
					ca.setCategoryurl(c.getString(c.getColumnIndex(DbHelper.CATEGORYURL)));

					categories.add(ca);

				}while(c.moveToNext());

			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}



		return categories;
	}



	
	
	
	public ArrayList<Category> getdetailsfrmcatid(int id)
	{

		ArrayList<Category> categories = new ArrayList<Category>();

		try{
			Cursor c =
					db.query(DbHelper.TABLE_CATEGORY, new String[] {
							DbHelper.CATEGORYNAME,DbHelper.CATEGORYURL}, DbHelper.ID+"="+id, null, null, null, null);  

			if(c.moveToFirst())
			{

				do
				{
					Category ca = new Category();


					ca.setCategoryname(c.getString(c.getColumnIndex(DbHelper.CATEGORYNAME)));
					ca.setCategoryurl(c.getString(c.getColumnIndex(DbHelper.CATEGORYURL)));

					categories.add(ca);

				}while(c.moveToNext());

			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}



		return categories;

	}

	public ArrayList<String> getcategoriesfrmchnlid(int channelid)
	{

		ArrayList<String> categories = new ArrayList<String>();

		try{
			Cursor c =
					db.query(DbHelper.TABLE_CATEGORY, new String[] {
							DbHelper.CATEGORYNAME}, DbHelper.CHANNELID+"="+channelid, null, null, null, null);  

			if(c.moveToFirst())
			{

				do
				{
					
					categories.add(c.getString(c.getColumnIndex(DbHelper.CATEGORYNAME)));

				}while(c.moveToNext());

			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}



		return categories;
	}
	
	


}
