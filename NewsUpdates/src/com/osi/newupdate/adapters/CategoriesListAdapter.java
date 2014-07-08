package com.osi.newupdate.adapters;

import java.util.ArrayList;

import com.osi.newsupdate.models.Category;
import com.osi.newsupdate.models.Channel;
import com.osi.newsupdates.R;
import com.osi.newupdate.adapters.ManageCategoryAdapter.ListViewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class CategoriesListAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Category> dataList;
	
	public CategoriesListAdapter(ArrayList<Category> dataList,
			Context context) {
		this.dataList = dataList;
		this.context = context;
		
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
			convertView = inflater.inflate(R.layout.categories_listitems, parent,	false);
			vh.tv = (TextView) convertView.findViewById(R.id.CatItem);
			//vh.remove=(Button)convertView.findViewById(R.id.delete_button);
			//vh.remove=(Button)convertView.findViewById(R.id.removebutton);
			
			convertView.setTag(vh);
		}else{
			vh = (ListViewHolder)convertView.getTag();
		}

		final Category co = dataList.get(position);


		vh.tv.setText(co.getCategoryname());
		
		
		

		return convertView;

	}
	public class ListViewHolder{
		public TextView tv;
		public Button remove;
		
	}
}
