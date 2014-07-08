package com.osi.newsupdates.tables;

import java.util.ArrayList;

import com.osi.newsupdate.models.Channel;
import com.osi.newsupdates.db.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Channeltable {



	private SQLiteDatabase db;


	public Channeltable(Context ct){

		db =    ct.openOrCreateDatabase(DbHelper.DATABASE_NAME, DbHelper.DATABASE_VERSION, null);

	}


	public void close() {
		db.close();
	}


	public void insertchannel(String channelname,String imageurl)
	{


		ContentValues initialValues = new ContentValues();
		initialValues.put(DbHelper.CHANNELNAME, channelname);
		initialValues.put(DbHelper.IMAGEURL, imageurl);

		
		db.insert(DbHelper.TABLE_CHANNEL, null, initialValues);


	}


	public void deletechannel(int id)
	{

		db.delete(DbHelper.TABLE_CHANNEL, "id=" + id, null);

	}


	public void updatechannel(int id,String channelname,String imageurl)
	{

		ContentValues initialValues = new ContentValues();

		
		initialValues.put(DbHelper.CHANNELNAME, channelname);
		initialValues.put(DbHelper.IMAGEURL, imageurl);

		db.update(DbHelper.TABLE_CHANNEL, initialValues, "id=" +id, null);

	}
	
	
	public void updatechannelimage(int id,String imageurl)
	{

		ContentValues initialValues = new ContentValues();

		
	
		initialValues.put(DbHelper.IMAGEURL, imageurl);

		db.update(DbHelper.TABLE_CHANNEL, initialValues, "id=" +id, null);

	}



	public String getnamefromid(int id)
	{
		String name = null;

		try{
			Cursor c =
					db.query(DbHelper.TABLE_CHANNEL, new String[] {
							DbHelper.CHANNELNAME }, "id=" + id, null, null, null, null);  

			if(c.moveToFirst())
			{

				name = c.getString(0);

			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}


		return name;
	}



	public ArrayList<Channel> getalldetails()
	{

		ArrayList<Channel> channel = new ArrayList<Channel>();

		try{
			Cursor c =
					db.query(DbHelper.TABLE_CHANNEL, new String[] {DbHelper.ID,
							DbHelper.CHANNELNAME,DbHelper.IMAGEURL }, null, null, null, null, null);  

			if(c.moveToFirst())
			{

				do
				{
					Channel ch = new Channel();

					ch.setId(c.getInt(c.getColumnIndex(DbHelper.ID)));
					ch.setChannelname(c.getString(c.getColumnIndex(DbHelper.CHANNELNAME)));
					ch.setLogoimageurl(c.getString(c.getColumnIndex(DbHelper.IMAGEURL)));

					channel.add(ch);

				}while(c.moveToNext());

			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}



		return channel;
	}

	public int getChannelidfromname(String channelName){


		int id = 0;

		try{
			Cursor	c = db.query(true, DbHelper.TABLE_CHANNEL, new String[] {DbHelper.ID, DbHelper.CHANNELNAME, DbHelper.IMAGEURL}, 
					DbHelper.CHANNELNAME + "=\"" + channelName+ "\"" , null, null, null, null, null);

			if(c.moveToFirst())
			{

				do
				{

					id=c.getInt(c.getColumnIndex(DbHelper.ID));
                    

				}while(c.moveToNext());

			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}



		return id;
	}

	public Channel getChannelfromname(String channelName){


		Channel ch = new Channel();

		try{
			Cursor	c = db.query(true, DbHelper.TABLE_CHANNEL, new String[] {DbHelper.ID, DbHelper.CHANNELNAME, DbHelper.IMAGEURL}, 
					DbHelper.CHANNELNAME + "=\"" + channelName+ "\"" , null, null, null, null, null);

			if(c.moveToFirst())
			{

				do
				{

					ch.setId(c.getInt(c.getColumnIndex(DbHelper.ID)));
					ch.setChannelname(c.getString(c.getColumnIndex(DbHelper.CHANNELNAME)));
                    

				}while(c.moveToNext());

			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}



		return ch;
	}

	
	
	
	 public boolean checkchannelname(String name)
	 {
	  boolean check =false;
	  
	  
	  try {
	      Cursor c =
	                 db.query(DbHelper.TABLE_CHANNEL, new String[] {
	                       DbHelper.CHANNELNAME
	                       }, DbHelper.CHANNELNAME+"=?", new String[]{name}, null, null, null);
	             
	         
	      if(c.getCount()==0)
	           {
	            check = true;
	           }
	      
	      
	           } catch (Exception e) {
	             System.out.println(""+e);
	           }
	     
	  
	  
	  
	  return check;
	 }
	 
	 
	 


}
