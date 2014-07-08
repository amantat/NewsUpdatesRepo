package com.osi.newupdate.adapters;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.osi.newsupdate.models.Category;
import com.osi.newsupdates.R;
import com.osi.newsupdates.tables.Categorytable;

public class ManageCategoryAdapter extends BaseAdapter{

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Category> dataList;
	
	public ManageCategoryAdapter(ArrayList<Category> dataList,
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
			convertView = inflater.inflate(R.layout.managecategory_listitems, parent,	false);
			vh.tv = (TextView) convertView.findViewById(R.id.category_name);
			vh.remove=(Button)convertView.findViewById(R.id.delete_button);
			//vh.remove=(Button)convertView.findViewById(R.id.removebutton);
			
			convertView.setTag(vh);
		}else{
			vh = (ListViewHolder)convertView.getTag();
		}

		final Category co = dataList.get(position);


		vh.tv.setText(co.getCategoryname());
		
		
		vh.remove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				AlertDialog.Builder adb = new AlertDialog.Builder(context);

				adb.setTitle("Alert");

				adb.setMessage("Are you sure you want to remove ");

				adb.setIcon(android.R.drawable.ic_dialog_alert);

				adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						//removeQGuide(qGuideName);
						dataList.remove(co);
						
						Categorytable categorytable = new Categorytable(context);
					      categorytable.deletechannel(co.getId());
					      
					      
						
						notifyDataSetChanged();
					} 	
				});

				adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});	    
				adb.show();
			}
		});

		return convertView;

	}
	public class ListViewHolder{
		public TextView tv;
		public Button remove;
		
	}
	
}
