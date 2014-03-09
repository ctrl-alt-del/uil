package com.uil;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.uil.util.SysUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


/**
 * Adapter to display all packages installed on a device
 * 
 * @since 2014-03-07
 * @version 1.0
 * */
public class PackageListAdapter extends BaseAdapter implements ListAdapter {

	private final Activity activity;
	private final Context context;
	private LinkedList<String> content;
	private final PackageManager packageManager;


	public PackageListAdapter(Activity activity) {
		this.activity = activity;
		this.context = activity.getBaseContext();
		this.packageManager = activity.getPackageManager();
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

		PackageItem item;
		if (convertView == null) {
			convertView = View.inflate(this.context, R.layout.package_list_item, null);
			item = new PackageItem().setNameOfPackage((TextView) convertView.findViewById(R.id.textViewOfApplicationName));
		} else {
			item = (PackageItem) convertView.getTag();
		}

		convertView.setTag(item);
		convertView.setId(position);

		int min = 0;
		int max = 255;
		Random r = new Random();
		int red = r.nextInt(max - min + 1) + min;
		int green = r.nextInt(max - min + 1) + min;
		int blue = r.nextInt(max - min + 1) + min;

		convertView.setBackgroundColor(Color.rgb(red, green, blue));

		item.getNameOfPackage().setText((String) this.content.get(position));

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				Log.e("-->", "position: " + v.getId());
				String appName = content.get(v.getId());
				Log.e("-->", "appName: " + appName);
				Intent intent = packageManager.getLaunchIntentForPackage(appName);

				if (intent != null) {
					intent.addCategory(Intent.CATEGORY_LAUNCHER);
					activity.startActivity(intent);
					//					throw new PackageManager.NameNotFoundException();
				} else {
					Toast.makeText(context, "the package is not installed", Toast.LENGTH_LONG).show();
				}
			}
		});
		return convertView;
	}

	private class PackageItem {

		private TextView nameOfPackage;
		private TextView nameOfApplication;
		private TextView description;
		private ImageView icon;
		/**
		 * @return the name
		 */
		public TextView getNameOfPackage() {
			return this.nameOfPackage;
		}
		/**
		 * @param name the name to set
		 */
		public PackageItem setNameOfPackage(TextView name) {
			this.nameOfPackage = name;
			return this;
		}
		/**
		 * @return the title
		 */
		public TextView getNameOfApplication() {
			return this.nameOfApplication;
		}
		/**
		 * @param title the title to set
		 */
		public PackageItem setNameOfApplication(TextView title) {
			this.nameOfApplication = title;
			return this;
		}
		/**
		 * @return the description
		 */
		public TextView getDescription() {
			return this.description;
		}
		/**
		 * @param description the description to set
		 */
		public PackageItem setDescription(TextView description) {
			this.description = description;
			return this;
		}
		/**
		 * @return the icon
		 */
		public ImageView getIcon() {
			return this.icon;
		}
		/**
		 * @param icon the icon to set
		 */
		public PackageItem setIcon(ImageView icon) {
			this.icon = icon;
			return this;
		}
	}
}