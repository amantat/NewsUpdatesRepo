package com.osi.newsupdate.listeners;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.util.Log;

import com.android.volley.Response.Listener;
import com.osi.newsupdate.models.Newsitems;
import com.osi.newsupdates.NewsActivity;
import com.osi.newsupdates.tables.Cachetable;
import com.osi.newsupdates.tables.Channeltable;
import com.osi.newsupdates.utils.XMLParser;
import com.osi.newupdate.adapters.NewsAdapter;

public class ResponseListner implements Listener<String>{


	private NewsActivity context;
	private Cachetable cache;
	
	public ResponseListner(NewsActivity context)
	{

		this.context = context;

	}


	public void onResponse(String response) {
		
		
		
		Log.d("Response","Response"+response);

		XMLParser parser = new XMLParser();

		org.w3c.dom.Document doc = parser.getDomElement(response);

		NodeList nn = doc.getElementsByTagName("channel");

		Element nodeelement = (Element)nn.item(0);
		
		NodeList nnn = nodeelement.getElementsByTagName("image");
		
		Element nodeelement1 = (Element)nnn.item(0);
		
		Log.d("LogoImage", "LogoImage"+parser.getValue(nodeelement1, "url"));
		
			
			Channeltable ct = new Channeltable(context);
			
			int id =ct.getChannelidfromname(context.channelnameholder);
			
			ct.updatechannelimage(id,parser.getValue(nodeelement1, "url"));
			
			ct.close();
			
		NodeList nn1 = nodeelement.getElementsByTagName("item");

		ArrayList<Newsitems> itemarray = new ArrayList<Newsitems>();

		for(int i=0; i<nn1.getLength();i++)
		{

			Element e = (Element) nn1.item(i);
			Newsitems item = new Newsitems();

			item.setTitle(parser.getValue(e, "title"));

			item.setNewslink(parser.getValue(e, "link"));
			
			item.setPublisheddate(parser.getValue(e, "pubDate"));
			
			Log.d("NEWSLINK","NEWSLINK"+item.getNewslink()+"Date "+item.getPublisheddate());

			String totaldescription = parser.getValue(e, "description");
			item.setDescripetion("DESCRIPTION");

			try{

				/*ArrayList<String> urllist=new ArrayList<String>();
				for (String retval: totaldescription.split("'")){
					urllist.add(retval);
				}


				String[] dec = totaldescription.split("gt;");

				String description = dec[0];

				dec=description.split(">");


				item.setImage(urllist.get(1).replace("\"", ""));

				item.setDescripetion(dec[1]);

				Log.d("ImageUrl", "ImageUrl"+urllist.get(1).replace("\"", ""));*/
				
				//item.setDescripetion(totaldescription);

				Log.d("title+++",item.getTitle());

			}catch(Exception f){
				f.printStackTrace();
			}

			itemarray.add(item);



		}

		
		if(context.pd!=null){
			context.pd.dismiss();
		}

		context.newsadapter = new NewsAdapter(itemarray, context);

		context.newslist.setAdapter(context.newsadapter);
		
		cache = new Cachetable(context);
		cache.delete();
		//Log.d("GGGGG","GGGGG"+context.channelnameholder+"  "+context.categorynameholder+"itemarray: "+itemarray.size());
		cache.insertcache(context.channelnameholder, context.categorynameholder, itemarray);

	}


}
