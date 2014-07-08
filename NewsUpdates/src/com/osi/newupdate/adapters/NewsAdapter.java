package com.osi.newupdate.adapters;

import java.util.ArrayList;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.osi.newsupdate.models.Newsitems;
import com.osi.newsupdates.NewsActivity;
import com.osi.newsupdates.R;
import com.osi.newsupdates.utils.ImagefromUri;



@SuppressLint("NewApi")
public class NewsAdapter extends BaseAdapter{
	private NewsActivity context;
	private LayoutInflater inflater;
	private ArrayList<Newsitems> dataList;
	 public com.osi.newsupdates.utils.ImageLoader imageLoader;

	 public ImagefromUri imagefromuri;
	 public ImageLoader il;
	public NewsAdapter(ArrayList<Newsitems> dataList,
			NewsActivity context) {
		this.dataList = dataList;
		this.context = context;
		
	/*	mRequestQueue = Volley.newRequestQueue(context);
		mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
		    private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
		    public void putBitmap(String url, Bitmap bitmap) {
		        mCache.put(url, bitmap);
		    }
		    public Bitmap getBitmap(String url) {
		        return mCache.get(url);
		    }
		});*/
		
		// imageLoader=new com.osi.newsupdates.utils.ImageLoader(context.getApplicationContext());
		 
		
		
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {


		final ListViewHolder vh;
		if (convertView == null) {
			vh=new ListViewHolder();
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.news_listitems, parent,	false);
			vh.tv = (TextView) convertView.findViewById(R.id.news_title);
			vh.publishdate = (TextView) convertView.findViewById(R.id.feed_date);
			//vh.remove=(Button)convertView.findViewById(R.id.removebutton);
			vh.image=(NetworkImageView)convertView.findViewById(R.id.image);
			
			convertView.setTag(vh);
		}else{
			vh = (ListViewHolder)convertView.getTag();
		}

		final Newsitems ao = dataList.get(position);


		vh.tv.setText(ao.getTitle());
		vh.publishdate.setText(ao.getPublisheddate());
		/*if(ao.getImage()!=null){
			vh.image.setVisibility(View.VISIBLE);
		vh.image.setImageUrl(ao.getImage(), context.iml);
		vh.image.setDefaultImageResId(R.drawable.stub);
		vh.image.setErrorImageResId(R.drawable.stub);
		}else{
			vh.image.setVisibility(View.GONE);
		}*/
		
		// imageLoader.DisplayImage(ao.getImage(), vh.image);
		//vh.image.setImageUrl(ao.getImage(), mImageLoader);
       //  imagefromuri = new ImagefromUri(context, ao.getImage(),vh.image);
		 
		 playAnimation(convertView);

		return convertView;

	}
	
	
	private void playAnimation(View v){
		  AnimatorSet set = new AnimatorSet();
		  set.play(ObjectAnimator.ofFloat(v,View.SCALE_X, 0, 1));
		  set.play(ObjectAnimator.ofFloat(v, View.SCALE_Y, 0,1));
		  set.setDuration(300);
		  set.setInterpolator(new DecelerateInterpolator());
		  set.start();
		 }
	
	public class ListViewHolder{
		public TextView tv,publishdate;
		public Button remove;
		public NetworkImageView image;
	}
}

