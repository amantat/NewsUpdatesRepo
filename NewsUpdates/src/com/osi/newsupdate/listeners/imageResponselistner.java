package com.osi.newsupdate.listeners;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response.Listener;

public class imageResponselistner implements Listener<Bitmap>{

	
	private ImageView imageV;
	
	public imageResponselistner(ImageView iv)
	{
		
		this.imageV = iv;
		
	}
	
	
	@Override
	public void onResponse(Bitmap response) {
		// TODO Auto-generated method stub
		
		imageV.setImageBitmap(response);
		
	}

}
