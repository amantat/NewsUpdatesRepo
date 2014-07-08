package com.osi.newsupdates.utils;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImagefromUri  {
	
	
	
	public RequestQueue queue;
	private ImageView imageV;
	
	public ImagefromUri(Context context,String url,final ImageView iv)
	{
	
		this.imageV = iv;
		
		queue = Volley.newRequestQueue(context);

		ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
			
			 
			
			    @Override
		
			    public void onResponse(Bitmap response) {
			
			       imageV.setImageBitmap(response);
			
			 
			
			    }
			
			}, 0, 0, null, null);
		
		queue.add(ir);
		
		
	}
	
	
	

}
