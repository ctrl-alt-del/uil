package com.uil.util;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Class contains all useful system utilities in a static form
 * 
 * @since 2014-03-06
 * @version 1.0
 * */
public class SysUtil {

	/**
	 * Method to retrieve the names of all packages installed in the device
	 * 
	 * @param activity the current activity
	 * @return a {@link LinkedList}<{@Link String}> containing the names of all
	 * packages installed in the device
	 * 
	 * @since 2014-03-06
	 * @version 1.0
	 */
	public static LinkedList<String> listAllPackages(Activity activity) {
		PackageManager manager = activity.getPackageManager();
		List<PackageInfo> packages = manager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		LinkedList<String> nameOfPackages = new LinkedList<String>();
		for (PackageInfo p : packages) {
			nameOfPackages.add(p.packageName);
		}
		return nameOfPackages;
	}

}
