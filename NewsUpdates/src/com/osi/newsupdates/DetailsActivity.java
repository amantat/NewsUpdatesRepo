package com.osi.newsupdates;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.osi.newsupdate.listeners.imageResponselistner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.TextView;


@SuppressLint("NewApi")
public class DetailsActivity extends Activity {

	
	
	private String title,desc,imageurl;
	
	private TextView title_tv,desc_tv; 
	private ImageView image;
	private RequestQueue queue;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_view);
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = 
			        new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			}
		
		title_tv=(TextView)findViewById(R.id.title_tv);
		desc_tv=(TextView)findViewById(R.id.description_tv);
		image=(ImageView)findViewById(R.id.image);
		
		Intent i = getIntent();
		Bundle extras = i.getExtras();

		title = extras.getString("Title");
		desc = extras.getString("Description");
		imageurl=extras.getString("ImageUrl");
		
		title_tv.setText(title);
		desc_tv.setText(desc);
		
		
	/*	queue = Volley.newRequestQueue(this);
		
		ImageRequest ir = new ImageRequest(imageurl, new imageResponselistner(image), 280, 280, null, null);
		
		
		queue.add(ir);*/
		
		URL newurl = null;
		try {
			newurl = new URL(imageurl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 Bitmap mIcon_val;
		try {
			mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
			image.setImageBitmap(mIcon_val);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
