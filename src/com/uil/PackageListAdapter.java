package com.uil;

import java.util.List;
import java.util.Random;

import com.uil.util.SysUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;


/**
 * Adapter to display all packages installed on a device
 * 
 * @since 2014-03-07
 * @version 1.0
 * */
public class PackageListAdapter extends BaseAdapter implements ListAdapter {

	private Activity activity;
	private Context context;
	private List<?> content;

	
	public PackageListAdapter(Activity activity) {
		this.activity = activity;
		this.context = activity.getBaseContext();
		this.content = SysUtil.listAllPackages(activity);
	}
	
	@Override
	public int getCount() {
		return this.content.size();
	}

	@Override
	public Object getItem(int position) {
		return this.content.get(position);
	}

	@Override
	public long getItemId(int position) {
		// return its id if there is one, else return position
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		 if (convertView == null) {
			 convertView = new View(this.context);
			 convertView.setId(position);
			 
			 int min = 0;
			 int max = 255;

			 Random r = new Random();
			 int red = r.nextInt(max - min + 1) + min;
			 int green = r.nextInt(max - min + 1) + min;
			 int blue = r.nextInt(max - min + 1) + min;
			 
			 convertView.setBackgroundColor(Color.rgb(red, green, blue));
			 
		 }
		
		return convertView;
	}


}
