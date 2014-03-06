package com.uil;

import com.uil.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class UilPage extends Activity implements View.OnClickListener,
		SystemUiHider.OnVisibilityChangeListener {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	private Activity activity;
	private Context context;
	private PackageManager packageManager;

	private View controlsView, contentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		super.setContentView(R.layout.uil_page);

		this.activity = this;
		this.context = this.getBaseContext();
		this.packageManager = this.context.getPackageManager();

		this.controlsView = findViewById(R.id.fullscreen_content_controls);
		this.contentView = findViewById(R.id.fullscreen_content);

		final Button openCamera = (Button) super.findViewById(R.id.action_01);
		final Button openCalculator = (Button) super.findViewById(R.id.action_02);
		final Button openAlarm = (Button) super.findViewById(R.id.action_03);
		final Button openCalendar = (Button) super.findViewById(R.id.action_04);
		final Button openWifi = (Button) super.findViewById(R.id.action_05);

		openCamera.setOnClickListener(this);
		openCalculator.setOnClickListener(this);
		openAlarm.setOnClickListener(this);
		openCalendar.setOnClickListener(this);
		openWifi.setOnClickListener(this);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider.setOnVisibilityChangeListener(this);

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(this);

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		findViewById(R.id.dummy_button).setOnTouchListener(
				mDelayHideTouchListener);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}

	/**
	 * Method to implement the callback of view being clicked
	 * 
	 * @param view
	 *            the view is being clicked
	 * @version 1.0
	 * @since 2014-02-28
	 * */
	@Override
	public void onClick(View view) {

		Intent intent = new Intent();

		switch (view.getId()) {
		case R.id.action_01:
			try {
				final ResolveInfo mInfo = this.packageManager
						.resolveActivity(
								new Intent(
										android.provider.MediaStore.ACTION_IMAGE_CAPTURE),
								0);

				intent.setComponent(new ComponentName(
						mInfo.activityInfo.packageName, mInfo.activityInfo.name));
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);

				super.startActivity(intent);
			} catch (Exception e) { // ActivityNotFoundException
				Log.e("ERROR", "Unable to launch: ", e);
			}
			break;

		case R.id.action_02:
			try {

				intent.setComponent(new ComponentName(
						"com.android.calculator2",
						"com.android.calculator2.Calculator"));
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);

				super.startActivity(intent);
			} catch (Exception e) { // ActivityNotFoundException
				Log.e("ERROR", "Unable to launch: ", e);
			}
			break;

		case R.id.action_03:
			try {
				final ResolveInfo mInfo = this.packageManager
						.resolveActivity(new Intent(
								android.provider.AlarmClock.ACTION_SET_ALARM),
								0);
				intent.setComponent(new ComponentName(
						mInfo.activityInfo.packageName, mInfo.activityInfo.name));
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);

				super.startActivity(intent);
			} catch (Exception e) { // ActivityNotFoundException
				Log.e("ERROR", "Unable to launch: ", e);
			}
			break;

		case R.id.action_04:
			try {
				final ResolveInfo mInfo = this.packageManager
						.resolveActivity(
								new Intent(
										android.provider.CalendarContract.ACCOUNT_TYPE_LOCAL),
								0);

				intent.setComponent(new ComponentName(
						mInfo.activityInfo.packageName, mInfo.activityInfo.name));
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);

				super.startActivity(intent);
			} catch (Exception e) { // ActivityNotFoundException
				Log.e("ERROR", "Unable to launch: ", e);
			}
			break;
		case R.id.action_05:
			try {
				final ResolveInfo mInfo = this.packageManager
						.resolveActivity(
								new Intent(
										android.provider.Settings.ACTION_WIFI_SETTINGS),
								0);

				intent.setComponent(new ComponentName(
						mInfo.activityInfo.packageName, mInfo.activityInfo.name));
				intent.setAction(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);

				super.startActivity(intent);
			} catch (Exception e) { // ActivityNotFoundException
				Log.e("ERROR", "Unable to launch: ", e);
			}
			break;

		case R.id.fullscreen_content:
			if (TOGGLE_ON_CLICK) {
				mSystemUiHider.toggle();
			} else {
				mSystemUiHider.show();
			}
			break;

		default:
			break;
		}
	}

	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void onVisibilityChange(boolean visible) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			// If the ViewPropertyAnimator API is available
			// (Honeycomb MR2 and later), use it to animate the
			// in-layout UI controls at the bottom of the
			// screen.
			int mControlsHeight = controlsView.getHeight();
			int mShortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			this.controlsView.animate()
					.translationY(visible ? 0 : mControlsHeight)
					.setDuration(mShortAnimTime);
		} else {
			// If the ViewPropertyAnimator APIs aren't
			// available, simply show or hide the in-layout UI
			// controls.
			this.controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
		}

		if (visible && AUTO_HIDE) {
			// Schedule a hide().
			this.delayedHide(AUTO_HIDE_DELAY_MILLIS);
		}
	}
}
