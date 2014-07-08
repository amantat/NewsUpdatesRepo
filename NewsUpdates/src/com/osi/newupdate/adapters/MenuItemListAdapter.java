package com.osi.newupdate.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.osi.newsupdate.models.Channel;
import com.osi.newsupdates.R;
import com.osi.newsupdates.utils.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuItemListAdapter extends BaseAdapter{

	private Context context;
	public ArrayList<Channel> dataList;
	private LayoutInflater inflater;
	private ImageLoader imageloader;
	private static int selectedIndex;
	
	public MenuItemListAdapter(Context con,ArrayList<Channel> dataList){
		this.context = con;
		this.dataList = dataList;
		this.inflater = LayoutInflater.from(context);
		 imageloader=new com.osi.newsupdates.utils.ImageLoader(context.getApplicationContext());
	}
	
	@Override
	public int getCount() {		
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	
	public static void setSelectedIndex(int ind) {
	     selectedIndex = ind;
	 }
	
	public int getfirstindex(String name)
	{
		int selid=0;
		
	  for(int i=0;i<dataList.size();i++)
	  {
		  
		  Channel c = (Channel)dataList.get(i);
		  
		  if(c.getChannelname().equals(name))
		  {
			  selid = i;
			  
		  }
		  
	  }
		
		
		return selid;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {		
		Channel current = dataList.get(position);		
	
		TextView tv;
		ImageView im;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.drawer_layout, parent,false);
		}
		tv = (TextView)convertView.findViewById(R.id.MenuItem);
		im = (ImageView)convertView.findViewById(R.id.flag);
		tv.setText(current.getChannelname());
		
		if(current.getLogoimageurl().equalsIgnoreCase(""))
		{
			
		im.setImageResource(R.drawable.rss_feed_con);
		
		
		}else{
			
			imageloader.DisplayImage(current.getLogoimageurl(), im);
			
		}
		
		 if (position == selectedIndex) {
	         // convertView.setBackgroundColor(convertView.getResources().getColor(R.color.skyblue));
	         // convertView.setBackgroundResource(R.color.darkgrey);
	      }
	      else {
	        //  convertView.setBackgroundColor(convertView.getResources().getColor(R.color.silver));
	      }
		
		return convertView;
	}
	
	

}
