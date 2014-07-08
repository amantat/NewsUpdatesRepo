package com.osi.newsupdate.listeners;

import android.content.Context;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.osi.newsupdates.NewsActivity;

public class Responseerrorlistner implements ErrorListener{

	
	NewsActivity context;
	
	public Responseerrorlistner(NewsActivity context)
	{
		
		this.context =context;
		
	}
	
	
	
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		
		if(context.pd!=null)
		{
			
			context.pd.dismiss();
			
		}
		
		
	}

	
	
}
